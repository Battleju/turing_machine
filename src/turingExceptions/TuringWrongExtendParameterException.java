package turingExceptions;

public class TuringWrongExtendParameterException extends Exception {
    public TuringWrongExtendParameterException() {
        super("Wrong Parameter for a 'extend' method");
    }
}
