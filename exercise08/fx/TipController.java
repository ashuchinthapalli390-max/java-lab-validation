import javafx.fxml.FXML;import javafx.scene.control.Label;import javafx.scene.control.TextField;
public final class TipController {
 @FXML private TextField bill,percentage,people;@FXML private Label tip,total,perPerson,error;
 @FXML public void calculate(){try{TipModel.Result r=TipModel.calculate(bill.getText(),percentage.getText(),people.getText());tip.setText(r.tip().toPlainString());total.setText(r.total().toPlainString());perPerson.setText(r.perPerson().toPlainString());error.setText("");}catch(IllegalArgumentException e){tip.setText("--");total.setText("--");perPerson.setText("--");error.setText("Enter valid nonnegative numbers and a positive whole number of people.");}}
}
