package model;
import java.sql.Connection;
import java.sql.Date;

import toolkit.sql.SQLDataModel;

public class Publisher extends SQLDataModel<Publisher>
{
    private String name;
    private String anotherNameAndItWorks;

    private Date foundationDate;

    public Publisher()
    { super("publisher"); }

    public Publisher(Connection conn)
    {
        super("publisher");
        autoMap(conn);
        map("anotherNameAndItWorks", "name");
    }

    public String getName()
    { return name; }
    
    public void setName(String name)
    { this.name = name; }
    
    public String getAnotherNameAndItWorks()
    { return anotherNameAndItWorks; }

    public void setAnotherNameAndItWorks(String anotherNameAndItWorks)
    { this.anotherNameAndItWorks = anotherNameAndItWorks; }

    public Date getFoundationDate()
    { return foundationDate; }
    
    public void setFoundationDate(Date foundationDate)
    { this.foundationDate = foundationDate; }

    @Override
    public String toString()
    { return "Publisher [name=" + name + ", foundationDate=" + foundationDate + ", anotherNameAndItWorks=" + anotherNameAndItWorks + "]"; }
}
