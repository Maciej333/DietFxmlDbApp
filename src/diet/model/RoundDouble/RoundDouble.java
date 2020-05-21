package diet.model.RoundDouble;

public class RoundDouble {

    public static String roundDouble(Double doubleToRound){
        String doubleFormat = String.format("%.2f", doubleToRound);
        String stringToParse = doubleFormat.replace(",",".");
        return stringToParse;
    }
}
