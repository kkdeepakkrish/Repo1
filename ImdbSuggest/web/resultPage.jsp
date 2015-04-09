<%@page import="Model.Movie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <title>Movie recommendations for 
            <%=request.getAttribute("searchMovie")%>        
        </title>
    </head>
    <body>
        <%
        
        if( request.getAttribute("movieObj") != null ) {
            Movie m = (Movie)request.getAttribute("movieObj");
         %>   
         
        <h1>Here is the recommendation for <font color ="red"> <%= request.getAttribute("searchMovie")%> </font></h1><br>
       <a href="<%= m.getLink()%>"
><img height="573" width="476" title="<%= m.getTitle() %>" src="<%= m.getThumb() %>" /> <br/>
</a>
        <%
        } else {
        %>
        <h1>Sorry! No recommendations found </h1><br>
        <%
        }  
        %>

    </body>
</html>

