<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${meetList != null }">
	<c:forEach items="${meetList}" var="meet">
		<div class="detailMeet">
			<p class="meetTitle">${meet.getMetTitle()}</p>
			<c:choose>
				<c:when test="${joinCheck == 1 && meet.getAttCheck() == 0}">
					<button class="btn-two attBtn attendBtn">참석</button>
				</c:when>
				<c:when test="${joinCheck == 1 && meet.getAttCheck() == 1}">
					<button class="btn-two notAttBtn withdrawBtn">불참</button>
				</c:when>
			</c:choose>
			<div>
				<input type="hidden" value="${meet.getMetNo()}" class="subGatMetNo">
				<input type="hidden" value="${meet.getMetMaxMem()}" class="subGatMetMaxMem">
				<img alt="모임썸네일" src="./images/meet/${meet.getMetImg()}">
				<div class="meetInfoArea">
					<p>
						<span class="gray">일시</span> <span>${meet.getMetExpDate()}</span>
					</p>
					<p>
						<span class="gray">위치</span> <span>${meet.getMetPlace()}</span>
					</p>
					<p>
						<span class="gray">비용</span> <span>${meet.getMetPrice()}</span>
					</p>
					<p>
						<span class="gray">참석</span> <span>${meet.getJCount()}</span>
					</p>
					<p>
						<span class="gray">설명</span> <span>${meet.getMetContent()}</span>
					</p>
				</div>
			</div>
		</div>
	</c:forEach>
</c:if>