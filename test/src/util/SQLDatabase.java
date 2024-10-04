package util;

import java.io.Closeable;
import java.lang.reflect.Constructor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Vector;

public class SQLDatabase implements Closeable
{
    private Connection connection;
    private Statement statement;
    private String catalog;

    public SQLDatabase(String url, String user, String password, boolean autoCommit)
            throws Exception
    {
        connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(autoCommit);
        statement = connection.createStatement();    
    }

    public static SQLDatabase oracle(String user, String password, boolean autoCommit)
            throws Exception
    {
        SQLConfiguration.setup();

        String host = SQLConfiguration.get("oracle_host");
        String port = SQLConfiguration.get("oracle_port");

        Class.forName("oracle.jdbc.driver.OracleDriver");
        Class.forName("oracle.jdbc.OracleDriver");
        SQLDatabase sqlDatabase = new SQLDatabase(String.format("jdbc:oracle:thin:@%s:%s:xe", host, port), user, password, autoCommit);
        sqlDatabase.catalog = user;
        return sqlDatabase;
    }

    public static SQLDatabase oracle(String user, String password)
            throws Exception
    { return oracle(user, password, false); }

    public static SQLDatabase postgres(String user, String password, String database, boolean autoCommit)
            throws Exception
    {
        SQLConfiguration.setup();

        user = user != null ? user : SQLConfiguration.get("postgres_user");
        password = password != null ? password : SQLConfiguration.get("postgres_password");
        String host = SQLConfiguration.get("postgres_host");
        String port = SQLConfiguration.get("postgres_port");

        Class.forName("org.postgresql.Driver");
        SQLDatabase sqlDatabase = new SQLDatabase(String.format("jdbc:postgresql://%s:%s/", host, port) + database, user, password, autoCommit);
        sqlDatabase.catalog = database;
        return sqlDatabase;
    }

    public static SQLDatabase postgres(String database)
            throws Exception
    { return postgres(null, null, database, false); }

    public static SQLDatabase mysql(String user, String password, String database, boolean autoCommit)
            throws Exception
    {
        SQLConfiguration.setup();

        user = user != null ? user : SQLConfiguration.get("mysql_user");
        password = password != null ? password : SQLConfiguration.get("mysql_password");
        String host = SQLConfiguration.get("mysql_host");
        String port = SQLConfiguration.get("mysql_port");

        Class.forName("com.mysql.cj.jdbc.Driver");
        SQLDatabase sqlDatabase = new SQLDatabase(String.format("jdbc:mysql://%s:%s/", host, port) + database, user, password, autoCommit);
        sqlDatabase.catalog = database;
        return sqlDatabase;
    }

    public static SQLDatabase mysql(String database)
            throws Exception
    { return mysql(null, null, database, false); }

    public Connection getConnection()
    { return connection; }

    public String getCatalog()
    { return catalog; }

    public DatabaseMetaData getMetaData()
        throws Exception
    { return connection.getMetaData(); }

    static String[] getFields(ResultSetMetaData table)
            throws Exception
    {
        int numColumns = table.getColumnCount();

        String[] head = new String[numColumns];
        for(int i = 1; i <= numColumns; i++)
        { head[i-1] = table.getColumnLabel(i); }

        return head;
    }

    @SuppressWarnings("unchecked")
    static <E> Vector<E> get(ResultSet set, E reference)
        throws Exception
    {
        Vector<E> objects = new Vector<E>();

        Class<?>[] types = null;
        Constructor<?> constructor = null;

        while (set.next())
        {
            if(types == null)
            {
                types = new Class<?>[set.getMetaData().getColumnCount()];
                for(int i = 0; i < types.length; i++)
                {
                    Object item = set.getObject(i + 1);
                    if(item == null)
                    { types[i] = Object.class; }
                    else
                    { types[i] = item.getClass(); }
                }

                constructor = reference.getClass().getConstructor(types);
            }

            Object[] row = new Object[types.length];
            for(int i = 0; i < row.length; i++)
            { row[i] = set.getObject(i + 1); }

            objects.add((E)constructor.newInstance(row));
        }

        set.close();
        return objects;
    }

    static Vector<LinkedHashMap<String, Object>> get(ResultSet set)
        throws Exception
    {
        ResultSetMetaData setMetaData = set.getMetaData();

        Vector<LinkedHashMap<String, Object>> data = new Vector<LinkedHashMap<String, Object>>();
        String[] keys = getFields(setMetaData);

        while(set.next())
        {
            LinkedHashMap<String, Object> row = new LinkedHashMap<String, Object>();
            for(int i = 0; i < keys.length; i++)
            { row.put(keys[i].toLowerCase(), set.getObject(i + 1)); }
            
            data.add(row);
        }

        return data;
    }

    public static interface StatementExecutor
    { public ResultSet execute() throws Exception; }

    static Vector<LinkedHashMap<String, Object>> get(StatementExecutor se)
        throws Exception
    {
        ResultSet set = null;
        try
        {
            set = se.execute();
            Vector<LinkedHashMap<String, Object>> data = get(set);
            set.close();

            return data;
        }
        catch (Exception e)
        {
            if(set != null)
            { set.close(); }
            throw e;
        }
    }

    static <E> Vector<E> get(StatementExecutor se, E reference)
        throws Exception
    {
        ResultSet set = null;
        try
        {
            set = se.execute();
            Vector<E> data = get(set, reference);
            set.close();

            return data;
        }
        catch (Exception e)
        {
            if(set != null)
            { set.close(); }
            throw e;
        }
    }

    public Vector<LinkedHashMap<String, Object>> get(String request)
        throws Exception
    { return get(() -> statement.executeQuery(request)); }

    public <E> Vector<E> get(String request, E reference)
            throws Exception
    { return get(() -> statement.executeQuery(request), reference); }

    public int post(String request)
            throws Exception
    {
        int result = statement.executeUpdate(request);
        return result;
    }

    public void close()
    {
        try
        {
            if(statement != null)
            { statement.close(); }
            if(connection != null)
            { connection.close(); }
        }
        catch (Exception e)
        { e.printStackTrace(); }
    }
}