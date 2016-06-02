<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/templates/head.jsp"%>
<body>
	<%@include file="/WEB-INF/views/templates/header.jsp"%>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				Error 500: An error has occured!
				<br/>
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<%@include file="/WEB-INF/views/templates/scripts.jsp"%>

</body>
</html>