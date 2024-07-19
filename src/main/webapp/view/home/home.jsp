<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="categoryArea">
				<table id="cateList">
					<tr>
						<c:forEach items="${firstCol}" var="cate">
							<td>
								<input class="cNo" type="hidden" value="${cate.getCNo()}"/>
								<img src="./images/category/${cate.getCImg()}">
								<p>${cate.getCName()}</p>
							</td>
						</c:forEach>
					<tr>
						<c:forEach items="${secondCol}" var="cate">
							<td>
								<input class="cNo" type="hidden" value="${cate.getCNo()}"/>
								<img src="./images/category/${cate.getCImg()}">
								<p>${cate.getCName()}</p>
							</td>
						</c:forEach>
					</tr>
				</table>
			</div>
			<div id="hotGathArea">
				<p>활동이 활발한 소모임 &#128293;</p>
				<div class="hotContainer">
					<ul>
					<c:forEach items="${hotGathList}" var="gath">
						<li>
							<div class="detailHot">
							<input class="gNo" type="hidden" value="${gath.getGNo()}">
							<img alt="${gath.getGTitle()}" src="./images/gath/${gath.getGImg()}">
							<div>
								<div class="hotTitle">
									${gath.getGTitle()}
								</div>
								<div class="hotContent">
									${gath.getGIntro()}
								</div>
								<div class="hotSub"><span class="cateSpan">${gath.getCName()}</span> <span>맴버 <span class="memCntSpan">${gath.getMemCnt()}</span></span></div>
							</div>
							</div>
						</li>
						</c:forEach>
					</ul>
					<ul class="list2">
					<c:forEach items="${hotGathList}" var="gath">
						<li>
							<div class="detailHot">
							<input class="gNo" type="hidden" value="${gath.getGNo()}">
							<img alt="${gath.getGTitle()}" src="./images/gath/${gath.getGImg()}">
							<div>
								<div class="hotTitle">
									${gath.getGTitle()}
								</div>
								<div class="hotContent">
									${gath.getGIntro()}
								</div>
								<div class="hotSub"><span class="cateSpan">${gath.getCName()}</span> <span>맴버 <span class="memCntSpan">${gath.getMemCnt()}</span></span></div>
							</div>
							</div>
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>