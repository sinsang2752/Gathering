<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<button id='subClose' class='btnClose'></button>
			<div id="subGathArea">
			<input id="subGathGno" type="hidden" value="${gathInfo.getGNo()}">
				<div class="gathImagewrapper">
					<img src="./images/gath/${gathInfo.getGImg()}" alt="모임 썸네일">
				</div>
				<div id="subGathTitleArea">
					<p>${gathInfo.getGTitle()}</p>
				</div>
				<div id="subGathIntroArea">
					<span>${gathInfo.getGIntro()}</span>
				</div>
				<hr>
				<div id="subGathMeetArea">
					<p>모집중인 모임 <c:if test="${joinCheck == 1}"><button id="createMeetBtn">모임 개설</button></c:if></p>
					<div id="detailMeetArea">
					<jsp:include page="meetList.jsp"/>
					</div>
				</div>
				<hr>
				<div id="memListArea">
					<p>모임 맴버 (${fn:length(joinList)})</p>
					<c:forEach items="${joinList}" var="mem">
					<div id="memList">
						<p><img src="./images/profile/${mem.getImg()}"> <span>${mem.getName()}</span></p>
					</div>
					</c:forEach>
				</div>
</div>
<c:if test="${joinCheck == 0}"><button id = "joinGathBtn" class="joinGathBtn">모임 가입하기</button></c:if>