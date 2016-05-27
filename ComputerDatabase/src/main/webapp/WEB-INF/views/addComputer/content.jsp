<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1><spring:message code="computer.add.addComputer"/></h1>
				<form:form method="POST" commandName="dtoComputer" action="add">
					<fieldset>
						<div class="form-group">
							<label for="computerName"><spring:message code="computer.general.computerName"/></label>
							<form:input type="text" class="form-control" id="computerName" path="name"/>
						</div>
						<div class="form-group">
							<label for="introduced"><spring:message code="computer.general.introducedDate"/></label>
							
							<form:input type="text" class="form-control" id="introduced" path="introduced"/>
						</div>
						<div class="form-group">
							<label for="discontinued"><spring:message code="computer.general.discontinuedDate"/></label>
							<form:input type="text" class="form-control" id="discontinued" path="discontinued"/>
						</div>
						<div class="form-group">
							<label for="companyId"><spring:message code="computer.general.company"/></label>
							<form:select class="form-control" id="companyId" path="companyId">
								<option value="0">--</option>
								<c:forEach var="companyDTO" items="${requestScope.allCompanies}">
									<option value="${companyDTO.id}"><c:out value="${companyDTO.name}"></c:out></option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="<spring:message code="computer.add.add"/>" class="btn btn-primary" id="addButton"/>
						or <a href="${pageContext.request.contextPath}/computer/list" class="btn btn-default"><spring:message code="computer.general.cancel"/></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>