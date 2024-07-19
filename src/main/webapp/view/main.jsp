<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>소모임</title>
<link rel="stylesheet" href="./css/main.css">
<link rel="stylesheet" href="./css/common.css">
<link rel="stylesheet" href="./css/login.css">
<link rel="stylesheet" href="./css/sub.css">
<link rel="stylesheet" href="./css/calendar.css">
<link rel="stylesheet" href="./css/joinGath.css">
<link rel="stylesheet" href="./css/manage.css">
<script src="./js/common/jquery-3.7.1.min.js"></script>
<script src="./js/canlendar.js" charset="UTF-8"></script>
<script src="./js/joinMeet.js" charset="UTF-8"></script>
<script src="./js/manage.js" charset="UTF-8"></script>
<script>
$(function() {
	if("${userInfo}" != '') {
		$("#logoutBtn").on("click",logout);
	}
	$("#menuArea ul li").on("click", selMenu);
	$("#cateList td").on("click", selectByCno);
	$("#subClose").on("click", closeSub);
	$("#log_btn").on("click",login);
	$(".hotContainer ul li").on("click", selHotGath);
	
	$(".hotContainer ul li").hover(function() {
		$(this).parent().parent().children("ul").css("animation-play-state", "paused");
	}, function() {
		$(this).parent().parent().children("ul").css("animation-play-state", "");
	})
	

});

function selGathList() {
	var gNo = $(this).children(".gathListGno").val();
	getSubGathInfo(gNo);
}

function selHotGath() {
	var gNo = $(this).children().children(".gNo").val();
	getSubGathInfo(gNo);
}

function getSubGathInfo(gNo) {
	$.ajax({
		url: "subGath.do",
		type: "GET",
		data: {"gNo":gNo},
		success: function(result) {
			$("#subArea").html(result);
			$("#subClose").on("click", closeSub);
			
			openSub();
			
			$("#joinGathBtn").on("click",joinGath);
			$("#createMeetBtn").on("click",openMeetCreate);
			$(".attendBtn").on("click",attendMeet);
			$(".withdrawBtn").on("click",withdrawMeet);
		}, error: function(e) {
			console.log(e);
		}
	});
}

function attendMeet() {
	var metNo = $(this).parent().children("div").children(".subGatMetNo").val();
	var memMax = $(this).parent().children("div").children(".subGatMetMaxMem").val();
	
	$.ajax({
		url: "joinMeet.do",
		type: "POST",
		data: {"metNo":metNo, "memMax" : memMax},
		success: function(result, status, xhr) {
			if(xhr.getResponseHeader("result") == 1) {
				alert("모임에 참가했습니다.");
				getMeetList();
				getTodayMeet();
			} else {
				alert("모임 참가에 실패했습니다.");
			}
		}, error: function(e) {
			console.log(e);
		}
	});
}

function withdrawMeet() {
	var metNo = $(this).parent().children("div").children(".subGatMetNo").val();
	
	$.ajax({
		url: "withdrawMeet.do",
		type: "POST",
		data: {"metNo":metNo},
		success: function(result, status, xhr) {
			if(xhr.getResponseHeader("result") == 1) {
				alert("모임 참가를 철회했습니다.");
				getMeetList();
				getTodayMeet();
			} else {
				alert("철회에 실패했습니다.");
			}
		}, error: function(e) {
			console.log(e);
		}
	});
}

function getMeetList() {
	var gNo = $("#subGathGno").val();
	$.ajax({
		url: "getMeetList.do",
		type: "GET",
		data: {"gNo":gNo},
		success: function(result) {
			$("#detailMeetArea").html(result);
			$(".attendBtn").on("click",attendMeet);
			$(".withdrawBtn").on("click",withdrawMeet);
		}, error: function(e) {
			console.log(e);
		}
	});
}

function joinGath() {
	var gNo = $("#subGathGno").val();
	if(sessionChk() == 0) return;
	
	$.ajax({
		url: "joinGath.do",
		type: "POST",
		data: {"gNo":gNo},
		success: function(result) {
			if(result == 1) {
				alert("소모임에 가입되었습니다.");
				getSubGathInfo(gNo);
			} else {
				alert("소모임 가입에 실패했습니다.");
			}
		}, error: function(e) {
			console.log(e);
		}
	});
}

function getTodayMeet() {
	$.ajax({
		url: "todayMeet.do",
		type: "GET",
		success: function(result) {
			$("#todayMeetArea").html(result);
		}, error: function(e) {
			console.log(e);
		}
	});
}

function selMenu() {
	closeSub();
	var beforeIndex = $(this).parent().children(".menuSel").children(".menuIndex").val();
	var menuIndex = $(this).children(".menuIndex").val();
	if (menuIndex == beforeIndex) return;
	if (menuIndex == 2 || menuIndex == 3 || menuIndex == 4) if(sessionChk() == 0) return;
	$(this).parent().children().removeClass("menuSel");
	$(this).addClass("menuSel");
	
	if(menuIndex == 1) homeMenu();
	if(menuIndex == 2) joinedGathMenu();
	if(menuIndex == 3) meetScheduleMenu();
	if(menuIndex == 4) manageMenu();
	
}

function homeMenu() {
	$.ajax({
		url: "home.do",
		type: "GET",
		success: function(result) {
			$("#contentArea").html(result);
			
			$("#cateList td").on("click", selectByCno);
			$(".hotContainer ul li").on("click", selHotGath);
			
			$(".hotContainer ul li").hover(function() {
				$(this).parent().parent().children("ul").css("animation-play-state", "paused");
			}, function() {
				$(this).parent().parent().children("ul").css("animation-play-state", "");
			})
		}, error: function(e) {
			console.log(e);
		}
	});
}

function joinedGathMenu() {
	$.ajax({
		url: "joinGathMain.do",
		type: "GET",
		success: function(result) {
			$("#contentArea").html(result);
			initJoinMeet();
		}, error: function(e) {
			console.log(e);
		}
	});
}

function meetScheduleMenu() {
	$.ajax({
		url: "meetSchedule.do",
		type: "GET",
		success: function(result) {
			$("#contentArea").html(result);
			calendarInit();
		}, error: function(e) {
			console.log(e);
		}
	});

}

function manageMenu() {
	$.ajax({
		url: "manage.do",
		type: "GET",
		success: function(result) {
			$("#contentArea").html(result);
			initManager();
		}, error: function(e) {
			console.log(e);
		}
	});
}

function openSub() {
	$("#contentArea").css("width", "39%");
	$("#subArea").css("width", "37.5%");
/* 	$("#subArea").css("padding", "15px"); */
	$("#subArea").css("border", "2px solid #f9a9a9");
}

function closeSub() {
	$("#contentArea").css("width", "79.5%");
	$("#subArea").css("width", "0");
	$("#subArea").css("padding", "0");
	$("#subArea").css("border", "none");
}

function sessionChk() {
	var resultNum = 0;
	$.ajax({
		url: "sessionCheck.do",
		async: false,
		success: function(result) {
			if(result == "null"){
				alert("로그인 후 사용 가능한 기능입니다.");
			} else {
				resultNum = 1;
			}
		}, error: function(e) {
			console.log(e);
		}
	});
	
	return resultNum;
}

function selectByCno() {
	var cNo = $(this).children(".cNo").val();
	var cName = $(this).children("p").text();
	$.ajax({
		url: "selectCno.do",
		type: "GET",
		data: {"cateName": cName, "cNo" : cNo},
		success: function(result) {
			$("#subArea").html(result);
			$("#subClose").on("click", closeSub);
			
			openSub();
			
			$("#createGathArea").on("click", openCreate);
			$("#createClose").on("click", closeCreate);
			
			$("#createCno").val(cNo);
			$("#createCname").val(cName);
			$(".gathList").on("click", selGathList);
		}, error: function(e) {
			console.log(e);
		}
	});
}


function afterCreate() {
	var cNo = $("#createCno").val();
	var cName = $("#createCname").val();
	$.ajax({
		url: "selectCno.do",
		type: "GET",
		data: {"cateName": cName, "cNo" : cNo},
		success: function(result) {
			$("#subArea").html(result);
			$("#subClose").on("click", closeSub);
			$("#createGathArea").on("click", openCreate);
			$("#createClose").on("click", closeCreate);
			closeCreate();
			initCreate();
			$(".gathList").on("click", selGathList);
		}, error: function(e) {
			console.log(e);
		}
	});
}

function logout() {
	$.ajax({
		url: "logout.do",
		type: "GET",
		success: function(result) {
			alert("로그아웃 되었습니다.");
			$("#userInfo").html(result);
			$("#loginBtn").on("click", openLogin);
			getTodayMeet();
			homeMenu();
			$("#menuArea ul").children().removeClass("menuSel");
			$("#menuArea ul").children().eq(0).addClass("menuSel");
		}, error: function(e) {
			console.log(e);
		}
	});
}

function creataGath() {
	var img = $("#createImgFile").val();
	var title = $("#createTitle").val();
	var cNo = $("#createCno").val();
	var intro = $("#createIntro").val();
	
	if(img == "") {
		alert("썸네일을 첨부해주세요");
		return;
	}
	if (title == "") {
		alert("소모임 이름을 입력해주세요");
		$("#title").focus();
		return;
	}
	if (intro == "") {
		alert("소개를 입력해주세요");
		$("#intro").focus();
		return;
	}
	
	var createForm = $("#createForm")[0];  	           
    var createDatas = new FormData(createForm);
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "createGath.do",
		data: createDatas,
		processData: false,    
	    contentType: false,      
	    cache: false,
		success: function(result, status, xhr) {
			if(xhr.getResponseHeader("result") == 1) {
				alert("소모임이 개설되었습니다.");
				afterCreate();
			} else {
				alert("소모임 개설에 실패했습니다.");
			}
		}, error: function(e) {
			console.log(e);
		}
	});
}

function login() {
	var id = $("#logId").val();
	var pw = $("#logPw").val();
	if(id == "") {
		alert("ID를 입력해주세요");
		$("#logId").focus();
		return;
	}
	if (pw == "") {
		alert("패스워드를 입력해주세요");
		$("#logPw").focus();
		return;
	}
	$.ajax({
		url: "login.do",
		type: "POST",
		data: {"userId": $("#logId").val(),
				"userPw": $("#logPw").val()},
		success: function(result, status, xhr) {
			if(xhr.getResponseHeader("result") == 1) {
				$("#userInfo").html(result);
				closeLogin();
				$("#logoutBtn").on("click",logout);
				getTodayMeet();
			} else {
				alert("아이디 또는 패스워드가 틀렸습니다.");
			}
		}, error: function(e) {
			console.log(e);
		}
	});
}
</script>
</head>
<body>
<jsp:include page="./modal/loginModal.jsp"/>
<jsp:include page="./modal/gathCreateModal.jsp"/>
<jsp:include page="./modal/meetCreateModal.jsp"/>
<jsp:include page="./modal/updateGathModal.jsp"/>
	<div id="container">
		<div id="header">
		<div id="userInfo">
			<jsp:include page="./login/loginCheck.jsp"/>
		</div>
		</div>
		<aside id="sideMenu">
			<div id="menuIcon"><img src="./images/logo.png"><span>소모임</span></div>
			<div id="menuArea">
				<ul>
					<!-- 메인메뉴 -->
					<li class="menuSel">
						<div></div>
						<input type="hidden" value="1" class="menuIndex">
						<img src="./images/home.png">
						<span>홈화면</span>
					</li>
					<li>
						<div></div>
						<input type="hidden" value="2" class="menuIndex">
						<img src="./images/cheked.png">
						<span>가입한 소모임</span>
					</li>
					<li>
						<div></div>
						<input type="hidden" value="3" class="menuIndex">
						<img src="./images/time.png">
						<span>모임 일정</span>
					</li>
					<!-- 관리 -->
					<li>
						<div></div>
						<input type="hidden" value="4" class="menuIndex">
						<img src="./images/gears.png">
						<span>소모임 관리</span>
					</li>
				</ul>
			</div>
		</aside>
		<div id="todayMeetArea">
			<jsp:include page="./home/todayMeetList.jsp"/>
		</div>
		<div id="contentArea">
			<jsp:include page="./home/home.jsp"/>
		</div>
		<div id="subArea">
			
		</div>
	</div>
</body>
</html>