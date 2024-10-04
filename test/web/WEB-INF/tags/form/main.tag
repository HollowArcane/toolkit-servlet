<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ attribute name="method" required="false" %>
<%@ attribute name="action" required="false" %>

<form action="${ action }" method="${ method }">
    <jsp:doBody />

    <form:button> Valider </form:button>
</form>