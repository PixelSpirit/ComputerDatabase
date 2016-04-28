<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Add Computer</h1>
				<form action="http://localhost:8080/cdb/computers" method="POST">
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label>
							<input type="text" class="form-control" id="computerName"
								placeholder="Computer name" name="name">
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label>
							<input type="date" class="form-control" id="introduced"
								placeholder="Introduced date" name="introduced">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label>
							<input type="date" class="form-control" id="discontinued"
								placeholder="Discontinued date" name="discontinued">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label>
							<select class="form-control" id="companyId" name="companyid">
								<option value="0">--</option>
								<c:forEach var="companyDTO" items="${requestScope.allCompanies}">
									<option value="${companyDTO.id}"><c:out value="${companyDTO.name}"></c:out></option>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Add" class="btn btn-primary" id="addButton">
						or <a href="dashboard.html" class="btn btn-default">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>