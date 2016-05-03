<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="target" required="true" %> 
<%@ attribute name="page" required="false" %> 
<%@ attribute name="limit" required="false" %>
<%@ attribute name="search" required="false" %>
<%@ attribute name="edit" type="java.lang.Integer" required="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<a href="http://localhost:8080/cdb/${target}?
	<c:choose>
		<c:when test='${not empty edit}' >
			<c:out value='edit=${edit}' />
		</c:when>
		<c:when test='${not empty page and not empty limit}' >
			<c:out value='page=${page}&limit=${limit}' />
			<c:if test='${not empty search or not search eq ""}' >
				<c:out value='&search=${search}' />
			</c:if>
		</c:when>
	</c:choose>
">
	<jsp:doBody></jsp:doBody>
</a>