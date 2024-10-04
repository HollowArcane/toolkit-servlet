package toolkit.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;


import toolkit.exception.ParseException;
import toolkit.exception.ValidationException;
import toolkit.reflect.GenericType;

public final class FormValidation<E>
{
    private static final String[] VALID_ARRAYS = { "java.util.ArrayList", "java.util.Vector", "java.util.List", "java.util.HashSet", "java.util.Set" };
    private static final HashMap<Class<? extends Exception>, String> MESSAGES = new HashMap<>();

    private static void setErrorMessages()
    { MESSAGES.putIfAbsent(ParseException.class, "The value of '%s' should be a valid %s"); }

    public static void setErrorMessage(Class<? extends Exception> error, String message)
    { MESSAGES.put(error, message); }

    private HashMap<String, Object> parameters;
    private HashMap<String, Exception> errors;
    private HashMap<String, String> parameterMap;

    private Consumer<FormValidation<E>> onFail;

    private Class<E> type;
    private E model;


    public FormValidation(HashMap<String, Object> parameters, Class<E> type)
    {
        Objects.requireNonNull(type);
        Objects.requireNonNull(parameters);

        setErrorMessages();
        this.type = type;
        this.parameters = parameters;
        errors = new HashMap<>();
        parameterMap = new HashMap<>(); 
    }

    public FormValidation(HashMap<String, Object> parameters, E model)
    {
        Objects.requireNonNull(model);
        Objects.requireNonNull(parameters);

        setErrorMessages();
        this.model = model;
        this.parameters = parameters;
        errors = new HashMap<>();
        parameterMap = new HashMap<>(); 
    }

    public FormValidation<E> onFail(Consumer<FormValidation<E>> onFail)
    {
        this.onFail = onFail;
        return this;
    }

    public FormValidation<E> map(String parameter, String attribute)
    {
        parameterMap.put(parameter, attribute);
        return this;
    }

    public FormValidation<E> autoMap()
    { return autoMap(str -> {
        StringBuilder builder = new StringBuilder(str.length());
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) == '-')
            { builder.append(Character.toUpperCase(str.charAt(++i))); }
            else if(str.charAt(i) != '[' && str.charAt(i) != ']')
            { builder.append(str.charAt(i)); }
        }
        return builder.toString();
    }); }

    public FormValidation<E> autoMap(Function<String, String> nameMapper)
    {
        for(String next: parameters.keySet())
        { map(next, nameMapper.apply(next)); }
        return this;
    }

    public HashMap<String, String> getParameterMap()
    { return parameterMap; }

    public HashMap<String, Exception> getErrorMap()
    { return errors; }

    public HashMap<String, String> getErrorMessages()
    { return Arrays.map(errors, Exception::getMessage); }

    public E apply()
        throws ValidationException
    {
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
            { onFail.accept(this); }
            throw new ValidationException(errors);
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
            Object parameter = parameters.get(param.getKey());
            if(parameter instanceof String[])
            {
                if(Arrays.contains(VALID_ARRAYS, f.getType().getName()) && setValues(f, (String[])parameter))
                { return; }
                if(f.getType().isArray())
                { value = Parse.valueOfArray((Class<?>)f.getType().getComponentType(), (String[])parameter); }
                else
                { value = Parse.valueOf(f.getType(), String.valueOf(parameters.get(param.getKey()))); }
            }
            else
            { value = Parse.valueOf(f.getType(), String.valueOf(parameter)); }
    
            setValue(param.getKey(), f, value);
        }
        catch (NoSuchMethodException p)
        {
            try
            { f.set(model, value); }
            catch (IllegalAccessException e)
            { errors.put(param.getKey(), e); }
            catch (IllegalArgumentException e)
            { errors.put(param.getKey(), new Exception(MESSAGES.get(ParseException.class))); }
        }
        catch (IllegalAccessException e)
        { errors.put(param.getKey(), e); }
        catch (InvocationTargetException e)
        { errors.put(param.getKey(), MESSAGES.containsKey(e.getCause().getClass()) ? new Exception(MESSAGES.get(e.getCause().getClass())): (Exception)e.getCause()); }
        
        // the following exception are parsing exceptions: they cannot be sent as null nor as the type expected by the method
        catch (ParseException e)
        { errors.put(param.getKey(), new Exception(MESSAGES.get(ParseException.class))); }
    }

    private boolean setValues(Field f, String[] parameters)
        throws IllegalAccessException,
               InvocationTargetException
    {
        try
        {
            // search for a 'addAttribute' method
            Method m = Arrays.first(model.getClass().getDeclaredMethods(),
                m0 -> m0.getName().equals("add" + Strings.ucFirst(f.getName()))
                   && m0.getParameterCount() == 1
            ).orElseThrow();

            for(int i = 0; i < parameters.length; i++)
            { m.invoke(model, Parse.valueOf(m.getParameterTypes()[0], parameters[i])); }
            
            return true;
        }
        catch (NoSuchElementException e)
        { /* let collapse to the next statement */ }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            // throw to the exception handler
            throw e;
        }
        return false;
    }

    private void setValue(String param, Field field, Object value)
            throws NoSuchMethodException,
                   IllegalAccessException,
                   InvocationTargetException
    { 
        try
        {
            Method m = model.getClass().getDeclaredMethod(
                "set" + Strings.ucFirst(field.getName()),
                String.class
            );     
            m.invoke(model, parameters.get(param));
        }
        catch(NoSuchMethodException e)
        {
            Method m = model.getClass().getDeclaredMethod(
                "set" + Strings.ucFirst(field.getName()),
                field.getType()
            );  
            m.invoke(model, value);
        }
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