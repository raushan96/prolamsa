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
<%@ attribute name="productList" required="true" type="java.util.Collection" %>

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
		<c:set value="(lb)" var="wtLabel" />
		<c:set value="(Pc)" var="wtLabel2" />
	</c:if>
	
	<c:if test="${fn:contains(unit.code,'ton')}">
		<c:set value="(Ton)" var="wtLabel" />
		<c:set value="(Ton)" var="wtLabel2" />
	</c:if>
	
	<!-- Analyze kinds of products on list -->
	<c:set var="isPageWithAPIProducts" value="${false}" />
	<c:set var="isPageWithNoAPIProducts" value="${false}" />
	<c:forEach items="${productList}" var="product" varStatus="status">
		<c:choose>
			<c:when test="${product.isAPI}">
				<c:set var="isPageWithAPIProducts" value="${true}" />
			</c:when>
			<c:otherwise>
				<c:set var="isPageWithNoAPIProducts" value="${true}" />
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	
	<!-- Section whether API products are included -->
	<c:if test="${isPageWithAPIProducts eq true}">
		<spring:theme code="product.list.add.productsapi.message" />
		<c:set var="inventoryQtyFtAPIMin" value="${inventoryQtyAPIMin}" />
		<c:set var="inventoryQtyFtAPIMax" value="${inventoryQtyAPIMax}" />
		<c:set var="rollingQtyFtAPIMin" value="${rollingQtyAPIMin}" />
		<c:set var="rollingQtyFtAPIMax" value="${rollingQtyAPIMax}" />
		<c:set value="(Ft)" var="wtLabel3" />

		<table id="productListTableAPI" class="productListTable">
			<tr class="firstrow">
				<c:if test="${selLocation != null}">
			       <td width="5"></td>
			    </c:if>
				<c:if test="${product.code == null }">
					<td class="col_center"><spring:theme code="product.list.header.part" /></td>
					<td class="col_center"><spring:theme code="product.list.header.description" /></td>
				</c:if>
				<td class="col_center"><spring:theme code="product.list.header.wt-ft" /><br/>${wtLabel}</td>
				
				<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
					<td class="location_col"><spring:theme code="product.list.header.available-stock"/><br/>${wtLabel3}</td>
				</sec:authorize>
				
				<td class="col_center"><spring:theme code="product.list.header.location" /></td>
				<td class="col_center"><spring:theme code="product.list.header.quantity" /><br/>${wtLabel3}</td>
				
				<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
					<td class="col_center"><spring:theme code="product.list.header.rolling-schedule-week1"/><br/><spring:theme code="product.list.header.rolling-schedule-week2"/> </td>
					<td class="col_center"><spring:theme code="product.list.header.order-rolling-units1" /><br/><spring:theme code="product.list.header.order-rolling-units2"/></td>
				</sec:authorize>
				
				<td class="col_center"></td>
			</tr>
			
			<c:forEach items="${productList}" var="product" varStatus="status">
				<c:if test="${product.isAPI}">
					<c:set var="inventoryEntry" value="${product.inventoryEntry}" />
					<c:set var="inventory" value="${inventoryEntry.availableStockBundles}" />
		
					<c:set value="0" var="isFavorite" />
						<c:if test="${product.isFavoriteProduct eq true}">
    						<c:set value="1" var="isFavorite"></c:set>
					</c:if>

					<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
						<c:set var="inventory" value="${inventoryEntry.availableStockBundlesInternal}" />
					</sec:authorize>
		
					<tr class="prolamsa_product_row" productCode="${product.code}" productLocation="${inventoryEntry.location}" isAPIProduct="${true}">
						
						<!-- product location column -->
						<c:if test="${selLocation != null}">
							<td width="5" style="vertical-align: text-top; padding-top: 5px; height: 20px;" >	
							   <input type="radio" name="selProduct" value="" onClick="getURLCodeLocation('/store/p/${product.code}?selLocation=${inventoryEntry.location}')" 
							           <c:if test="${inventoryEntry.location eq selLocation}">checked</c:if>
							            />
							</td>
						</c:if>
						
						<!-- product code and description columns -->
						<c:if test="${productCode == null }">
							<td class="text_top" style="vertical-align: text-top; padding-top: 5px;"><a href="${contextPath}${product.url}">${product.visibleCode}</a></td>
							<td class="product_list_product_code text_top" style="vertical-align: text-top; padding-top: 5px; max-width: 200px;">
								 <span class="productDescription" style="vertical-align: text-top; " productCode="${product.code}" onclick="quickView(this, event)" onmouseout="closePop();"><spring:theme code="${product.name}"/></span> 
							</td>
						</c:if>
						
						<!-- calculate weight/ft and available stock -->
						<c:set var="weightPerFt" value="" />
						<!-- ft -->
						<c:set var="availableStock"  value="${inventory}" />
						
						<c:choose>
							<c:when test="${fn:contains(unit.code,'bun_kg') }">
								<c:set var="weightPerFt"  value="${product.ftKgEquiv}"/>
							</c:when>
							
							<c:when test="${fn:contains(unit.code,'pc_kg')  }">
								<c:set var="weightPerFt"  value="${product.ftKgEquiv}"/>
							</c:when>
						
							<c:when test="${fn:contains(unit.code,'kg')}">
								<c:set var="weightPerFt"  value="${product.ftKgEquiv}"/>
							</c:when>
						
							<c:when test="${fn:contains(unit.code,'mt')}">
								<c:set var="weightPerFt"  value="${product.ftKgEquiv}"/>
							</c:when>
						
							<c:when test="${fn:contains(unit.code,'bun_lb')}">
								<c:set var="weightPerFt"  value="${product.ftLbEquiv}"/>
							</c:when>
			
							<c:when test="${fn:contains(unit.code,'pc_lb')}">
								<c:set var="weightPerFt"  value="${product.ftLbEquiv}"/>
							</c:when>
			
							<c:when test="${fn:contains(unit.code,'lb')}">
								<c:set var="weightPerFt"  value="${product.ftLbEquiv}"/>
							</c:when>
			
							<c:when test="${fn:contains(unit.code,'ft')}">
								<c:set var="weightPerFt"  value="${product.ftLbEquiv}"/>
							</c:when>
						
							<c:when test="${fn:contains(unit.code,'ton')}">
								<c:set var="weightPerFt"  value="${product.ftTnEquiv}"/>
							</c:when>
						</c:choose>
						
						<!-- weight/FT column -->
						<td class="col_right text_top" style="vertical-align: text-top; padding-top: 5px;">
							<fmt:formatNumber pattern="${weightNumberPattern}"  value="${weightPerFt}"/>
						</td>
						
						<!-- available stock column -->
						<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
							<c:choose>
								<c:when test="${availableStock eq 0 }">
									<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px; color:red;">
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
						
						<!-- inventory location column -->
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
			
						<!-- quantity column -->
						<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
							<c:set var="inputDisabled" value=""/>
							<c:set var="inputPlaceholder" value="0"/>
							<c:set var="inputClass" value="edit intqty"/>
							
							<c:if test="${not empty product.quantityExcel }">
								<fmt:formatNumber var="inputValue" pattern="###,###,###,###,###"  value="${product.quantityExcel}"/>							
							</c:if>
							
							<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
								<c:if test="${availableStock eq 0}">
									<c:set var="inputDisabled" value="disabled"/>
									<c:set var="inputPlaceholder" value="----"/>
									<c:set var="inputClass" value="noedit"/>
									<c:set var="inputValue" value="" />
								</c:if>
								<c:set var="inventoryQtyFtAPIMax" value="${availableStock}"/>
							</sec:authorize>
							<input id="stockQty" class="${inputClass} stockQty validateMinMax"  valMin="${inventoryQtyFtAPIMin}" valMax="${inventoryQtyFtAPIMax}" type="text" value="${inputValue}"size="10" ${inputDisabled} placeholder="${inputPlaceholder}"/>
						</td>
						
						<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
							<!-- rolling schedule week column -->
							<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
								<product:productInventoryRollingScheduleSelector dateFormat="MM-dd-yyyy" dates="${inventoryEntry.rollingScheduleDates}"/>
							</td>
							
							<!-- order rolling units -->
							<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
								<c:set var="inputDisabled" value=""/>
								<c:set var="inputPlaceholder" value="0"/>
								<c:set var="inputClass" value="edit intqty"/>
								<c:set var="rollingExcelQty" value="" />
								<c:if test="${empty inventoryEntry.rollingScheduleDates}">
									<c:set var="inputDisabled" value="disabled"/>
									<c:set var="inputPlaceholder" value="----"/>
									<c:set var="inputClass" value="noedit"/>
									<c:set var="rollingExcelQty" value="" />
								</c:if>
								<c:if test="${availableStock eq 0 and not empty inventoryEntry.rollingScheduleDates and not empty product.quantityExcel}">
									<fmt:formatNumber var="rollingExcelQty" pattern="###,###,###,###,###"  value="${product.quantityExcel}"/>
								</c:if>
								<input id="rollingScheduleQty" class="${inputClass} validateMinMax" valMin="${rollingQtyFtAPIMin}" valMax="${rollingQtyFtAPIMax}" type="text" size="10" value="${rollingExcelQty}" ${inputDisabled} placeholder="${inputPlaceholder}"/>
							</td>
						</sec:authorize>
					
						<!-- favorite star column -->
			           	<td class="col_center">
			           		<div class="favorite_container">
				           		<div id="loading_${product.code}" class="favorite_loading" style="display: none;"></div>
				           		<div id="star_${product.code}" class="favorite_star" data-score="${isFavorite}" data-productCode="${product.code}"></div>
			           		</div>
			           	</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br/>
	</c:if>
	
	<!-- Section whether Products No API are included -->
	<c:if test="${isPageWithNoAPIProducts eq true}">
		<c:if test="${baseStore.APIFunctionaliatyEnabled eq true}">
			<spring:theme code="product.list.add.productsnoapi.message" />
		</c:if>
		<table id="productListTable_NoAPI" class="productListTable">
			<tr class="firstrow">
				<c:if test="${selLocation != null}">
			       <td width="5"></td>
			    </c:if>
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
		
			<c:forEach items="${productList}" var="product" varStatus="status">
				<c:if test="${product.isAPI eq false}">
					<c:set var="inventoryEntry" value="${product.inventoryEntry}" />
					<c:set var="inventory" value="${inventoryEntry.availableStockBundles}" />
					
					<c:set value="0" var="isFavorite" />
					<c:if test="${product.isFavoriteProduct eq true}">
			    		<c:set value="1" var="isFavorite"></c:set>
					</c:if>
			
					<sec:authorize ifAnyGranted="ROLE_PROLAMSA_INTERNAL">
						<c:set var="inventory" value="${inventoryEntry.availableStockBundlesInternal}" />
					</sec:authorize>
								
					<tr class="prolamsa_product_row" productCode="${product.code}" productLocation="${inventoryEntry.location}" isAPIProduct="${false}">
						
						<!-- product location column -->
						<c:if test="${selLocation != null}">
							<td width="5" style="vertical-align: text-top; padding-top: 5px; height: 20px;" >	
							   <input type="radio" name="selProduct" value="" onClick="getURLCodeLocation('/store/p/${product.code}?selLocation=${inventoryEntry.location}')" 
							           <c:if test="${inventoryEntry.location eq selLocation}">checked</c:if>
							            />
							</td>
						</c:if>
						
						<!-- product code and description columns -->
						<c:if test="${productCode == null }">
							<td class="text_top" style="vertical-align: text-top; padding-top: 5px;"><a href="${contextPath}${product.url}">${product.visibleCode}</a></td>
							<td class="product_list_product_code text_top" style="vertical-align: text-top; padding-top: 5px; max-width: 200px;">
								 <span class="productDescription" style="vertical-align: text-top; " productCode="${product.code}" onclick="quickView(this, event)" onmouseout="closePop();"><spring:theme code="${product.name}"/></span> 
							</td>
						</c:if>
						
						<!-- pieces/bundle column -->
						<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
							<fmt:formatNumber pattern="###,###,###,###,##0"  value="${product.piecesPerBundle}"/>
						</td>

						<!-- calculate weight/piece, weight/bundle, available stock -->
						<c:set var="weightPerPiece" value="" />
						<c:set var="weightPerBundle" value="" />
						<c:set var="availableStock" value="" />
			
						<c:choose>
							<c:when test="${fn:contains(unit.code,'bun_kg') }">
								<c:set var="weightPerPiece"  value="${ product.pcKgEquiv}"/>
								<c:set var="weightPerBundle"  value="${ product.bundleKgEquiv }"/>
								<c:set var="availableStock"  value="${inventory}" />
							</c:when>
							
							<c:when test="${fn:contains(unit.code,'pc_kg')  }">
								<c:set var="weightPerPiece"  value="${product.pcKgEquiv }"/>
								<c:set var="weightPerBundle"  value="${product.bundleKgEquiv }"/>
								<c:set var="availableStock"  value="${inventory*product.piecesPerBundle}"/>
							</c:when>
						
							<c:when test="${fn:contains(unit.code,'kg')}">
								<c:set var="weightPerPiece"  value="${product.pcKgEquiv }"/>
								<c:set var="weightPerBundle"  value="${product.bundleKgEquiv }"/>
								<c:set var="availableStock"  value="${inventory*product.bundleKgEquiv}"/>
							</c:when>
						
							<c:when test="${fn:contains(unit.code,'mt')}">
								<c:set var="weightPerPiece"  value="${product.pcKgEquiv }"/>
								<c:set var="weightPerBundle"  value="${product.bundleKgEquiv }"/>
								<c:set var="availableStock"  value="${inventory*product.mtEquiv}"/>
							</c:when>
						
							<c:when test="${fn:contains(unit.code,'bun_lb') }">
								<c:set var="weightPerPiece"  value="${product.pcLbEquiv }"/>
								<c:set var="weightPerBundle"  value="${product.bundleLbEquiv }"/>
								<c:set var="availableStock"  value="${inventory}"/>
							</c:when>
			
							<c:when test="${fn:contains(unit.code,'pc_lb') }">
								<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
								<c:set var="weightPerBundle"  value="${product.bundleLbEquiv }"/>
								<c:set var="availableStock"  value="${inventory*product.piecesPerBundle}"/>
							</c:when>
			
							<c:when test="${fn:contains(unit.code,'lb')}">
								<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
								<c:set var="weightPerBundle"  value="${product.bundleLbEquiv }"/>
								<c:set var="availableStock"  value="${inventory*product.bundleLbEquiv}"/>
							</c:when>
			
							<c:when test="${fn:contains(unit.code,'ft')}">
								<c:set var="weightPerPiece"  value="${product.bundleLbEquiv / product.piecesPerBundle}"/>
								<c:set var="weightPerBundle"  value="${product.bundleLbEquiv }"/>
								<c:set var="availableStock"  value="${inventory*product.ftEquiv}"/>
							</c:when>
						
							<c:when test="${fn:contains(unit.code,'ton')}">
								<c:set var="weightPerPiece"  value="${ product.tnEquiv / product.piecesPerBundle}"/>
								<c:set var="weightPerBundle"  value="${ product.tnEquiv}"/>
								<c:set var="availableStock" value="${inventory*product.tnEquiv}"/>
							</c:when>
						</c:choose>
					
						<!-- weight/piece column -->
						<td class="col_right text_top" style="vertical-align: text-top; padding-top: 5px;">
							<fmt:formatNumber pattern="${weightNumberPattern}"  value="${weightPerPiece}"/>
						</td>
						<!-- weight/bundle column -->
						<td class="col_right text_top" style="vertical-align: text-top; padding-top: 5px;">
							<fmt:formatNumber pattern="${weightNumberPattern}"  value="${weightPerBundle}"/>
						</td>
						
						<!-- available stock column -->
						<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
							<c:choose>
								<c:when test="${availableStock eq 0 }">
									<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px; color:red;">
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
						
						<!-- inventory location column -->
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
						
						<!-- quantity column -->
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
									<c:when test="${not empty product.quantityExcel }">
										<c:set value="${product.quantityExcel }" var="selectedQuantity"/>
										<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
											<product:productInventoryBundleSelectorExcel bundleName="stockQty" product="${product}" quantityCol="${noInventoryRoleBundlesCol}" selectedQuantity="${product.quantityExcel}" />
										</td>
									</c:when>
									<c:otherwise>
										<td class="col_center text_top"  style="vertical-align: text-top; padding-top: 5px;" >
											<product:productInventoryBundleSelector   bundleName="stockQty" quantityCol="${noInventoryRoleBundlesCol}" selectedQuantity="${ selectedQuantity}" />
										</td>
									</c:otherwise>
								</c:choose>
								
								<c:if test="${not empty product.quantityExcel }">
									<c:set value="${product.quantityExcel }" var="selectedQuantity"/>
								</c:if>
								
							</c:when>
							<c:when test="${availableStock eq 0 }">
								<td class="col_center text_top"  style="vertical-align: text-top; padding-top: 5px;" >
									<product:productInventoryBundleSelector   bundleName="stockQty" quantityCol="${x}" selectedQuantity="0" />
								</td>
							</c:when>
							<c:when test="${empty product.quantityExcel}">
								<td class="col_center text_top"  style="vertical-align: text-top; padding-top: 5px;" >
									<product:productInventoryBundleSelector   bundleName="stockQty" quantityCol="${availableStockBundlesCol}" selectedQuantity="0" />
								</td>
							</c:when>
							<c:otherwise>
								<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
									<product:productInventoryBundleSelectorExcel bundleName="stockQty" product="${product}" quantityCol="${availableStockBundlesCol}" selectedQuantity="${product.quantityExcel}" />
								</td>
							</c:otherwise>
						</c:choose>
						
						<sec:authorize ifNotGranted="ROLE_PROLAMSA_NO_INVENTORY">
							<!-- rolling schedule week column -->
							<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px;">
								<product:productInventoryRollingScheduleSelector dateFormat="MM-dd-yyyy" dates="${inventoryEntry.rollingScheduleDates}"/>
							</td>
				
							<!-- order rolling units -->
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
										<c:when test="${ availableStock eq 0 and not empty product.quantityExcel  }">
											<td class="col_center text_top" style="vertical-align: text-top; padding-top: 5px; padding-bottom: 5px;">
												<product:productInventoryBundleSelectorExcel  product="${ product}" bundleName="rollingScheduleQty" quantityCol="${inventoryEntry.rollingScheduleBundlesCol}" selectedQuantity="${product.quantityExcel  }" />
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
						
						<!-- favorite star column -->
			           	<td class="col_center">
			           		<div class="favorite_container">
				           		<div id="loading_${product.code}" class="favorite_loading" style="display: none;"></div>
				           		<div id="star_${product.code}" class="favorite_star" data-score="${isFavorite}" data-productCode="${product.code}"></div>
			           		</div>
			           	</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</c:if>
	
<script type="text/javascript"> // set vars
	onDOMLoaded(bindIntQty);
	
	function bindIntQty()
	{
		$(".intqty").numericInput();
		
		$(".intqty").focusin(function(){
			var val = $(this).val();
			if(val != '' && val != 0){
				var valUnFormatted = numeral().unformat(val);
				$(this).val(valUnFormatted);
			}
		});
		
		$(".intqty").focusout(function(){
			var val = $(this).val();
			if(val != '' && val != 0){
				var valFormatted = numeral(val).format('0,0');
				$(this).val(valFormatted);
			}
		});
	}
</script>