package toolkit.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import javax.ejb.Local;

import toolkit.exception.ValidationException;

@Local
public interface EJBResourceLocal<E>
{
    public E[] get() throws SQLException;
    public <T> Optional<E> get(T id) throws SQLException;
    public int store(HashMap<String, Object> formData) throws ValidationException, SQLException;
    public <T> int update(HashMap<String, Object> formData, T id) throws ValidationException, SQLException;
    public <T> int delete(HashMap<String, Object> formData, T id) throws ValidationException, SQLException;
}
