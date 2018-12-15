package utils;

import java.util.ArrayList;

public class Utils {

    public static boolean StringWhitelist(ArrayList<Character> alph, String text){
        boolean b = false;
        ArrayList booleans = new ArrayList();
        for (Character c : text.toCharArray()) {
            for (Character character : alph) {
                System.out.println(character + "" + c);
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
}
