package toolkit.http;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletValidation extends HttpServlet
{
    private HttpServletFormValidation<?> validation = null;

    private Request request;

    @SuppressWarnings("unchecked")
    public <E> HttpServletFormValidation<E> validate(Class<E> clazz)
    {
        request.saveFormData();

        validation = new HttpServletFormValidation<E>(request.getServletRequest(), clazz)
                            .autoMap()
                            .onFail(request::setErrors);
        return (HttpServletFormValidation<E>)validation;
    }

    @SuppressWarnings("unchecked")
    public <E, T> HttpServletFormValidation<E> validate(E model)
    {   
        request.saveFormData();

        validation = new HttpServletFormValidation<E>(request.getServletRequest(), model)
                            .autoMap()
                            .onFail(request::setErrors);
        return (HttpServletFormValidation<E>)validation;
    }

    
    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
        throws ServletException, IOException
    {
        request = new Request(arg0);
        super.service(arg0, arg1);
    }
}
