package toolkit.http;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import toolkit.bean.Bean;
import toolkit.util.Debug;
import toolkit.util.EJBScanner;

public class ServletEJBClient extends HttpServlet
{
    @Override
    public void init()
        throws ServletException 
    {
        super.init();

        HashMap<String, String> ejbs = null;
        Context context = null;

        try
        {
            context = new InitialContext();
            ejbs = EJBScanner.search(context);    
        }
        catch (Exception e)
        {
            Debug.error(e);
            return;
        }

        setBeanFields(ejbs, context);
    }

    private void setBeanFields(HashMap<String, String> ejbs, Context context)
    {
        Field[] fields = getClass().getDeclaredFields();
        for(Field f: fields)
        {
            Bean annotation = f.getAnnotation(Bean.class);
            if(annotation != null)
            {
                String beanName = annotation.value() == null ? f.getType().getSimpleName(): annotation.value();
                String beanReference = beanName + "!" + f.getType().getName();

                if(ejbs.containsKey(beanReference))
                {
                    try
                    { f.set(this, context.lookup(ejbs.get(beanReference))); }
                    catch(NamingException e)
                    { Debug.error(e.getMessage()); }
                    catch (IllegalArgumentException | IllegalAccessException e)
                    { Debug.error(e.getMessage()); }
                }
                else
                { Debug.warning("Bean does not exist: " + beanReference); }
            }
        }
    }
}
