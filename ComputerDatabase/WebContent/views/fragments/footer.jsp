<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>
<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<ul class="pagination">
			<li>
				<a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
			</li>
			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
		</ul>


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