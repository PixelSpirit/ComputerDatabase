<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib" %> 
<!DOCTYPE html>
<html>

<%@include file="/views/fragments/head.jsp"%>

<body>
	<%@include file="/views/fragments/header.jsp"%>
	
	<section id="main">
		<%@include file="/views/fragments/mainContainer.jsp"%>
		<div class="container" style="margin-top: 10px;">
			<m:pagination page="${requestScope.page}" />
		</div>
	</section>

	<%@include file="/views/fragments/footer.jsp"%>
	
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>