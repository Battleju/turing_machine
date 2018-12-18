package utils;

import turingExceptions.TuringParseStringBooleanException;
import turingExceptions.TuringWrongExtendParameterException;

import java.util.ArrayList;

public class Utils {

    public static boolean StringWhitelist(ArrayList<Character> alph, String text){
        boolean b = false;
        ArrayList booleans = new ArrayList();
        for (Character c : text.toCharArray()) {
            for (Character character : alph) {
                if (c.equals(character)) {
                    b = true;
                    break;
                }else {
                    b = false;
                }
            }
            booleans.add(b);
        }
        for (int i = 0; i < booleans.size(); i++){
            boolean b1 = (boolean)booleans.get(i);
            if(b1 == false){
                return false;
            }
        }
        return true;
    }

    public static String booleanToString(boolean b){
        if(b == false){
            return "false";
        }else{
            return "true";
        }
    }

    public static boolean stringToBoolean(String s){
        if(s.equals("true")){
            return true;
        }else if(s.equals("false")){
            return false;
        }else {
            try {
                throw new TuringParseStringBooleanException();
            } catch (TuringParseStringBooleanException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
