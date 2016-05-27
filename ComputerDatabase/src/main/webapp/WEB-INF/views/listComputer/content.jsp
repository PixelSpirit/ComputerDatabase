<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<section id="main">
	<div class="container">
		<h1 id="homeTitle">
			<c:out value="${requestScope.computerNumber}" />
			<spring:message code="computer.list.numberFound"/>
		</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">
					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="<spring:message code='computer.list.searchName'/>"
						<c:if test="${not empty requestScope.search}">
							value="${requestScope.search}"
						</c:if>
					/>
					<input type="submit" id="searchsubmit" value="<spring:message code='computer.list.filterByName'/>"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="${pageContext.request.contextPath}/computer/add">
					<spring:message code="computer.list.addComputer"/>
				</a>
				<a class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();">
					<spring:message code="computer.list.edit"/>
				</a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="#" method="POST">
		<input type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<%@include file="/WEB-INF/views/listComputer/table.jsp"%>
	</div>
</section>