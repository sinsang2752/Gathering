<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="toDoMeetTitle">${meetDay} 참석한 모임</div>
<div id="toDomeetListArea">
	<c:if test="${meetList != null}">
		<c:forEach items="${meetList}" var="meet">
			<div class="toDoDetailMeet">
				<p class="meetTitle">${meet.getMetTitle()}</p>
				<div>
					<img alt="모임썸네일" src="./images/meet/${meet.getMetImg()}">
					<div class="toDoMeetInfoArea">
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
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>
</div>