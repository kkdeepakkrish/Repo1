<%-- 
    Document   : StartFileUpload
    Created on : Dec 29, 2014, 3:29:49 PM
    Author     : KK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p>Upload user file:</p>
        <form action="uploadFile" method="post" enctype="multipart/form-data">
    <input type="text" name="description" />
    <input type="file" name="file" />
    <input type="submit" />
</form>
    </body>
</html>
