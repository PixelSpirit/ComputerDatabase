<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>
<footer class="navbar-fixed-bottom">
	<div class="container text-center">
	
		<!-- Page Buttons -->
		<ul class="pagination">
		
			<!-- Previous Button -->
			<c:if test="${requestScope.page.number > 0}">
				<li><a href=<m:link limit="${requestScope.page.size}" page="${requestScope.page.number - 1}" target="Computers" /> aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>
			
			<!-- Pages Button -->
			<c:choose>
			
				<c:when test="${requestScope.page.maxNumber <= 5}">
					<c:forEach var="i" begin="0" end="${requestScope.page.maxNumber}" step="1">
						<li><a href=<m:link limit="${requestScope.page.size}" page="${i}" target="Computers" /> >
							<c:out value="${i}" />
						</a></li>
					</c:forEach>
				</c:when>
				
				<c:otherwise>

					<c:if test="${requestScope.page.number > 2}">
						<li><span aria-hidden="true">&hellip;</span></li>
					</c:if>
					
					<c:if test="${requestScope.page.number > 1}">
						<li><a href=<m:link limit="${requestScope.page.size}" page="${requestScope.page.number - 2}" target="Computers" /> >
							<c:out value="${requestScope.page.number - 2}" />
						</a></li>
					</c:if>
					
					<c:if test="${requestScope.page.number > 0}">
						<li><a href=<m:link limit="${requestScope.page.size}" page="${requestScope.page.number - 1}" target="Computers" /> >
							<c:out value="${requestScope.page.number - 1}" />
						</a></li>
					</c:if>
					
					<li><a href=<m:link limit="${requestScope.page.size}" page="${requestScope.page.number}" target="Computers" /> >
							<c:out value="${requestScope.page.number}" />
					</a></li>
					
					<c:if test="${requestScope.page.number < requestScope.page.maxNumber}">
						<li><a href=<m:link limit="${requestScope.page.size}" page="${requestScope.page.number + 1}" target="Computers" /> >
							<c:out value="${requestScope.page.number + 1}" />
						</a></li>
					</c:if>
					
					<c:if test="${requestScope.page.number < requestScope.page.maxNumber - 1}">
						<li><a href=<m:link limit="${requestScope.page.size}" page="${requestScope.page.number + 2}" target="Computers" /> >
							<c:out value="${requestScope.page.number + 2}" />
						</a></li>
					</c:if>
					
					<c:if test="${requestScope.page.number < requestScope.page.maxNumber - 2}">
						<li><span aria-hidden="true">&hellip;</span></li>
					</c:if>

				</c:otherwise>
			</c:choose>
			
			<!-- Next Button -->
			<c:if test="${requestScope.page.number < requestScope.page.maxNumber}">
				<li><a href=<m:link limit="${requestScope.page.size}" page="${requestScope.page.number + 1}" target="Computers" /> aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
			
			
		</ul>



		<!-- Limit Buttons -->
		<div class="btn-group btn-group-sm pull-right" role="group">
			<a href=<m:link limit="10" page="${requestScope.page.number}" target="Computers" /> >
				<button type="button" class="btn btn-default">10</button>
			</a>
			
			<a href=<m:link limit="50" page="${requestScope.page.number}" target="Computers" /> >
				<button type="button" class="btn btn-default">50</button>
			</a>
			
			<a href=<m:link limit="100" page="${requestScope.page.number}" target="Computers" /> >
				<button type="button" class="btn btn-default">100</button>
			</a>

		</div>
	</div>

</footer>