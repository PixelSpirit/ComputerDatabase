<%@ tag body-content="empty" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="number" type="java.lang.Integer" required="true"%>
<%@ attribute name="maxNumber" type="java.lang.Integer" required="true"%>
<%@ attribute name="size" type="java.lang.Integer" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>

<ul class="pagination">

	<!-- Previous Button -->
	<c:if test="${number > 0}">
		<li><m:link limit="${size}"
				page="${number - 1}" target="computers">
				<span aria-hidden="true">&laquo;</span>
			</m:link></li>
	</c:if>

	<!-- Pages Button -->
	<c:choose>

		<c:when test="${maxNumber <= 5}">
			<c:forEach var="i" begin="0" end="${maxNumber}"
				step="1">
				<li><m:link limit="${size}" page="${i}"
						target="computers">
						<c:choose>
							<c:when test="${number == i}">
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

			<c:if test="${number > 3 }">
				<li><m:link limit="${size}" page="1" target="computers">
					<c:out value="1" />
				</m:link></li>
			</c:if>

			<c:if test="${number > 3}">
				<li><span aria-hidden="true">&hellip;</span></li>
			</c:if>

			<c:if test="${number > 1}">
				<li><m:link limit="${size}" page="${number - 2}" target="computers">
					<c:out value="${number - 2}" />
				</m:link></li>
			</c:if>

			<c:if test="${number > 0}">
				<li><m:link limit="${size}" page="${number - 1}" target="computers">
					<c:out value="${number - 1}" />
				</m:link></li>
			</c:if>

			<li><m:link limit="${size}" page="${number}" target="computers">
				<b><i><c:out value="${number}" /></i></b>
			</m:link></li>

			<c:if test="${number < maxNumber}">
				<li><m:link limit="${size}" page="${number + 1}" target="computers">
					<c:out value="${number + 1}" />
				</m:link></li>
			</c:if>

			<c:if test="${number < maxNumber - 1}">
				<li><m:link limit="${size}" page="${number + 2}" target="computers">
					<c:out value="${number + 2}" />
				</m:link></li>
			</c:if>

			<c:if test="${number < maxNumber - 3}">
				<li><span aria-hidden="true">&hellip;</span></li>
			</c:if>

			<c:if test="${number < maxNumber - 3 }">
				<li><m:link limit="${size}" page="${maxNumber}" target="computers">
					<c:out value="${maxNumber}" />
				</m:link></li>
			</c:if>

		</c:otherwise>
	</c:choose>

	<!-- Next Button -->
	<c:if test="${number < maxNumber}">
		<li><m:link limit="${size}"
				page="${number + 1}" target="computers">
				<span aria-hidden="true">&raquo;</span>
			</m:link></li>
	</c:if>


</ul>