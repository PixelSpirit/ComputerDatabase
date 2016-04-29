<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>
<footer class="navbar-fixed-bottom">
	<div class="container text-center">

		<!-- Page Buttons -->
		<ul class="pagination">

			<!-- Previous Button -->
			<c:if test="${requestScope.page.number > 0}">
				<li><m:link limit="${requestScope.page.size}"
						page="${requestScope.page.number - 1}" target="computers">
						<span aria-hidden="true">&laquo;</span>
					</m:link></li>
			</c:if>

			<!-- Pages Button -->
			<c:choose>

				<c:when test="${requestScope.page.maxNumber <= 5}">
					<c:forEach var="i" begin="0" end="${requestScope.page.maxNumber}"
						step="1">
						<li><m:link limit="${requestScope.page.size}" page="${i}"
								target="computers">
								<c:choose>
									<c:when test="${requestScope.page.number == i}">
										<b><i><c:out value="${i}" /></i></b>
									</c:when>
									<c:otherwise>
										<c:out value="${i}" />
									</c:otherwise>
								</c:choose>
							</m:link></li>
					</c:forEach>
				</c:when>

				<c:otherwise>
				
					<c:if test="${requestScope.page.number > 3 }">
						<li><m:link limit="${requestScope.page.size}" page="1" target="computers">
								<c:out value="1" />
							</m:link></li>
					</c:if>

					<c:if test="${requestScope.page.number > 3}">
						<li><span aria-hidden="true">&hellip;</span></li>
					</c:if>

					<c:if test="${requestScope.page.number > 1}">
						<li><m:link limit="${requestScope.page.size}"
								page="${requestScope.page.number - 2}" target="computers">
								<c:out value="${requestScope.page.number - 2}" />
							</m:link></li>
					</c:if>

					<c:if test="${requestScope.page.number > 0}">
						<li><m:link limit="${requestScope.page.size}"
								page="${requestScope.page.number - 1}" target="computers">
								<c:out value="${requestScope.page.number - 1}" />
							</m:link></li>
					</c:if>

					<li><m:link limit="${requestScope.page.size}"
							page="${requestScope.page.number}" target="computers">
							<b><i><c:out value="${requestScope.page.number}" /></i></b>
						</m:link></li>

					<c:if
						test="${requestScope.page.number < requestScope.page.maxNumber}">
						<li><m:link limit="${requestScope.page.size}"
								page="${requestScope.page.number + 1}" target="computers">
								<c:out value="${requestScope.page.number + 1}" />
							</m:link></li>
					</c:if>

					<c:if
						test="${requestScope.page.number < requestScope.page.maxNumber - 1}">
						<li><m:link limit="${requestScope.page.size}"
								page="${requestScope.page.number + 2}" target="computers">
								<c:out value="${requestScope.page.number + 2}" />
							</m:link></li>
					</c:if>

					<c:if
						test="${requestScope.page.number < requestScope.page.maxNumber - 3}">
						<li><span aria-hidden="true">&hellip;</span></li>
					</c:if>
					
					<c:if test="${requestScope.page.number < requestScope.page.maxNumber - 3 }">
						<li><m:link limit="${requestScope.page.size}" page="${requestScope.page.maxNumber}" target="computers">
								<c:out value="${requestScope.page.maxNumber}" />
							</m:link></li>
					</c:if>

				</c:otherwise>
			</c:choose>

			<!-- Next Button -->
			<c:if
				test="${requestScope.page.number < requestScope.page.maxNumber}">
				<li><m:link limit="${requestScope.page.size}"
						page="${requestScope.page.number + 1}" target="computers">
						<span aria-hidden="true">&raquo;</span>
					</m:link></li>
			</c:if>


		</ul>



		<!-- Limit Buttons -->
		<div class="btn-group btn-group-sm pull-right" role="group">
			<m:link limit="10" page="${requestScope.page.number}"
				target="computers">
				<button type="button" class="btn btn-default">10</button>
			</m:link>

			<m:link limit="50" page="${requestScope.page.number}"
				target="computers">
				<button type="button" class="btn btn-default">50</button>
			</m:link>

			<m:link limit="100" page="${requestScope.page.number}"
				target="computers">
				<button type="button" class="btn btn-default">100</button>
			</m:link>

		</div>
	</div>

</footer>