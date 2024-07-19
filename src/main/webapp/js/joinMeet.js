
$(function() {
	initJoinMeet();
});

function initJoinMeet() {
	$(".joinGath").off().on("click",getGathInfo);
	$(".withdrawGathBtn").off().on("click", function(e) {
		var gNo = $(this).parent().parent().parent().children(".joinGno").val();
		var gTitle = $(this).parent().parent().children(".joinGathTitle").text();
		withdrawGath(gNo,gTitle);
		e.stopImmediatePropagation()
	});
	$("#joinCateArea ul li").off().on("click", joinSelCate);
}

function joinSelCate() {
	var selCno = $(this).children(".joinCateCno").val();
	$("#joinSelCno").val(selCno);
	$(this).parent().children().removeClass("joinCateSel");
	$(this).addClass("joinCateSel");
	getJoinGathList();
	
}

function getGathInfo() {
	var gNo = $(this).children(".joinGno").val();
	getSubGathInfo(gNo);
}

function withdrawGath(gNo,gTitle) {
  	var result = confirm(gTitle+"을 탈퇴하시겠습니까?");
    if(!result){return false;}
	$.ajax({
		url: "withdrawGath.do",
		type: "POST",
		data:{"gNo":gNo},
		success: function(result) {
			if(result == 0) {
				alert("탈퇴에 실패했습니다..");
				return false;
			}
			alert("탈퇴 되었습니다.");
			getJoinGathList();
		}, error: function(e) {
			console.log(e);
		}
	});
}

function getJoinGathList() {
	var joinCno = $("#joinSelCno").val();
	$.ajax({
		url: "joinGathList.do",
		type: "GET",
		data:{"joinCno":joinCno},
		success: function(result) {
			$("#joinGathArea").html(result);
			initJoinMeet();
		}, error: function(e) {
			console.log(e);
		}
	});
	
}