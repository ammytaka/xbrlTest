<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Index</title>
<link rel="stylesheet" type="text/css" href="/css/global.css" />
</head>
<body>
<p>Hello Index !!!</p>
<p>${msg}</p>
<form method="post" action="/">
<table>
<tr><td>企業名</td><td><input type="text" ${f:text("name")}></td></tr>
<tr><td>コード</td><td><input type="text" ${f:text("code")}></td></tr>
<tr><td>証券コード</td><td><input type="text" ${f:text("scode")}></td></tr>
<tr><td></td><td><input type="submit"></td>
</table>
</form>
<table border="1">
<c:forEach var="mydata" items="${mydatas}">
<tr>
<td>${mydata.name}</td>
<td>${mydata.code}</td>
<td>${mydata.scode}</td>
</tr>
</c:forEach>
</table>
</body>
</html>
