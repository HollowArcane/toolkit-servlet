package util;

import java.util.HashMap;

public class SQLConfiguration
{
    private static HashMap<String, String> configurations;

    static void setup()
    {
        if(configurations != null)
        { return; }

        configurations = new HashMap<>();
        
        configurations.put("postgres_host", "localhost");
        configurations.put("postgres_port", "5432");
        configurations.put("postgres_password", "43710");
        configurations.put("postgres_user", "postgres");
        
        configurations.put("oracle_host", "127.0.0.1");
        configurations.put("oracle_port", "1521");
        
        configurations.put("mysql_host", "localhost");
        configurations.put("mysql_port", "3306");
        configurations.put("mysql_password", "43710");
        configurations.put("mysql_user", "root");
    }

    public static String get(String name)
    { return configurations.get(name); }
}
