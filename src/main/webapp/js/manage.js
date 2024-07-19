

function initManager() {
	$(".gathModBtn").off().on("click", modGath);
	$(".gathDelBtn").off().on("click", delGath);
}

function modGath() {
	openUpdate();
	var gNo = $(this).parent().parent().parent().children(".manageGno").val();
	var gImg = $(this).parent().parent().parent().children("img").attr("src");
	var gIntro = $(this).parent().parent().children(".manageGathIntro").text();
	$("#updateGno").val(gNo);
	$("#updateIntro").val(gIntro);
	$("#updatePreview").attr("src", gImg);
}

function delGath() {
	var gIntro = $(this).parent().parent().children(".manageGathTitle").text();
	var gNo = $(this).parent().parent().parent().children(".manageGno").val();
	var result = confirm(gIntro+"을 삭제하시겠습니까?");
    if(!result){return false;}
    $.ajax({
		type: "POST",
		url: "delGath.do",
		data: {"gNo":gNo},
		success: function(result) {
			if(result == 1) {
				alert(gIntro + "을 삭제했습니다.");
				manageMenu();
			} else {
				alert("삭제에 실패했습니다.");
			}
		}, error: function(e) {
			console.log(e);
		}
	});
}