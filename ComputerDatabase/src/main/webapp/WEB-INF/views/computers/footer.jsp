<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>
<footer class="navbar-fixed-bottom">
	<div class="container text-center">

		<!-- Page Buttons -->
		<m:pagination maxNumber="${requestScope.page.maxNumber}"
			size="${requestScope.page.size}" orderby="${requestScope.orderby}"
			direction="${requestScope.direction }"
			number="${requestScope.page.number}" />

		<!-- Limit Buttons -->
		<div class="btn-group btn-group-sm pull-right" role="group">
			<m:link limit="10" page="${requestScope.page.number}"
				orderby="${requestScope.orderby}"
				direction="${requestScope.direction}" target="computers">
				<button type="button" class="btn btn-default">10</button>
			</m:link>
			<m:link limit="50" page="${requestScope.page.number}"
				orderby="${requestScope.orderby}"
				direction="${requestScope.direction}" target="computers">
				<button type="button" class="btn btn-default">50</button>
			</m:link>

			<m:link limit="100" page="${requestScope.page.number}"
				orderby="${requestScope.orderby}"
				direction="${requestScope.direction}" target="computers">
				<button type="button" class="btn btn-default">100</button>
			</m:link>
		</div>
	</div>

</footer>