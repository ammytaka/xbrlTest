<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@page import="java.io.BufferedReader" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>LoadEdinetCode</title>
</head>
<body>
<p>Hello LoadEdinetCode !!!</p>
<form method="post" action="/LoadEdinetCode" enctype="multipart/form-data">
<p>
EdinetコードCSVファイル：<input type="file" name="formFile">
</p>
<input type="submit">
</form>
<form  method="post" action="/LoadEdinetCode">
<input type="hidden" name="clear" value="true">
<input type="submit" value="クリア">
</form>
<table border="1">
<c:forEach var="record" items="${list}">
<tr>
<td>${record.edinetCode}</td>
<td>${record.companyName}</td>
<td>${record.industryType}</td>
<td>${record.securityCode}</td>
</tr>
</c:forEach>
</table>

</body>
</html>
