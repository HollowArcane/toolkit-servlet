package toolkit.http;

import java.util.HashMap;

public class HttpServletFormValidationException extends Exception
{
    private HashMap<String, Exception> errors;

    public HttpServletFormValidationException(HashMap<String, Exception> errors)
    { this.errors = errors; }

    public static HttpServletFormValidationException empty()
    { return new HttpServletFormValidationException(null); }

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
