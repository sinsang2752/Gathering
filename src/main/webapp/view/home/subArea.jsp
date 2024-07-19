<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<button id="subClose" class="btnClose"></button>
<div id="cateTitle">
	<span>${cateName}</span> 모임 리스트
</div>
<div id="gathContainer">
	<c:forEach items="${gathList}" var="gath">
		<div class="gathList">
			<input class="gathListGno" type="hidden" value="${gath.getGNo()}">
			<img src="./images/gath/${gath.getGImg()}">
			<div>
				<div class="subGathTitle">${gath.getGTitle()}</div>
				<div class="subGathContent">${gath.getGIntro()}</div>
				<div class="subGathInfo">
					<span class="subCateSpan">${cateName}</span> <span>맴버 <span class="subGathMemCnt">${gath.getMemCnt()}</span></span>
				</div>
			</div>
		</div>
	</c:forEach>
	<div id="createGathArea">
		<button id="createGathBtn">${cateName} 소모임 개설</button>
	</div>
</div>