<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="target" required="true"%>
<%@ attribute name="page" required="true"%>
<%@ attribute name="limit" required="true"%>
<%@ attribute name="search" required="false"%>
<%@ attribute name="orderby" required="false"%>
<%@ attribute name="direction" required="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<a href="${pageContext.request.contextPath}/${target}?page=${page}&limit=${limit}
	<c:if test='${not empty search or not search eq ""}' >
		&search=${search}
	</c:if>
	<c:if test='${not empty orderby or not orderby eq ""}'>
		&orderby=${orderby}
	</c:if>
	<c:if test='${not empty isAscendent or not isAscendent eq ""}'>
		&direction=${direction}
	</c:if>
">
	<jsp:doBody></jsp:doBody>
</a>