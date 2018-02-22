<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ attribute name="whislists" required="true" type="java.util.Collection" %>

<fmt:setLocale value="en_US" scope="session"/>

	<spring:theme code="product.list.header.wt-piece" var="wtPieceLabel" />
		
	<c:if test="${fn:contains(unit.code,'kg')}">
		<c:set value="(Kg)" var="wtLabel" />
		<c:set value="(Kg)" var="wtLabel2" />
	</c:if>
	
	<c:if test="${fn:contains(unit.code,'mt')}">
		<c:set value="(Kg)" var="wtLabel" />
		<c:set value="(Mts)" var="wtLabel2" />
	</c:if>
	
	<c:if test="${fn:contains(unit.code,'bun_kg')}">
		<c:set value="(Kg)" var="wtLabel" />
		<c:set value="(Paq)" var="wtLabel2" />
	</c:if>
	
	<c:if test="${fn:contains(unit.code,'pc_kg')}">
		<c:set value="(Kg)" var="wtLabel" />
		<c:set value="(Pza)" var="wtLabel2" />
	</c:if>
	
	<c:if test="${fn:contains(unit.code,'lb')}">
		<c:set value="(Lb)" var="wtLabel" />
		<c:set value="(Lb)" var="wtLabel2" />
	</c:if>
	
	<c:if test="${fn:contains(unit.code,'ft')}">
		<c:set value="(Lb)" var="wtLabel" />
		<c:set value="(Ft)" var="wtLabel2" />
	</c:if>
	
	<c:if test="${fn:contains(unit.code,'bun_lb')}">
		<c:set value="(Lb)" var="wtLabel" />
		<c:set value="(Bun)" var="wtLabel2" />
	</c:if>
	
	<c:if test="${fn:contains(unit.code,'pc_lb')}">
		<c:set value="(Lb)" var="wtLabel" />
		<c:set value="(Pc)" var="wtLabel2" />
	</c:if>
	
	<c:if test="${fn:contains(unit.code,'ton')}">
		<c:set value="(Ton)" var="wtLabel" />
		<c:set value="(Ton)" var="wtLabel2" />
	</c:if>
	
	<table id="productListTable">
	<tr class="firstrow">
	    <!-- NEORIS_CHANGE #107 -->			
		<c:if test="${selLocation != null}">
	       <td width="5"></td>
	    </c:if>
	    
		<!-- NEORIS_CHANGE #78 -->
		<c:if test="${product.code == null }">
			<td class="col_center"><spring:theme code="product.list.header.part" /></td>
			<td class="col_center"><spring:theme code="product.list.header.description" /></td>
		</c:if>
		<td class="col_center"><spring:theme code="product.list.header.pcs-bundle" /></td>
		<td class="col_center"><spring:theme code="product.list.header.wt-piece" /><br/>${wtLabel}</td>
		<td class="attribute_col"><spring:theme code="product.list.header.wt-bundle" /><br/>${wtLabel}</td>
		<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
		<td class="location_col"><spring:theme code="product.list.header.available-stock"/><br/>${wtLabel2}</td>
		</sec:authorize>
		<td class="col_center"><spring:theme code="product.list.header.location" /></td>
		<td class="col_center"><spring:theme code="product.list.header.quantity" /></td>
		<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
		<td class="col_center"><spring:theme code="product.list.header.rolling-schedule-week1"/><br/><spring:theme code="product.list.header.rolling-schedule-week2"/> </td>
		<td class="col_center"><spring:theme code="product.list.header.order-rolling-units1" /><br/><spring:theme code="product.list.header.order-rolling-units2"/></td>
		</sec:authorize>
		<td class="col_center"></td>
	</tr>

	<c:forEach items="${whislists}" var="eachWishlist" varStatus="status">
		<c:set var="inventoryEntry" value="${eachWishlist.product.inventoryEntry}" />
		<c:set var="inventory" value="${inventoryEntry.availableStockBundles}" />

		<c:set value="0" var="isFavorite" />
		<c:if test="${eachWishlist.product.isFavoriteProduct eq true}">
    		<c:set value="1" var="isFavorite"></c:set>
		</c:if>

		<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
			<c:set var="inventory" value="${inventoryEntry.availableStockBundlesInternal}" />
		</sec:authorize>

		<tr class="prolamsa_product_row" productCode="${eachWishlist.product.code}" productLocation="${inventoryEntry.location}">
			
			<!-- NEORIS_CHANGE #107 -->			
			<c:if test="${selLocation != null}">
				<td width="5" style="vertical-align: text-top; padding-top: 5px; height: 20px;" >	
				   <input type="radio" name="selProduct" value="" onClick="getURLCodeLocation('/store/p/${product.code}?selLocation=${inventoryEntry.location}')" 
				           <c:if test="${inventoryEntry.location eq selLocation}">checked</c:if>
				            />
				</td>
			</c:if>
			
			<!-- NEORIS_CHANGE #78 -->								
				<td class="text_top" style="vertical-align: text-top; padding-top: 5px;"><a href="${contextPath}${product.url}">${eachWishlist.product.visibleCode}</a></td>
				<td class="product_list_product_code text_top" style="vertical-align: text-top; padding-top: 5px; max-width: 200px;">
					 <span class="productDescription" style="vertical-align: text-top; " productCode="${eachWishlist.product.code}" onclick="quickView(this, event)" onmouseout="closePop();"><spring:theme code="${eachWishlist.product.name}"/></span> 
				</td>
				
				<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
					<fmt:formatNumber pattern="###,###,###,###,##0"  value="${eachWishlist.product.piecesPerBundle}"/>
				</td>
				
				<c:set var="weightPerPiece" value="" />
				<c:set var="weightPerBundle" value="" />
				<c:set var="availableStock" value="" />
				<c:choose>
					<c:when test="${fn:contains(unit.code,'bun_kg') }">
						<c:set var="weightPerPiece"  value="${ eachWishlist.product.pcKgEquiv}"/>
						<c:set var="weightPerBundle"  value="${ eachWishlist.product.bundleKgEquiv }"/>
						<c:set var="availableStock"  value="${inventory}" />
					</c:when>
					
					<c:when test="${fn:contains(unit.code,'pc_kg')  }">
						<c:set var="weightPerPiece"  value="${eachWishlist.product.pcKgEquiv }"/>
						<c:set var="weightPerBundle"  value="${eachWishlist.product.bundleKgEquiv }"/>
						<c:set var="availableStock"  value="${inventory*eachWishlist.product.piecesPerBundle}"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'kg')}">
						<c:set var="weightPerPiece"  value="${eachWishlist.product.pcKgEquiv }"/>
						<c:set var="weightPerBundle"  value="${eachWishlist.product.bundleKgEquiv }"/>
						<c:set var="availableStock"  value="${inventory*eachWishlist.product.bundleKgEquiv}"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'mt')}">
						<c:set var="weightPerPiece"  value="${eachWishlist.product.pcKgEquiv }"/>
						<c:set var="weightPerBundle"  value="${eachWishlist.product.bundleKgEquiv }"/>
						<c:set var="availableStock"  value="${inventory*eachWishlist.product.mtEquiv}"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'bun_lb') }">
						<c:set var="weightPerPiece"  value="${eachWishlist.product.pcLbEquiv }"/>
						<c:set var="weightPerBundle"  value="${eachWishlist.product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory*eachWishlist.product.bundleLbEquiv}"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'pc_lb') }">
						<c:set var="weightPerPiece"  value="${eachWishlist.product.bundleLbEquiv / eachWishlist.product.piecesPerBundle}"/>
						<c:set var="weightPerBundle"  value="${eachWishlist.product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory*eachWishlist.product.piecesPerBundle}"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'lb')}">
						<c:set var="weightPerPiece"  value="${eachWishlist.product.bundleLbEquiv / eachWishlist.product.piecesPerBundle}"/>
						<c:set var="weightPerBundle"  value="${eachWishlist.product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory*eachWishlist.product.bundleLbEquiv}"/>
					</c:when>

					<c:when test="${fn:contains(unit.code,'ft')}">
						<c:set var="weightPerPiece"  value="${eachWishlist.product.bundleLbEquiv / eachWishlist.product.piecesPerBundle}"/>
						<c:set var="weightPerBundle"  value="${eachWishlist.product.bundleLbEquiv }"/>
						<c:set var="availableStock"  value="${inventory*eachWishlist.product.ftEquiv}"/>
					</c:when>
				
					<c:when test="${fn:contains(unit.code,'ton')}">
						<c:set var="weightPerPiece"  value="${ eachWishlist.product.tnEquiv / eachWishlist.product.piecesPerBundle}"/>
						<c:set var="weightPerBundle"  value="${ eachWishlist.product.tnEquiv}"/>
						<c:set var="availableStock" value="${inventory*eachWishlist.product.tnEquiv}"/>
					</c:when>
				</c:choose>

				<td class="col_right text_top" style="vertical-align: text-top; padding-top: 5px;">
					<fmt:formatNumber pattern="${weightNumberPattern}"  value="${weightPerPiece}"/>
				</td>
				<td class="col_right text_top" style="vertical-align: text-top; padding-top: 5px;">
					<fmt:formatNumber pattern="${weightNumberPattern}"  value="${weightPerBundle}"/>
				</td>
			<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
				<c:choose>
					<c:when test="${availableStock eq 0 }">
						<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px; color: red;">
							<fmt:formatNumber var="availableStockLabel" pattern="${quantityPattern }" value="${availableStock}" />
							<c:if test="${availableStock eq 0 }">
								<c:set var="availableStockLabel" value="----" />
							</c:if>
		
							<c:out value="${availableStockLabel}" />
						</td>
					</c:when>
					<c:otherwise> 
						<td class="col_right text_top" style="vertical-align: text-top; padding-top: 5px;">
							<fmt:formatNumber var="availableStockLabel" pattern="${quantityPattern }" value="${availableStock}" />
							<c:if test="${availableStock eq 0 }">
								<c:set var="availableStockLabel" value="-" />
							</c:if>
		
							<c:out value="${availableStockLabel}" />
						</td>
					</c:otherwise>	
				</c:choose>
			</sec:authorize>
			<td class="text_top" style="vertical-align: text-top; padding-top: 5px;">
				<spring:theme code="${inventoryEntry.location}" />
			</td>

			<c:set var="availableStockBundlesCol" value="${inventoryEntry.availableStockBundlesCol}" />

			<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
				<c:set var="availableStockBundlesCol" value="${inventoryEntry.availableStockBundlesColInternal}" />
			</sec:authorize>
			<c:set var="noInventoryRoleBundlesCol" value="" />
			<sec:authorize ifAnyGranted="ROLE_PROLAMSA_NO_INVENTORY">
				<c:set var="noInventoryRoleBundlesCol" value="${inventoryEntry.noInventoryRoleBundlesCol}" />
			</sec:authorize>
			
			<!-- NEORIS_CHANGE #105 -->
			<c:choose>
				<c:when test="${not empty noInventoryRoleBundlesCol}">
					<style type="text/css">
						<!-- 
						table td select {
							    width: 50%;
							}
						-->
						</style>
					<c:set value="0" var="selectedQuantity"/>
					<c:choose>
						<c:when test="${not empty eachWishlist.product.quantityExcel }">
							<c:set value="${eachWishlist.product.quantityExcel }" var="selectedQuantity"/>
							<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
								<product:productInventoryBundleSelectorExcel bundleName="stockQty" product="${product}" quantityCol="${noInventoryRoleBundlesCol}" selectedQuantity="${eachWishlist.product.quantityExcel}" />
							</td>
						</c:when>
						<c:otherwise>
							<td class="col_center text_top"  style="vertical-align: text-top; padding-top: 5px;" >
								<product:productInventoryBundleSelector   bundleName="stockQty" quantityCol="${noInventoryRoleBundlesCol}" selectedQuantity="${ selectedQuantity}" />
							</td>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${not empty eachWishlist.product.quantityExcel }">
						<c:set value="${eachWishlist.product.quantityExcel }" var="selectedQuantity"/>
					</c:if>
					
					
				</c:when>
				<c:when test="${availableStock eq 0 }">
					<td class="col_center text_top"  style="vertical-align: text-top; padding-top: 5px;" >
						<product:productInventoryBundleSelector   bundleName="stockQty" quantityCol="${x}" selectedQuantity="0" />
					</td>
				</c:when>
				<c:when test="${empty eachWishlist.product.quantityExcel}">
					<td class="col_center text_top"  style="vertical-align: text-top; padding-top: 5px;" >
						<product:productInventoryBundleSelector   bundleName="stockQty" quantityCol="${availableStockBundlesCol}" selectedQuantity="0" />
					</td>
				</c:when>
				<c:otherwise>
					<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
						<product:productInventoryBundleSelectorExcel bundleName="stockQty" product="${product}" quantityCol="${availableStockBundlesCol}" selectedQuantity="${eachWishlist.product.quantityExcel}" />
					</td>
				</c:otherwise>
			</c:choose>
			
			
			<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
				<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
					<product:productInventoryRollingScheduleSelector dateFormat="MM-dd-yyyy" dates="${inventoryEntry.rollingScheduleDates}"/>
				</td>
				 <c:choose>				
					<c:when test="${empty inventoryEntry.rollingScheduleDates}">
						<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px; padding-bottom: 5px;">					   			
							 <select id="rollingScheduleQty" class="col_center text_top"  disabled="disabled">
							    <option value="0">-</option>
						    </select>							    
					    </td>
					</c:when>
					<c:otherwise>
						<c:choose>
						
							<c:when test="${ availableStock eq 0 and not empty eachWishlist.product.quantityExcel  }">
								<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px; padding-bottom: 5px;">
									<product:productInventoryBundleSelectorExcel  product="${ product}" bundleName="rollingScheduleQty" quantityCol="${inventoryEntry.rollingScheduleBundlesCol}" selectedQuantity="${eachWishlist.product.quantityExcel  }" />
								</td> 
							</c:when>
							<c:otherwise>
							<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px; padding-bottom: 5px;">
								<product:productInventoryBundleSelector bundleName="rollingScheduleQty" quantityCol="${inventoryEntry.rollingScheduleBundlesCol}" selectedQuantity="${selectedQty }" /> 
						    </td> 	
							</c:otherwise>
						</c:choose>
					
									    
					</c:otherwise>
				</c:choose>		
			</sec:authorize>
           	<td class="col_center">
           		<div class="favorite_container">
	           		<div id="loading_${eachWishlist.product.code}" class="favorite_loading" style="display: none;"></div>
	           		<div id="star_${eachWishlist.product.code}" class="favorite_star" data-score="${isFavorite}" data-productCode="${eachWishlist.product.code}"></div>
           		</div>
           	</td>	
           	<td>
           	 <input type="text" id="stockOuts" name="stockOuts" style="visibility: hidden; display: none;" value="${inventoryEntry.stockOuts}">
           	</td>		
		</tr>
	</c:forEach>
</table>