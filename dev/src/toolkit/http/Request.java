package toolkit.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import toolkit.exception.ValidationException;
import toolkit.util.FormValidation;
import toolkit.util.Parse;

public class Request
{
    private static String errorPrefix = "error:";
    private static String valuePrefix = "value:";

    private HttpServletRequest request;
    public Request(HttpServletRequest request)
    {
        Objects.requireNonNull(request);
        this.request = request;
    }

    public HashMap<String, Object> getParameterMap()
    {
        HashMap<String, Object> parameters = new HashMap<>();
        for(Enumeration<String> keys = request.getParameterNames(); keys.hasMoreElements();)
        {
            String key = keys.nextElement();
            if(key.endsWith("[]"))
            { parameters.put(key, request.getParameterValues(key)); }
            else
            { parameters.put(key, request.getParameter(key)); }
        }
        return parameters;
    }

    public HttpServletRequest getServletRequest()
    { return request; }

    public static void setErrorPrefix(String errorPrefix)
    {
        Objects.requireNonNull(errorPrefix);
        Request.errorPrefix = errorPrefix;
    } 

    public static void setValuePrefix(String valuePrefix)
    {
        Objects.requireNonNull(valuePrefix);
        Request.valuePrefix = valuePrefix;
    } 

    public void saveFormData()
    {
        for(Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();)
        {
            String parameter = e.nextElement();
            request.setAttribute(errorPrefix + parameter, "");
            if(parameter.endsWith("[]"))
            { request.setAttribute(valuePrefix + parameter, request.getParameterValues(parameter)); }
            else
            { request.setAttribute(valuePrefix + parameter, request.getParameter(parameter)); }
        }
    }

    public void setErrors(FormValidation<?> validation)
    {
        for(Entry<String, Exception> error: validation.getErrorMap().entrySet())
        { request.setAttribute(errorPrefix + error.getKey(), error.getValue().getMessage()); }
    }

    public void setErrors(ValidationException exception)
    {
        for(Entry<String, String> error: exception.getErrorMessages().entrySet())
        { request.setAttribute(errorPrefix + error.getKey(), error.getValue()); }
    }

    public boolean hasError(String parameter)
    { return request.getAttribute(errorPrefix + parameter) != null; }

    public String error(String parameter, Object... format)
    { return hasError(parameter) ? String.format(request.getAttribute(errorPrefix + parameter).toString(), format): ""; }

    public Optional<Object> attribute(String key)
    { return Optional.ofNullable(request.getAttribute(key)); }

    @SuppressWarnings("unchecked")
    public <E> Optional<E> parameter(Class<E> type, String key)
    {
        String param = request.getParameter(key);
        Object value = null;

        try
        { value = Parse.valueOf(type, param); }
        catch (Exception e)
        { value = null; }

        return Optional.ofNullable((E)value);
    }

    @SuppressWarnings("unchecked")
    public <E> Optional<E> attribute(Class<E> type, String key)
    { return Optional.ofNullable((E)request.getAttribute(key)); }

    @SuppressWarnings("unchecked")
    public <E, T> Optional<T> attribute(String key, Function<E, T> method)
    { return Optional.ofNullable((E)request.getAttribute(key)).map(method); }

    public Optional<Object> value(String parameter)
    { return attribute(valuePrefix + parameter); }

    public <E> Optional<E> value(Class<E> type, String parameter)
    { return attribute(type, valuePrefix + parameter); }
}
