<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<%@include file="/WEB-INF/views/templates/head.jsp"%>

<body>
	<%@include file="/WEB-INF/views/templates/header.jsp"%>
	
	<%@include file="/WEB-INF/views/editComputer/content.jsp" %>
	
	<%@include file="/WEB-INF/views/templates/scripts.jsp"%>
	
	<script src="${pageContext.request.contextPath}/resources/js/computerValidator.js"></script>
</body>
</html>