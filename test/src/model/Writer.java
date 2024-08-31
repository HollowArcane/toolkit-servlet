package model;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import toolkit.sql.SQLDataModel;
import toolkit.util.Arrays;
import toolkit.util.Strings;

public class Writer extends SQLDataModel<Writer>
{
    private Integer id;
    private String firstName;
    private String lastName;
    private int gender;

    private Set<Integer> books;

    public Writer()
    {
        super("writer");
        books = new HashSet<>();
    }
    
    @Override
    public String toString()
    { return "Writer [id=" + id + "\n, firstName=" + firstName + "\n, lastName=" + lastName + "\n, gender=" + gender + "\n, books=" + Arrays.join(books) + "]"; }

    public Integer getId()
    { return id; }

    public void setId(Integer id)
    { this.id = id; }

    public void addBooks(Integer book)
        throws Exception
    {
        Objects.requireNonNull(book);
        if(!Arrays.contains(new Integer[] { 1, 2, 3, 4 }, book))
        { throw new Exception(book + " is not a valid book index"); }

        this.books.add(book);
    }

    public Set<Integer> getBooks()
    { return books; }

    public String getFirstName()
    { return firstName; }

    public void setFirstName(String firstName)
        throws Exception
    {
        Objects.requireNonNull(firstName, "%s is required");
        this.firstName = Strings.requireNonEmpty(firstName.trim(),"%s is required");
    }

    public String getLastName()
    { return lastName; }

    public void setLastName(String lastName)
        throws Exception
    {
        Objects.requireNonNull(lastName, "%s is required");
        this.lastName = Strings.requireNonEmpty(lastName.trim(),"%s is required");
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