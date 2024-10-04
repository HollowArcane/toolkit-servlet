package toolkit.exception;

import java.util.HashMap;

import toolkit.util.Arrays;

public class ValidationException extends Exception
{
    private HashMap<String, Exception> errors;

    public ValidationException(HashMap<String, Exception> errors)
    { this.errors = errors; }

    public static ValidationException empty()
    { return new ValidationException(null); }

    public HashMap<String, String> getErrorMessages()
    { return Arrays.map(errors, Exception::getMessage); }

    public Exception getError(String param)
    {
        if(errors == null)
        { return null; }

        return errors.get(param);
    }

    public String get(String param)
    {
        if(errors == null)
        { return null; }
        
        return errors.get(param).getMessage();
    }

    @Override
    public String getMessage()
    { return getCause() != null ? getCause().getMessage(): null; }
}
