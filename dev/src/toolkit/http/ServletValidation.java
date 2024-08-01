package toolkit.http;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletValidation extends HttpServlet
{
    private static String errorPrefix = "error:";
    private static String valuePrefix = "value:";
    private HttpServletFormValidation<?> validation = null;

    private static HttpServletRequest request;

    @SuppressWarnings("unchecked")
    public <E> HttpServletFormValidation<E> validate(Class<E> clazz)
    {
        for(Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();)
        {
            String parameter = e.nextElement();
            request.setAttribute(errorPrefix + parameter, "");
            request.setAttribute(valuePrefix + parameter, request.getParameter(parameter));
        }
        validation = new HttpServletFormValidation<E>(request, clazz).autoMap().onFail(this::setErrors);
        return (HttpServletFormValidation<E>)validation;
    }

    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
        throws ServletException, IOException
    {
        request = arg0;
        super.service(arg0, arg1);
    }

    public static void setErrorPrefix(String errorPrefix)
    { ServletValidation.errorPrefix = errorPrefix; } 

    public static void setValuePrefix(String valuePrefix)
    { ServletValidation.valuePrefix = valuePrefix; } 

    public void setErrors()
    {
        for(Entry<String, Exception> error: validation.getErrorMap().entrySet())
        { request.setAttribute(errorPrefix + error.getKey(), error.getValue().getMessage()); }
    }

    public static boolean hasError(String parameter)
    { return request.getAttribute(errorPrefix + parameter) != null; }

    public static String error(String parameter, Object... format)
    { return hasError(parameter) ? String.format(request.getAttribute(errorPrefix + parameter).toString(), format): ""; }

    public static Optional<Object> attribute(String key)
    { return Optional.ofNullable(request.getAttribute(key)); }

    @SuppressWarnings("unchecked")
    public static <E> Optional<E> attribute(Class<E> type, String key)
    { return Optional.ofNullable((E)request.getAttribute(key)); }

    @SuppressWarnings("unchecked")
    public static <E, T> Optional<T> attribute(String key, Function<E, T> method)
    { return Optional.ofNullable((E)request.getAttribute(key)).map(method); }

    public static Optional<Object> value(String parameter)
    { return attribute(valuePrefix + parameter); }
}
