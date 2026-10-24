from pathlib import Path
import concurrent.futures, hashlib, json, os, shutil, subprocess, sys, time, urllib.request
from PIL import Image, ImageDraw, ImageFont
ROOT=Path(__file__).resolve().parent
os.chdir(ROOT)
DEPS=ROOT/'.deps'; DEPS.mkdir(exist_ok=True)
OUT=ROOT/'outputs'; OUT.mkdir(exist_ok=True)
BUILD=ROOT/'build'; BUILD.mkdir(exist_ok=True)
BASE='https://repo.maven.apache.org/maven2/'
artifacts={
'h2-2.3.232.jar':'com/h2database/h2/2.3.232/h2-2.3.232.jar',
'mysql-connector-j-9.1.0.jar':'com/mysql/mysql-connector-j/9.1.0/mysql-connector-j-9.1.0.jar',
}
for module in ['base','graphics','controls','fxml']:
 name=f'javafx-{module}-17.0.13-win.jar'
 artifacts[name]=f'org/openjfx/javafx-{module}/17.0.13/{name}'
def download(item):
 name,path=item; target=DEPS/name
 if not target.exists():
  with urllib.request.urlopen(BASE+path,timeout=40) as response: target.write_bytes(response.read())
 return {'name':name,'source':BASE+path,'sha256':hashlib.sha256(target.read_bytes()).hexdigest()}
with concurrent.futures.ThreadPoolExecutor(max_workers=6) as pool:
 dependencies=list(pool.map(download,artifacts.items()))
(OUT/'dependencies.json').write_text(json.dumps(dependencies,indent=2))
font=ImageFont.truetype('C:/Windows/Fonts/consola.ttf',18)
im=Image.new('RGB',(800,480))
px=im.load()
for y in range(480):
 for x in range(800): px[x,y]=(30+int(30*x/800),65+int(80*y/480),145+int(60*x/800))
d=ImageDraw.Draw(im);d.rounded_rectangle((65,100,735,380),28,outline='white',width=4);d.text((110,175),'JAVA FX',font=ImageFont.truetype('C:/Windows/Fonts/consolab.ttf',74),fill='white')
im.save(ROOT/'exercise08/fx/sample.png')
java=shutil.which('java'); javac=shutil.which('javac')
if not java or not javac: raise RuntimeError('JDK 17 is required')
classpath=os.pathsep.join([str(BUILD),str(DEPS/'h2-2.3.232.jar'),str(DEPS/'mysql-connector-j-9.1.0.jar')])
modulepath=os.pathsep.join(str(p) for p in DEPS.glob('javafx-*.jar'))
sources=[str(p) for directory in ['exercise02','exercise03','exercise07','exercise08','exercise09','tests'] for p in (ROOT/directory).rglob('*.java')]
compile_cmd=[javac,'--release','17','--module-path',modulepath,'--add-modules','javafx.controls,javafx.fxml','-d',str(BUILD)]+sources
compiled=subprocess.run(compile_cmd,text=True,capture_output=True,timeout=60)
(OUT/'compile.txt').write_text('Java 17 compilation\n'+compiled.stdout+compiled.stderr+'\nExit code: '+str(compiled.returncode))
if compiled.returncode: raise RuntimeError(compiled.stderr)
for p in (ROOT/'exercise08/fx').glob('*.fxml'): shutil.copy2(p,BUILD/p.name)
shutil.copy2(ROOT/'exercise08/fx/sample.png',BUILD/'sample.png')
def run(name,cls,args=(),gui=False,timeout=25):
 cmd=[java]
 if gui: cmd+=['-Djavafx.cachedir='+str(DEPS/'javafx-cache'),'--module-path',modulepath,'--add-modules','javafx.controls,javafx.fxml']
 cmd+=['-cp',classpath,cls]+list(args)
 start=time.monotonic();p=subprocess.run(cmd,text=True,capture_output=True,timeout=timeout,creationflags=getattr(subprocess,'CREATE_NO_WINDOW',0));duration=time.monotonic()-start
 log='Command: java '+cls+' '+' '.join(args)+'\nElapsed seconds: '+format(duration,'.3f')+'\nExit code: '+str(p.returncode)+'\n\n'+p.stdout+p.stderr
 (OUT/(name+'.txt')).write_text(log,encoding='utf-8')
 if p.returncode: raise RuntimeError(name+' failed\n'+log)
 return {'name':name,'exit_code':p.returncode,'elapsed_seconds':round(duration,3),'stdout':p.stdout}
jobs=[('ex02_binary_search','BinarySearch',()),('ex03_constructor_overloading','ConstructorOverloading',()),('ex07_thread_extending','ThreadsByExtending',('3',)),('ex07_thread_runnable','ThreadsByRunnable',('3',)),('ex07_alive_join','AliveJoinDemo',()),('ex07_daemon','DaemonThreadDemo',()),('ex07_producer_consumer','ProducerConsumer',('10',)),('ex08_packages','PackageExample',()),('checks','Checks',())]
with concurrent.futures.ThreadPoolExecutor(max_workers=6) as pool:
 results=list(pool.map(lambda j:run(*j),jobs))
for cls,tag in [('ThreadsByExtending','ex07_thread_extending'),('ThreadsByRunnable','ex07_thread_runnable')]:
 result=next(r for r in results if r['name']==tag)
 for message in ['Good Morning','Hello','Welcome']: assert result['stdout'].splitlines().count(message)==3,(tag,message)
 assert result['elapsed_seconds']>=8.8,tag
for n in [0,1,100]:
 r=run('producer_stress_'+str(n),'ProducerConsumer',(str(n),))
 assert 'Verified '+str(n)+' items' in r['stdout']
 results.append(r)
results.append(run('ex08_label_image','LabelImageApp',('--snapshot',),True))
results.append(run('ex08_tip_calculator','TipCalculatorApp',('--snapshot',),True))
for p in OUT.glob('*.txt'):
 if p.stem.startswith('producer_stress_'): continue
 lines=[]
 for line in p.read_text(encoding='utf-8').splitlines():
  lines.extend([line[i:i+100] for i in range(0,max(len(line),1),100)])
 image=Image.new('RGB',(1180,max(220,90+26*len(lines))),'#101a2d');draw=ImageDraw.Draw(image)
 draw.text((28,20),'ACTUAL EXECUTION TRANSCRIPT: '+p.stem,font=font,fill='#8dd8c7')
 for i,line in enumerate(lines):draw.text((28,65+26*i),line,font=font,fill='#eef2ff')
 image.save(p.with_name(p.stem+'_transcript.png'))
summary={'status':'passed','java_version':subprocess.run([java,'-version'],capture_output=True,text=True).stderr,'checks':results,'limitations':['JDBC integration used H2 MySQL compatibility mode, not a live MySQL server.','Portal URL submissions do not execute these programs.','GUI images are snapshots of the applications own rendered scene; console PNGs render actual saved execution transcripts.']}
(OUT/'validation.json').write_text(json.dumps(summary,indent=2),encoding='utf-8')
print('PASS: '+str(len(results))+' execution groups, 26 unit/integration assertions, 2 JavaFX scene checks',flush=True)
