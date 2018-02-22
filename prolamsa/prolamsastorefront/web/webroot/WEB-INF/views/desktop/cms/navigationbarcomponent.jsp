<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>

<c:set value="${component.styleClass} ${dropDownLayout}" var="bannerClasses"/>
<c:choose>
<c:when test='${component.styleClass ne "right"}'>
<li class="La ${bannerClasses}">
	<cms:component component="${component.link}" evaluateRestriction="true"/>
	<c:if test="${not empty component.navigationNode.children}">
		<ul class="Lb">
			<c:forEach items="${component.navigationNode.children}" var="child">
				<c:if test="${child.visible}">
					<li class="Lb">
						<span class="nav-submenu-title">${child.title}</span>
						<c:forEach items="${child.links}" step="${component.wrapAfter}" varStatus="i">
							<ul class="Lc left_col">
								<c:forEach items="${child.links}" var="childlink" begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
									<cms:component component="${childlink}" evaluateRestriction="true" element="li" class="Lc ${i.count < 2 ? 'left_col' : 'left_col'}"/>
								</c:forEach>
							</ul>
						</c:forEach>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</c:if>
</li>
</c:when>
<c:otherwise>
<li class="La ${bannerClasses}">
	<cms:component component="${component.link}" evaluateRestriction="true"/>
	<c:if test="${not empty component.navigationNode.children}">
		<ul class="Lb">
			<c:forEach items="${component.navigationNode.children}" var="child">
				<c:if test="${child.visible}">
					<li class="Lb">
						<span class="nav-submenu-title">${child.title}</span>
						<c:forEach items="${child.links}" step="${component.wrapAfter}" varStatus="i">
							<ul class="Lc left_col">
								<c:forEach items="${child.links}" var="childlink" begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
									<c:choose>
										<c:when test="${childlink.uid  eq 'MY_STUFF_OrderHistoryCategoryLink'}">
											<c:if test="${baseStore.showOrdersAndQuotes eq true}">
												<cms:component component="${childlink}" evaluateRestriction="true" element="li" class="Lc ${i.count < 2 ? 'left_col' : 'left_col'}"/>
											</c:if>
										</c:when>
										<c:when test="${childlink.uid  eq 'MY_STUFF_AccountBalanceCategoryLink'}">
											<c:if test="${baseStore.showAccountBalance eq true}">
												<cms:component component="${childlink}" evaluateRestriction="true" element="li" class="Lc ${i.count < 2 ? 'left_col' : 'left_col'}"/>
											</c:if>
										</c:when>
										<c:when test="${childlink.uid  eq 'MY_STUFF_PendingOrdersCategoryLink'}">
											<c:if test="${baseStore.showBackorder eq true}">
												<cms:component component="${childlink}" evaluateRestriction="true" element="li" class="Lc ${i.count < 2 ? 'left_col' : 'left_col'}"/>
											</c:if>
										</c:when>
										<c:when test="${childlink.uid  eq 'MY_STUFF_RelatedDocumentsCategoryLink'}">
											<c:if test="${baseStore.showDocumentSearch eq true}">
												<cms:component component="${childlink}" evaluateRestriction="true" element="li" class="Lc ${i.count < 2 ? 'left_col' : 'left_col'}"/>
											</c:if>
										</c:when>
										<c:when test="${childlink.uid  eq 'NoticesLink'}">
											<c:if test="${baseStore.showNoticesModule eq true}">
												<cms:component component="${childlink}" evaluateRestriction="true" element="li" class="Lc ${i.count < 2 ? 'left_col' : 'left_col'}"/>
											</c:if>
										</c:when>
										<c:otherwise>
											<cms:component component="${childlink}" evaluateRestriction="true" element="li" class="Lc ${i.count < 2 ? 'left_col' : 'left_col'}"/>
										</c:otherwise>
									</c:choose>
									
								</c:forEach>
							</ul>
						</c:forEach>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</c:if>
</li>
</c:otherwise>
</c:choose>