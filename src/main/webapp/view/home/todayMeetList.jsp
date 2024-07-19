<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul>
	<c:if test="${todayMetList != null}">
		<c:forEach items="${todayMetList}" var="todayMet">
			<li><img alt="소모임 썸네일" src="./images/gath/${todayMet.getGImg()}">
				<div>
					<p>${todayMet.getMetTitle()}</p>
					<img alt="모임 썸네일" src="./images/meet/${todayMet.getMetImg()}">
					<div>
						<p>
							<span class="gray">일시</span> <span>${todayMet.getExpDate()}</span>
						</p>
						<p>
							<span class="gray">위치</span> <span>${todayMet.getMetPlace()}</span>
						</p>
						<p>
							<span class="gray">비용</span> <span>${todayMet.getMetPrice()}</span>
						</p>
						<p>
							<span class="gray">참석</span> <span>${todayMet.getJoinCount()}</span>
						</p>
					</div>
				</div></li>
		</c:forEach>
	</c:if>
</ul>