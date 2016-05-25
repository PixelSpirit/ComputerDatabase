<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box" >
				<h1>Edit Computer</h1>
				<form action="http://localhost:8080/cdb/computer-edit" method="POST">
					<fieldset>
						<input type="hidden" name="id" id="id" value="${requestScope.computerToEdit.id}">
						<div class="form-group">
							<label for="computerName">Computer name</label>
							<input type="text" class="form-control" id="computerName"
								placeholder="Computer name" name="name" value="${requestScope.computerToEdit.name}">
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label>
							<input type="date" class="form-control" id="introduced"
								placeholder="Introduced date" name="introduced" value="${requestScope.computerToEdit.introduced}">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label>
							<input type="date" class="form-control" id="discontinued"
								placeholder="Discontinued date" name="discontinued" value="${requestScope.computerToEdit.discontinued}">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label>
							<select class="form-control" id="companyId" name="companyid">
								<option value="0">--</option>
								<c:forEach var="companyDTO" items="${requestScope.allCompanies}">
									<option value="${companyDTO.id}"
										<c:if test="${companyDTO.id eq requestScope.computerToEdit.companyId}">
											selected
										</c:if>
									> 
										<c:out value="${companyDTO.name}"/>
									</option>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Update" class="btn btn-primary" id="addButton">
						or <a href="http://localhost:8080/cdb/computers" class="btn btn-default">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>