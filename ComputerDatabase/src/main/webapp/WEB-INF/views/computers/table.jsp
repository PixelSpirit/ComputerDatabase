<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<!-- Variable declarations for passing labels as parameters -->
			<!-- Table header for Computer Name -->

			<th class="editMode" style="width: 60px; height: 22px;"><input
				type="checkbox" id="selectall" /> <span
				style="vertical-align: top;"> - <a href="#"
					id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
						class="fa fa-trash-o fa-lg"></i>
				</a>
			</span></th>
			<th><m:link target="computers"
					page="${requestScope.page.number}"
					limit="${requestScope.page.size}" search="${requestScope.search}"
					orderby="name" isAscendent="${(requestScope.orderby eq 'name' and requestScope.isAscendent eq true) ? false : true}">
					<!--  TODO : Create a tag to factorize code -->
					<c:if test="${requestScope.orderby eq 'name'}">
						<c:choose>
							<c:when test="${requestScope.isAscendent eq true}"> &dtrif;</c:when>
							<c:otherwise> &utrif;</c:otherwise>
						</c:choose>
					</c:if>
					Computer name
				</m:link></th>
			<th><m:link target="computers"
					page="${requestScope.page.number}"
					limit="${requestScope.page.size}" search="${requestScope.search}"
					orderby="introduced" isAscendent="${(requestScope.orderby eq 'introduced' and requestScope.isAscendent eq true) ? false : true}">
					<c:if test="${requestScope.orderby eq 'introduced'}">
						<c:choose>
							<c:when test="${requestScope.isAscendent eq true}"> &dtrif;</c:when>
							<c:otherwise> &utrif;</c:otherwise>
						</c:choose>
					</c:if>
					Introduced date
					</m:link></th>
			<th><m:link target="computers"
					page="${requestScope.page.number}"
					limit="${requestScope.page.size}" search="${requestScope.search}"
					orderby="discontinued" isAscendent="${(requestScope.orderby eq 'discontinued' and requestScope.isAscendent eq true) ? false : true}">
					<c:if test="${requestScope.orderby eq 'discontinued'}">
						<c:choose>
							<c:when test="${requestScope.isAscendent eq true}"> &dtrif;</c:when>
							<c:otherwise> &utrif;</c:otherwise>
						</c:choose>
					</c:if>
					Discontinued date
					</m:link></th>
			<th><m:link target="computers"
					page="${requestScope.page.number}"
					limit="${requestScope.page.size}" search="${requestScope.search}"
					orderby="companyName" isAscendent="${(requestScope.orderby eq 'companyName' and requestScope.isAscendent eq true) ? false : true}">
					<c:if test="${requestScope.orderby eq 'companyName'}">
						<c:choose>
							<c:when test="${requestScope.isAscendent eq true}"> &dtrif;</c:when>
							<c:otherwise> &utrif;</c:otherwise>
						</c:choose>
					</c:if>
					Company
					</m:link></th>

		</tr>
	</thead>
	<!-- Browse attribute computers -->
	<tbody id="results">
		<c:forEach var="computerDTO" items="${requestScope.page.content}">
			<tr>
				<td class="editMode"><input type="checkbox" name="cb"
					class="cb" value="${computerDTO.id}"></td>
				<td><m:link target="computer-edit" edit="${computerDTO.id}">${computerDTO.name}</m:link></td>
				<td>${computerDTO.introduced}</td>
				<td>${computerDTO.discontinued}</td>
				<td>${computerDTO.companyName}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
