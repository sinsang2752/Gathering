<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="joinCateArea">
	<ul>
		<li class="joinCateSel">
			<input type="hidden" value="0" class="joinCateCno">
			ALL
		</li>
		<c:forEach items="${cateList}" var="cate">
		<li>
			<input type="hidden" value="${cate.getCNo()}" class="joinCateCno">
			<img src="./images/category/${cate.getCImg()}">
		</li>
		</c:forEach>
	</ul>
</div>
<input type="hidden" value="0" id="joinSelCno">
<div id="joinGathArea">
	<jsp:include page="./joinGathList.jsp"></jsp:include>
</div>