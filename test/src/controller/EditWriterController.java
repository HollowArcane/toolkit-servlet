package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Writer;
import toolkit.http.HttpServletFormValidation;
import toolkit.http.HttpServletFormValidationException;
import toolkit.http.ServletValidation;

public class EditWriterController extends ServletValidation
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException,
               IOException
    { req.getRequestDispatcher("/WEB-INF/edit.jsp").forward(req, resp); }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException,
              IOException
    {
        HttpServletFormValidation<Writer> validation = null;
        try
        {
            validation = validate(Writer.class);
            Writer writer = validation.apply();    
            System.out.println(writer);

            resp.sendRedirect("form");
        }
        catch (HttpServletFormValidationException e)
        {
            System.out.println(validation.getErrorMap());
            doGet(req, resp);
        }
    }
}
