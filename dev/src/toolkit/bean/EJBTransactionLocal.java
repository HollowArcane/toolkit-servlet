package toolkit.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import javax.ejb.Local;

import toolkit.exception.ValidationException;

@Local
public interface EJBTransactionLocal<E>
{
    public E[] get() throws SQLException;
    public int post(HashMap<String, Object> formData) throws ValidationException, SQLException;
}
