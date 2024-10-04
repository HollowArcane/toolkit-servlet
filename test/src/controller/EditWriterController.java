package controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Writer;
import toolkit.http.HttpServletFormValidation;
import toolkit.http.HttpServletFormValidationException;
import util.SQLDatabase;

public class EditWriterController extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException,
               IOException
    {
        req.setAttribute("template__title", "Index Page");
        req.setAttribute("template__content", "/WEB-INF/pages/index.jsp");
        
        try (SQLDatabase db = SQLDatabase.postgres("sample"))
        { req.setAttribute("writers", Arrays.asList(new Writer().readAll(db.getConnection()))); }
        catch (Exception e)
        { e.printStackTrace(); }

        req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException,
              IOException
    {
        HttpServletFormValidation<Writer> validation = null;
        try
        {
            validation = null;
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
