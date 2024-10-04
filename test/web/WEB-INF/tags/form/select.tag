<%@ attribute name="name" required="true" %>
<%@ attribute name="value" required="false" %>
<%@ attribute name="options" type="java.util.Map" required="false" %>

<div>
    <label class="form-label" for="${ name }"> <jsp:doBody /> </label>
    <select class="form-select" name="${ name }" id="${ name }">
    <% for(java.util.Map.Entry option: options) { %>
        <option ${ option.getKey() == value ? "selected": "" } value="${ option.getKey() }"> ${ option.getValue() } </option>
    <% } %>
    </select>
</div>