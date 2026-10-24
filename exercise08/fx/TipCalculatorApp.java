import javafx.application.Application;import javafx.application.Platform;import javafx.fxml.FXMLLoader;import javafx.scene.Parent;import javafx.scene.Scene;import javafx.scene.control.*;import javafx.stage.Stage;
public final class TipCalculatorApp extends Application{
 public void start(Stage stage)throws Exception{Parent root=FXMLLoader.load(getClass().getResource("tip-calculator.fxml"));stage.setTitle("Tip Calculator");stage.setScene(new Scene(root));boolean snapshot=getParameters().getRaw().contains("--snapshot");if(snapshot){stage.setX(-10000);stage.setY(-10000);}stage.show();if(snapshot)Platform.runLater(()->{try{
  ((TextField)root.lookup("#bill")).setText("1000");((TextField)root.lookup("#percentage")).setText("15");((TextField)root.lookup("#people")).setText("2");((Button)root.lookup("#calculate")).fire();if(!((Label)root.lookup("#perPerson")).getText().equals("575.00"))throw new AssertionError("GUI calculation");
  FxSnapshot.save(root,"outputs/ex08_tip_calculator.png");((TextField)root.lookup("#people")).setText("0");((Button)root.lookup("#calculate")).fire();if(((Label)root.lookup("#error")).getText().isEmpty())throw new AssertionError("GUI invalid input");System.out.println("GUI calculation and invalid-input event checks passed");
 }catch(Throwable e){e.printStackTrace();System.exit(1);}finally{Platform.exit();}});}
 public static void main(String[] args){launch(args);}
}
