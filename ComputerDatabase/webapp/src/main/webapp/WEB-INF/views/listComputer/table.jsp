<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<!-- Variable declarations for passing labels as parameters -->
			<!-- Table header for Computer Name -->

			<th class="editMode" style="width: 60px; height: 22px;">
			<input type="checkbox" id="selectall" />
			<span style="vertical-align: top;"> -
			<a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
				<i class="fa fa-trash-o fa-lg"></i>
			</a>
			</span></th>
			<th><m:link target="computer/list"
					page="${requestScope.page.number}"
					orderby="name"
					limit="${requestScope.page.size}" search="${requestScope.search}"
					direction="${(requestScope.orderby eq 'name' and requestScope.direction eq 'asc') ? 'desc' : 'asc'}">
					<!--  TODO : Create a tag to factorize code -->
					<c:if test="${requestScope.orderby eq 'name'}">
						<c:choose>
							<c:when test="${requestScope.direction eq 'asc'}"> &dtrif;</c:when>
							<c:otherwise> &utrif;</c:otherwise>
						</c:choose>
					</c:if>
					<spring:message code="computer.general.computerName"/>
				</m:link></th>
			<th><m:link target="computer/list"
					page="${requestScope.page.number}"
					limit="${requestScope.page.size}" search="${requestScope.search}"
					orderby="introduced"
					direction="${(requestScope.orderby eq 'introduced' and requestScope.direction eq 'asc') ? 'desc' : 'asc'}">
					<c:if test="${requestScope.orderby eq 'introduced'}">
						<c:choose>
							<c:when test="${requestScope.direction eq 'asc'}"> &dtrif;</c:when>
							<c:otherwise> &utrif;</c:otherwise>
						</c:choose>
					</c:if>
					<spring:message code="computer.general.introducedDate"/>
					</m:link></th>
			<th><m:link target="computer/list"
					page="${requestScope.page.number}"
					limit="${requestScope.page.size}" search="${requestScope.search}"
					orderby="discontinued"
					direction="${(requestScope.orderby eq 'discontinued' and requestScope.direction eq 'asc') ? 'desc' : 'asc'}">
					<c:if test="${requestScope.orderby eq 'discontinued'}">
						<c:choose>
							<c:when test="${requestScope.direction eq 'asc'}"> &dtrif;</c:when>
							<c:otherwise> &utrif;</c:otherwise>
						</c:choose>
					</c:if>
					<spring:message code="computer.general.discontinuedDate"/>
					</m:link></th>
			<th><m:link target="computer/list"
					page="${requestScope.page.number}"
					limit="${requestScope.page.size}" search="${requestScope.search}"
					orderby="companyName"
					direction="${(requestScope.orderby eq 'companyName' and requestScope.direction eq 'asc') ? 'desc' : 'asc'}">
					<c:if test="${requestScope.orderby eq 'companyName'}">
						<c:choose>
							<c:when test="${requestScope.direction eq 'asc'}"> &dtrif;</c:when>
							<c:otherwise> &utrif;</c:otherwise>
						</c:choose>
					</c:if>
					<spring:message code="computer.general.company"/>
					</m:link></th>

		</tr>
	</thead>
	<!-- Browse attribute computers -->
	<tbody id="results">
		<c:forEach var="computerDTO" items="${requestScope.page.content}">
			<tr>
				<td class="editMode"><input type="checkbox" name="cb"
					class="cb" value="${computerDTO.id}" id="${computerDTO.name}_id">
				</td>
				<td><a
					href="${pageContext.request.contextPath}/computer/update?edit=${computerDTO.id}"
					id="${computerDTO.name}_name">${computerDTO.name}</a></td>
				<td>${computerDTO.introduced}</td>
				<td>${computerDTO.discontinued}</td>
				<td>${computerDTO.companyName}</td>
				
			</tr>
		</c:forEach>
	</tbody>
</table>
