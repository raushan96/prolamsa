<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>

<div id="footer">
<%--	<ul class="Fa">  --%>
	<div class="grid-container footer-wrap">
		<c:forEach items="${navigationNodes}" var="node">
			<c:if test="${node.visible}">
				<div class="grid-33">
					<ul class="Fb">
						<h3>${node.title}</h3>
						<c:forEach items="${node.links}" step="${component.wrapAfter}" varStatus="i">
							<ul class="Fc ${i.count < 2 ? 'left_col' : 'right_col'}">
								<c:forEach items="${node.links}" var="childlink" begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
									<c:choose>
										<c:when test="${childlink.uid  eq 'NoticesLink'}">
											<c:if test="${baseStore.showNoticesModule eq true}">
												<cms:component component="${childlink}" evaluateRestriction="true" element="li" class="Fc ${i.count < 2 ? 'left_col' : 'right_col'}"/>
											</c:if>
										</c:when>
										<c:otherwise>
											<cms:component component="${childlink}" evaluateRestriction="true" element="li" class="Fc ${i.count < 2 ? 'left_col' : 'right_col'}"/>
										</c:otherwise>
									</c:choose>
								
								
								</c:forEach>
							</ul>
						</c:forEach>
					</ul>
				</div>
			</c:if>
		</c:forEach>
	</div>
	<div id="copyright" class="grid-100">
		<p style="color: rgba(0, 0, 0, 0)">.</p>
	</div>
</div>