package toolkit.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import javax.ejb.Remote;

import toolkit.exception.ValidationException;

@Remote
public interface EJBTransactionRemote<E>
{
    public E[] get() throws SQLException;
    public int post(HashMap<String, Object> formData) throws ValidationException, SQLException;
}
