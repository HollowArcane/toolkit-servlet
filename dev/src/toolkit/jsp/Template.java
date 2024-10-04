package toolkit.jsp;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Template
{
    private HttpServletRequest request;
    private HttpServletResponse response;

    private ArrayList<String> attributes;

    public Template(HttpServletRequest request, HttpServletResponse response)
    {
        this.request = request;
        this.response = response;
        this.attributes = new ArrayList<>();
    }

    public void render(String view)
        throws IOException,
               ServletException
    { request.getRequestDispatcher(view).include(request, response); }

    public Template with(String attribute, Object value)
    {
        attributes.add(attribute);
        request.setAttribute(attribute, value);
        return this;
    }

    @Override
    public String toString()
    {   
        for (String attribute : attributes)
        { request.removeAttribute(attribute); }

        return "";
    }
}