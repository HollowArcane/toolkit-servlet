<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="list" tagdir="/WEB-INF/tags/list" %>

<list:g-table data="${ writers }" />

<form:main>
    <h2> Insertion Compteur </h2>

    <form:input name="compteur" type="number"> Valeur </form:input>
    <form:input name="datetime" type="datetime-local"> Date et heure </form:input>
</form:main>