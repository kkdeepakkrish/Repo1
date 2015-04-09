<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype")%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Movie</title>
    </head>
    <body>
        <p>Search...</p>
        <form action="findMovie" method="GET">
            <label for="letter">Type the movie name.</label>
            <input type="text" name="searchWord" value="" /><br>
            <input type="submit" value="Submit" />
        </form>
       
    </body>
</html>


