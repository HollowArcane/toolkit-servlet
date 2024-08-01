package toolkit.http;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import toolkit.util.Parse;
import toolkit.util.Strings;
import toolkit.util.reflect.GenericType;

public final class HttpServletFormValidation<E>
{
    private static final HashMap<Class<? extends Exception>, String> MESSAGES = new HashMap<>();

    private static void setErrorMessages()
    {
        MESSAGES.putIfAbsent(NumberFormatException.class, "The value of '%s' should be a valid number");
        MESSAGES.putIfAbsent(IllegalArgumentException.class, "The value of '%s' is invalid");
    }

    public static void setErrorMessage(Class<? extends Exception> error, String message)
    { MESSAGES.put(error, message); }

    private HttpServletRequest request;
    private HashMap<String, Exception> errors;
    private HashMap<String, String> parameterMap;

    private Runnable onFail;

    private Class<E> type;
    private E model;

    public HttpServletFormValidation(HttpServletRequest request)
    {
        this.request = request;
        errors = new HashMap<>();
        parameterMap = new HashMap<>(); 
    }

    public HttpServletFormValidation(HttpServletRequest request, Class<E> type)
    {
        this.type = type;
        this.request = request;
        errors = new HashMap<>();
        parameterMap = new HashMap<>(); 
    }

    public HttpServletFormValidation<E> from(E model)
    {
        this.model = model;
        return this;
    }

    public HttpServletFormValidation<E> onFail(Runnable onFail)
    {
        this.onFail = onFail;
        return this;
    }

    public HttpServletFormValidation<E> map(String parameter, String attribute)
    {
        parameterMap.put(parameter, attribute);
        return this;
    }

    public HttpServletFormValidation<E> autoMap()
    { return autoMap(str -> {
        StringBuilder builder = new StringBuilder(str.length());
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) == '-')
            { builder.append(Character.toUpperCase(str.charAt(++i))); }
            else
            { builder.append(str.charAt(i)); }
        }
        return builder.toString();
    }); }

    public HttpServletFormValidation<E> autoMap(Function<String, String> nameMapper)
    {
        for(Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();)
        {
            String next = e.nextElement();
            map(next, nameMapper.apply(next));
        }
        return this;
    }

    public HashMap<String, String> getParameterMap()
    { return parameterMap; }

    public HashMap<String, Exception> getErrorMap()
    { return errors; }

    public E apply()
        throws HttpServletFormValidationException
    {
        setErrorMessages();
        errors.clear();
        if(model == null)
        { model = generateModel(); }

        for(Entry<String, String> param: parameterMap.entrySet())
        {
            try 
            { setValue(param); }
            catch (NoSuchFieldException e)
            { System.out.println(e); }
        }

        if(errors.size() > 0)
        {
            if(onFail != null)
            { onFail.run(); }
            throw new HttpServletFormValidationException(errors);
        }

        return model;
    }

    private void setValue(Entry<String, String> param)
        throws NoSuchFieldException
    {
        Field f = model.getClass().getDeclaredField(param.getValue());
        Object value = null;
        try
        {
            value = Parse.valueOf(f.getType(), request.getParameter(param.getKey()));
            Method m = model.getClass().getDeclaredMethod(
                "set" + Strings.ucFirst(param.getValue()),
                f.getType()
            );
            m.invoke(model, value);
        }
        catch (NoSuchMethodException p)
        {
            try
            { f.set(model, value); }
            catch (IllegalAccessException e)
            { errors.put(param.getKey(), e); }
            catch (IllegalArgumentException e)
            { errors.put(param.getKey(), new Exception(MESSAGES.get(IllegalArgumentException.class))); }
        }
        catch (IllegalAccessException e)
        { errors.put(param.getKey(), e); }
        catch (InvocationTargetException e)
        { errors.put(param.getKey(), MESSAGES.containsKey(e.getCause().getClass()) ? new Exception(MESSAGES.get(e.getCause().getClass())): (Exception)e.getCause()); }
        catch (NumberFormatException e)
        { errors.put(param.getKey(), new Exception(MESSAGES.get(NumberFormatException.class))); }
        catch (IllegalArgumentException e)
        { errors.put(param.getKey(), new Exception(MESSAGES.get(IllegalArgumentException.class))); }
    }

    private E generateModel()
    {
        try
        {
            if(type == null)
            {
                Type t = GenericType.extract(this);
                String className = t.getTypeName().split("<")[0];
                @SuppressWarnings("unchecked")
                Class<E> clazz = (Class<E>)Class.forName(className);
                type = clazz;
            }
            return type.getConstructor().newInstance();    
        }
        catch (Exception e)
        { throw new RuntimeException(e); }
    }
}