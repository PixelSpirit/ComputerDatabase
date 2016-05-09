<%@ tag body-content="empty" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="number" type="java.lang.Integer" required="true"%>
<%@ attribute name="maxNumber" type="java.lang.Integer" required="true"%>
<%@ attribute name="size" type="java.lang.Integer" required="true"%>
<%@ attribute name="search" required="false"%>
<%@ attribute name="orderby" required="false"%>
<%@ attribute name="direction" required="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>

<ul class="pagination">

	<!-- Previous Button -->
	<c:if test="${number > 0}">
		<li><m:link limit="${size}" page="${number - 1}"
				target="computers">
				<span aria-hidden="true">&laquo;</span>
			</m:link></li>
	</c:if>

	<!-- Pages Button -->
	<c:choose>

		<c:when test="${maxNumber <= 5}">
			<c:forEach var="i" begin="0" end="${maxNumber}" step="1">
				<li class="${(number == i) ? 'active' : ''}"><m:link
						limit="${size}" page="${i}" search="${search}"
						orderby="${orderby}" direction="${direction}"
						target="computers">
						${i + 1}
					</m:link></li>
			</c:forEach>
		</c:when>

		<c:otherwise>

			<c:if test="${number > 2 }">
				<li><m:link limit="${size}" page="0" orderby="${orderby}"
						direction="${direction}" target="computers">
						1
					</m:link></li>
			</c:if>

			<c:if test="${number > 3}">
				<li><span aria-hidden="true">&hellip;</span></li>
			</c:if>

			<c:if test="${number > 1}">
				<li><m:link limit="${size}" page="${number - 2}"
						search="${search}" orderby="${orderby}"
						direction="${direction}" target="computers">
						${number - 1}
					</m:link></li>
			</c:if>

			<c:if test="${number > 0}">
				<li><m:link limit="${size}" page="${number - 1}"
						search="${search}" orderby="${orderby}"
						direction="${direction}" target="computers">
						${number}
					</m:link></li>
			</c:if>

			<li class="active"><m:link limit="${size}" page="${number}"
					search="${search}" orderby="${orderby}"
					direction="${direction}" target="computers">
				${number + 1}
			</m:link></li>

			<c:if test="${number < maxNumber}">
				<li><m:link limit="${size}" page="${number + 1}"
						search="${search}" orderby="${orderby}"
						direction="${direction}" target="computers">
						${number + 2}
					</m:link></li>
			</c:if>

			<c:if test="${number < maxNumber - 1}">
				<li><m:link limit="${size}" page="${number + 2}"
						search="${search}" orderby="${orderby}"
						direction="${direction}" target="computers">
						${number + 3}
					</m:link></li>
			</c:if>

			<c:if test="${number < maxNumber - 3}">
				<li><span aria-hidden="true">&hellip;</span></li>
			</c:if>

			<c:if test="${number < maxNumber - 2 }">
				<li><m:link limit="${size}" page="${maxNumber}"
						search="${search}" orderby="${orderby}"
						direction="${direction}" target="computers">
						${maxNumber + 1}
					</m:link></li>
			</c:if>

		</c:otherwise>
	</c:choose>

	<!-- Next Button -->
	<c:if test="${number < maxNumber}">
		<li><m:link limit="${size}" page="${number + 1}"
				search="${search}" orderby="${orderby}" direction="${direction}"
				target="computers">
				<span aria-hidden="true">&raquo;</span>
			</m:link></li>
	</c:if>


</ul>