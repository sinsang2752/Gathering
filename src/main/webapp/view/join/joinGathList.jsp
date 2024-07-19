<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${joinList}" var="gath">
<div class="joinGath">
<input class="joinGno" type="hidden" value="${gath.getGNo()}">
	<img alt="테스트" src="./images/gath/${gath.getGImg()}">
	<div>
		<div class="joinGathTitle">${gath.getGTitle()}</div>
		<div class="joinGathContent">
			${gath.getGIntro()}
		</div>
		<div class="joinGathSun"><span class="joinCateSpan">${gath.getCName()}</span> <span>맴버 <span class="memCntSpan">${gath.getMemCnt()}</span></span> <button class="button-3 withdrawGathBtn">탈 퇴</button></div>
	</div>
</div>
</c:forEach>