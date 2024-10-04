package toolkit.util;

import java.util.HashMap;
import java.util.Objects;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class EJBScanner
{
    private static HashMap<String, String> ejbs = null;

    private EJBScanner() {}

    public static HashMap<String, String> search(Context context)
        throws NamingException
    {
        if(ejbs == null)
        {
            ejbs = new HashMap<>();
            searchRecursive(context, ejbs);
        }

        return ejbs;
    }

    private static void searchRecursive(Context context, HashMap<String, String> results)
        throws NamingException
    { searchRecursive(context, results, "java:global/"); }

    private static void searchRecursive(Context context, HashMap<String, String> results, String root)
        throws NamingException
    {
        Objects.requireNonNull(context);
        Objects.requireNonNull(results);
        root = root == null ? "java:global/": root;

        NamingEnumeration<javax.naming.NameClassPair> list = context.list(root);
        
        while (list.hasMore()) {
            javax.naming.NameClassPair ncPair = list.next();

            if(Strings.count(root, "/") >= 3)
            { results.put(ncPair.getName(), root + ncPair.getName()); }
            else
            { searchRecursive(context, results, root + ncPair.getName() + "/"); }
        }
    }
}
