<%@ attribute name="name" required="true" %>
<%@ attribute name="type" required="false" %>
<%@ attribute name="value" required="false" %>
<%@ attribute name="placeholder" required="false" %>


<div>
    <label class="form-label" for="${ name }"> <jsp:doBody /> </label>
    <input
        class="form-control"
        type="${ empty type ? "text": type }"
        name="${ name }"    
        id="${ name }"
        placeholder="${ empty placeholder ? "": placeholder }"
        value="${ empty value ? "": value }"
    >
</div>