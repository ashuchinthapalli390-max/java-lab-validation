import java.awt.image.BufferedImage;import java.io.File;import javax.imageio.ImageIO;import javafx.scene.Parent;import javafx.scene.image.WritableImage;
public final class FxSnapshot {
 public static void save(Parent root,String filename)throws Exception{root.applyCss();root.layout();WritableImage image=root.snapshot(null,null);BufferedImage png=new BufferedImage((int)image.getWidth(),(int)image.getHeight(),BufferedImage.TYPE_INT_ARGB);for(int y=0;y<png.getHeight();y++)for(int x=0;x<png.getWidth();x++)png.setRGB(x,y,image.getPixelReader().getArgb(x,y));File f=new File(filename);f.getParentFile().mkdirs();ImageIO.write(png,"png",f);}
}
