<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Add Computer</h1>
				<form:form method="POST" commandName="dtoComputer" action="add">
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label>
							<form:input type="text" class="form-control" id="computerName"
								placeholder="Computer name" path="name"/>
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label>
							<form:input type="text" class="form-control" id="introduced"
								placeholder="Introduced date" path="introduced"/>
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label>
							<form:input type="text" class="form-control" id="discontinued"
								placeholder="Discontinued date" path="discontinued"/>
						</div>
						<div class="form-group">
							<label for="companyId">Company</label>
							<form:select class="form-control" id="companyId" path="companyId">
								<option value="0">--</option>
								<c:forEach var="companyDTO" items="${requestScope.allCompanies}">
									<option value="${companyDTO.id}"><c:out value="${companyDTO.name}"></c:out></option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Add" class="btn btn-primary" id="addButton"/>
						or <a href="${pageContext.request.contextPath}/computer/list" class="btn btn-default">Cancel</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>