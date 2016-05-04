<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>

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
				</span>
			</th>
			<th>
				<m:link target="computers" >
					Computer name
				</m:link>
			</th>
			<th>Introduced date</th>
			<!-- Table header for Discontinued Date -->
			<th>Discontinued date</th>
			<!-- Table header for Company -->
			<th>Company</th>

		</tr>
	</thead>
	<!-- Browse attribute computers -->
	<tbody id="results">
		<c:forEach var="computerDTO" items="${requestScope.page.content}">
			<tr>
				<td class="editMode">
					<input type="checkbox" name="cb" class="cb" value="${computerDTO.id}">
				</td>
				<td><m:link target="computer-edit" edit="${computerDTO.id}">${computerDTO.name}</m:link></td>
				<td>${computerDTO.introduced}</td>
				<td>${computerDTO.discontinued}</td>
				<td>${computerDTO.companyName}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
