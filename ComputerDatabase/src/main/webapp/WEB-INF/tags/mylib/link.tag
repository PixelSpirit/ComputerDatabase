<%@ tag body-content="empty" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="target" required="true" %> 
<%@ attribute name="page" required="true" %> 
<%@ attribute name="limit" required="true" %>
"http://localhost:8080/cdb/${target}?page=${page}&limit=${limit}"