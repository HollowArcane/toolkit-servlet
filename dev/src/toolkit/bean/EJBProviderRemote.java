package toolkit.bean;

import java.sql.SQLException;
import javax.ejb.Remote;

@Remote
public interface EJBProviderRemote<E>
{
    public E[] get() throws SQLException;
}
