<%@page import="model.Writer" %>
<%@page import="toolkit.http.Request" %>
<%@page import="toolkit.util.Arrays" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <link rel="stylesheet" href="/test/assets/css/chart.toolkit.css">
    <script src="/test/assets/js/chart.umd.js"></script>
    <script src="/test/assets/js/chart.toolkit.js"></script>

    <style>
        .grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            grid-template-rows: 600px 600px;

            gap: 2rem;
        }
    </style>
</head>
<body>
    <form action="/test/form" method="POST">
        <% Request rq = new Request(request); %>

        <p><%= rq.error("first-name", "First Name", "name") %></p>
        <p> First Name <input value="<%= rq.value("first-name").orElse("") %>" type="text" name="first-name"> </p>
        <p><%= rq.error("last-name", "Last Name", "name") %></p>
        <p> Last Name <input value="<%= rq.value("last-name").orElse("") %>" type="text" name="last-name"> </p>
        <p><%= rq.error("gender", "Gender", "gender") %></p>
        <p> Gender <select name="gender">
            <option value="1" <%= "1".equals(rq.value("gender").orElse("")) ? "selected": "" %>> Male </option>
            <option value="0" <%= "0".equals(rq.value("gender").orElse("")) ? "selected": "" %>> Female </option>
        </select> </p>

        <p><%= rq.error("books[]", "Book", "book index") %></p>
        <p> Books
            <p> Book 1: <input type="checkbox" value="1" name="books[]" <%= Arrays.contains(rq.value(String[].class, "books[]").orElse(new String[0]), "1") ? "checked": "" %> /> </p>
            <p> Book 2: <input type="checkbox" value="2" name="books[]" <%= Arrays.contains(rq.value(String[].class, "books[]").orElse(new String[0]), "2") ? "checked": "" %> /> </p>
            <p> Book 3: <input type="checkbox" value="3" name="books[]" <%= Arrays.contains(rq.value(String[].class, "books[]").orElse(new String[0]), "3") ? "checked": "" %> /> </p>
            <p> Book 4: <input type="checkbox" value="4" name="books[]" <%= Arrays.contains(rq.value(String[].class, "books[]").orElse(new String[0]), "4") ? "checked": "" %> /> </p>
            <p> Book 5: <input type="checkbox" value="5" name="books[]" <%= Arrays.contains(rq.value(String[].class, "books[]").orElse(new String[0]), "5") ? "checked": "" %> /> </p>
        </p>
        <button>Submit</button>
    </form>

    <div class="grid">
        <chart type="line">
            <labels>
                <label> Red </label>
                <label> Blue </label>
                <label> Yellow </label>
                <label> Green </label>
                <label> Purple </label>
                <label> Orange </label>
            </labels>

            <dataset
                name="Line 1"
                pt-background="blue"
                pt-border="white"
                h:pt-background="white"
                h:pt-border="blue"
                img="/test/assets/img/img-3.jpg"
                img-width="100"
            >
                <data> 12 </data>
                <data
                    img="/test/assets/img/img-5.jpg"
                > 19 </data>
                <data> 3 </data>
                <data> 5 </data>
                <data> 2 </data>
                <data> 3 </data>
            </dataset>

            <dataset
                tension=".3"
                name="Line 2"
                border="linear-gradient(0, red, blue)"
                background="linear-gradient(0, rgba(255, 0, 0, .2), rgba(0, 0, 255, .2))"
            >
                <data> 20 </data>
                <data> 10 </data>
                <data> 6 </data>
                <data> </data>
                <data> 10 </data>
                <data> 2 </data>
            </dataset>

            <dataset name="Line 3">
                <data> 15 </data>
                <data> 2 </data>
                <data> 18 </data>
                <data> 12 </data>
                <data> 8 </data>
                <data> 14 </data>
            </dataset>
        </chart>

        <chart type="bar">
            <labels>
                <label> Red </label>
                <label> Blue </label>
                <label> Yellow </label>
                <label> Green </label>
                <label> Purple </label>
                <label> Orange </label>
            </labels>

            <dataset background="cornflowerblue" h:background="blue" name="Line 1">
                <data> 12 </data>
                <data> 19 </data>
                <data> 3 </data>
                <data> 5 </data>
                <data> 2 </data>
                <data> 3 </data>
            </dataset>

            <dataset
                name="Line 2"
                border="linear-gradient(90, red, blue)"
                border-width="3"
                background="linear-gradient(90, rgba(255, 0, 0, .2), rgba(0, 0, 255, .2))"    
            >
                <data> 20 </data>
                <data> 10 </data>
                <data
                    img="/test/assets/img/img-5.jpg"
                > 6 </data>
                <data> </data>
                <data> 10 </data>
                <data> 2 </data>
            </dataset>

            <dataset name="Line 3">
                <data> 15 </data>
                <data> 2 </data>
                <data> 18 </data>
                <data> 12 </data>
                <data> 8 </data>
                <data> 14 </data>
            </dataset>
        </chart>

        <chart type="doughnut">
            <labels>
                <label> Red </label>
                <label> Blue </label>
                <label> Yellow </label>
                <label> Green </label>
                <label> Purple </label>
                <label> Orange </label>
            </labels>

            <dataset
                name="Line 1"
                border="linear-gradient(90, red, blue)"
                border-width="3"    
            >
                <data background="red"> 12 </data>
                <data background="blue"> 19 </data>
                <data background="yellow"> 3 </data>
                <data background="green"> 5 </data>
                <data background="purple"> 2 </data>
                <data background="orange"> 3 </data>
            </dataset>

            <dataset name="Line 2">
                <data background="red"> 20 </data>
                <data background="blue"> 10 </data>
                <data
                    background="yellow"
                    img="/test/assets/img/img-5.jpg"
                > 6 </data>
                <data background="green"> </data>
                <data background="purple"> 10 </data>
                <data background="orange"> 2 </data>
            </dataset>

            <dataset name="Line 3">
                <data background="red"> 15 </data>
                <data background="blue"> 2 </data>
                <data background="yellow"> 18 </data>
                <data background="green"> 12 </data>
                <data background="purple"> 8 </data>
                <data background="orange"> 14 </data>
            </dataset>
        </chart>

        <chart type="pie">
            <labels>
                <label> Red </label>
                <label> Blue </label>
                <label> Yellow </label>
                <label> Green </label>
                <label> Purple </label>
                <label> Orange </label>
            </labels>

            <dataset name="Line 1" img="/test/assets/img/img-1.jpg">
                <data> 12 </data>
                <data> 19 </data>
                <data> 3 </data>
                <data> 5 </data>
                <data> 2 </data>
                <data> 3 </data>
            </dataset>

            <dataset
                name="Line 2"
                border="linear-gradient(90, red, blue)"
                border-width="3"    
            >
                <data> 20 </data>
                <data> 10 </data>
                <data> 6 </data>
                <data> </data>
                <data> 10 </data>
                <data> 2 </data>
            </dataset>

            <dataset name="Line 3" img-width="100" img="/test/assets/img/img-3.jpg">
                <data> 15 </data>
                <data> 2 </data>
                <data> 18 </data>
                <data> 12 </data>
                <data> 8 </data>
                <data> 14 </data>
            </dataset>
        </chart>
    </div>
</body>
</html>