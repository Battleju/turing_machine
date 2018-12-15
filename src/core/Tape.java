package core;

import turingExceptions.TuringWrongExtendParameterException;

import java.util.ArrayList;

public class Tape {
    private char center;
    private char[] posSpace;
    private char[] negSpace;

    Tape(String tapeEx) {
        posSpace = new char[3];
        negSpace = new char[3];
        center = '#';

        for (int i = 0; i < posSpace.length; i++){
            posSpace[i] = '#';
            negSpace[i] = '#';
        }

        ArrayList<Character> tapeExChar = new ArrayList<>();
        for (char c : tapeEx.toCharArray()) {
            tapeExChar.add(c);
        }
        center = tapeExChar.get(0);
        for (int i = 1; i < tapeExChar.size(); i++){
            setValue(i, tapeExChar.get(i));
        }

    }

    public char getValue(int pos){
        if(pos == 0){
            return center;
        }else if(pos > 0){
            try{
                return posSpace[pos];
            }catch (ArrayIndexOutOfBoundsException ex){
                return '#';
            }
        }else {
            try{
                return negSpace[pos * -1];
            }catch (ArrayIndexOutOfBoundsException ex){
                return '#';
            }
        }
    }

    void setValue(int pos, char value){
        if(pos == 0){
            center = value;
        }else if(pos > 0){
            try{
                posSpace[pos] = value;
            }catch (ArrayIndexOutOfBoundsException ex){
                extend(1);
                posSpace[pos] = value;
            }
        }else {
            try{
                negSpace[pos * -1] = value;
            }catch (ArrayIndexOutOfBoundsException ex){
                extend(-1);
                negSpace[pos * -1] = value;
            }
        }
    }

    private void extend(int setting){
        if(setting == 1){
            char[] temp = new char[posSpace.length];
            System.arraycopy(posSpace, 0, temp, 0, posSpace.length);
            posSpace = new char[temp.length + 1];
            for (int i = 0; i < posSpace.length; i++){
                try{
                    posSpace[i] = temp[i];
                }catch (ArrayIndexOutOfBoundsException ex){
                    posSpace[i] = '#';
                }
            }
        }else if(setting == -1){
            char[] temp = new char[negSpace.length];
            System.arraycopy(negSpace, 0, temp, 0, negSpace.length);
            negSpace = new char[temp.length + 1];
            for (int i = 0; i < negSpace.length; i++){
                try{
                    negSpace[i] = temp[i];
                }catch (ArrayIndexOutOfBoundsException ex){
                    negSpace[i] = '#';
                }
            }
        }else {
            new TuringWrongExtendParameterException();
        }
    }
}
