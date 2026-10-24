import java.math.BigDecimal;import java.math.RoundingMode;
public final class TipModel {
 public record Result(BigDecimal tip,BigDecimal total,BigDecimal perPerson){}
 public static Result calculate(String billText,String percentageText,String peopleText){
  BigDecimal bill=new BigDecimal(billText.trim()),rate=new BigDecimal(percentageText.trim());int people=Integer.parseInt(peopleText.trim());
  if(bill.signum()<0||rate.signum()<0||people<1)throw new IllegalArgumentException("Use nonnegative amounts and at least one person");
  BigDecimal tip=bill.multiply(rate).divide(new BigDecimal("100"),2,RoundingMode.HALF_UP),total=bill.add(tip).setScale(2,RoundingMode.HALF_UP);
  return new Result(tip,total,total.divide(BigDecimal.valueOf(people),2,RoundingMode.HALF_UP));
 }
}
