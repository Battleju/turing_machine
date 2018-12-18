package turingExceptions;

public class TuringParseStringBooleanException extends Exception {
    public TuringParseStringBooleanException() {
        super("Could not parse this String to a boolean!");
    }
}