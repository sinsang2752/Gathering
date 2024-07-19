<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="manageArea">
	<c:forEach items="${gathList}" var="gath">
		<div class="manageGath">
			<input type="hidden" value="${gath.getGNo()}" class="manageGno">
			<img src="./images/gath/${gath.getGImg()}">
			<div class="manageGathInfo">
				<div class="manageGathTitle">${gath.getGTitle()}</div>
				<div class="manageGathIntro">${gath.getGIntro()}</div>
				<div class="manageBtnArea">
					<button class="gathModBtn">수정</button>
					<button class="gathDelBtn">삭제</button>
				</div>
			</div>
		</div>
	</c:forEach>
</div>