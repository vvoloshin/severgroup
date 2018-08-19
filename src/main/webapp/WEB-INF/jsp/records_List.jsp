<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Record list</title>
    <style type="text/css">
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 50%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Record List</h2>
    <table>
        <thead>
        <th scope="row">Date</th>
        <th scope="row">Name</th>
        <th scope="row">URL</th>
        <th scope="row">Seconds</th>
        </thead>
        <tbody>
        <c:forEach items="${recordsList}" var="record">
            <tr>
                <td>${record.dateSession}</td>
                <td>${record.userName}</td>
                <td>${record.url}</td>
                <td>${record.avgSeconds}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form method="get" action="/get">
        <input type="text" name="username" id="username" placeholder="type user name for search">
        <input type="submit" value="search">
    </form>
    <br/>
    <a href="/records">return to main page</a>
</div>
</body>
</html>