<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${userInfo == null || userInfo == ''}">
		<button class="logBtn" id="loginBtn">로그인</button>
	</c:when>
	<c:otherwise>
		<img src="./images/profile/${userInfo.img}">
		<span>${userInfo.name}님 | </span>
		<button class="logBtn" id='logoutBtn'>로그아웃</button>
	</c:otherwise>
</c:choose>