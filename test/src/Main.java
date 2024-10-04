import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import controller.EditWriterController;
import model.Publisher;
import model.Writer;

public class Main
{
    public static void main(String[] args)
        throws SQLException,
               ClassNotFoundException
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        System.out.println(list.getClass().getGenericSuperclass());
        EditWriterController controller = new EditWriterController();
    }
}
