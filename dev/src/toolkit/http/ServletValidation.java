package toolkit.http;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import toolkit.util.FormValidation;

public class ServletValidation extends HttpServlet
{
    private FormValidation<?> validation = null;

    private Request request;

    @SuppressWarnings("unchecked")
    public <E> FormValidation<E> validate(Class<E> clazz)
    {
        request.saveFormData();

        validation = new FormValidation<E>(request.getParameterMap(), clazz)
                            .autoMap()
                            .onFail(request::setErrors);
        return (FormValidation<E>)validation;
    }

    @SuppressWarnings("unchecked")
    public <E, T> FormValidation<E> validate(E model)
    {   
        request.saveFormData();

        validation = new FormValidation<E>(request.getParameterMap(), model)
                            .autoMap()
                            .onFail(request::setErrors);
        return (FormValidation<E>)validation;
    }

    
    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
        throws ServletException, IOException
    {
        request = new Request(arg0);
        super.service(arg0, arg1);
    }
}
