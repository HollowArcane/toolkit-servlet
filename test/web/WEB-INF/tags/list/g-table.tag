<%@ attribute name="data" type="Iterable" required="true" %>

<%
    Object reference = null;
    for(Object o: data)
    {
        reference = o;
        break;
    }

    java.lang.reflect.Field[] fields;
    if(reference != null)
    { fields = toolkit.reflect.AttributeAccess.getAccessible(reference); }
    else
    { fields = new java.lang.reflect.Field[0]; }
%>

<table>
    <thead>
        <tr>
            <% for (java.lang.reflect.Field f: fields) { %>
                <th> <%= toolkit.util.StringFormat.F_CAPITALIZE_FIRST.format(
                                toolkit.util.StringFormat.F_CAMELCASE_LC_FIRST.parse(
                                    f.getName())) %> </th>
                <% } %>
            </tr>
    </thead>

    <tbody>
        <% for (Object item : data) { %>
        <tr>
            <% for (java.lang.reflect.Field f: fields) { %>
            <td> <%= toolkit.reflect.AttributeAccess.getValue(item, f) %> </td>
            <% } %>
        </tr>
        <% } %>
    </tbody>
</table>