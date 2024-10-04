package toolkit.bean;

import java.sql.SQLException;
import javax.ejb.Local;

@Local
public interface EJBProviderLocal<E>
{
    public E[] get() throws SQLException;
}
