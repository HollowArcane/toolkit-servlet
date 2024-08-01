package model;
import toolkit.sql.SQLDataModel;

public class Writer extends SQLDataModel<Writer>
{
    private Integer id;
    private String firstName;
    private String lastName;
    private int gender;

    public Writer()
    { super("writer"); }
    
    @Override
    public String toString()
    { return "Writer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender + "]"; }

    public Integer getId()
    { return id; }

    public void setId(Integer id)
    { this.id = id; }

    public String getFirstName()
    { return firstName; }

    public void setFirstName(String firstName)
        throws Exception
    {
        if(firstName.trim().equals(""))
        { throw new Exception("%s is required"); }
        this.firstName = firstName;
    }

    public String getLastName()
    { return lastName; }

    public void setLastName(String lastName)
        throws Exception
    {
        if(lastName.trim().equals(""))
        { throw new Exception("%s is required"); }
        this.lastName = lastName;
    }

    public int getGender()
    { return gender; }

    public void setGender(int gender)
        throws Exception
    {
        if(gender != 0 && gender != 1)
        { throw new Exception("%s should be a valid gender"); }
        this.gender = gender;
    }
}