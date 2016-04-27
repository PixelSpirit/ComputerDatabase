<%@ tag body-content="empty" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="text" required="true" %>
<%@ attribute name="target" required="true" %> 
<%@ attribute name="page" required="true" %> 
<%@ attribute name="limit" required="true" %>
<a href="${target}?page=${page}&limit=${limit}">${text}</a>