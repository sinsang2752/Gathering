<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
$(function(){
	$("#updateClose").on("click", closeUpdate);
	$("#updateBtn").on("click", updateGath);
	$("#updateImgFile").on("change", function(e) {
		var file = e.target.files[0];
		if(isImageFile(file)) {
		var reader = new FileReader(); 
		reader.onload = function(e) {
		 	$("#updatePreview").attr("src", e.target.result);
		}
		reader.readAsDataURL(file);
		} else {
		    alert("이미지 파일만 첨부 가능합니다.");
		    $("#updateImgFile").val("");
			$("#updatePreview").attr("src", "");
		}
	});
	
});

function openUpdate() {
	if(sessionChk()  == 0) return;
	$("#updateModal").addClass("show");
}

function closeUpdate() {
	$("#updateModal").removeClass("show");
	initUpdate();
}

function initUpdate() {
	$("#updateImgFile").val("");
	$("#updateIntro").val("");
	$("#updatePreview").attr("src", "");
}

function updateGath() {
	var updateImg = $("#updateImgFile").val();
	if(updateImg == "") noModImg();
	else modImg();
}

function noModImg() {
	var gNo = $("#updateGno").val();
	var gIntro = $("#updateIntro").val();
	$.ajax({
		type: "POST",
		url: "updateNoImg.do",
		data: {"gNo":gNo, "gIntro":gIntro},
		success: function(result) {
			if(result == 1) {
				alert("소모임이 정보가 수정되었습니다.");
				manageMenu();
				closeUpdate();
			} else {
				alert("정보 수정에 실패했습니다.");
			}
		}, error: function(e) {
			console.log(e);
		}
	});
}

function modImg() {
	var updateForm = $("#updateForm")[0];  	           
    var updateDatas = new FormData(updateForm);
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "updateGath.do",
		data: updateDatas,
		processData: false,    
	    contentType: false,      
	    cache: false,
		success: function(result, status, xhr) {
			if(xhr.getResponseHeader("result") == 1) {
				alert("소모임이 정보가 수정되었습니다.");
				manageMenu();
				closeUpdate();
			} else {
				alert("정보 수정에 실패했습니다.");
			}
		}, error: function(e) {
			console.log(e);
		}
	});
}

</script>
<div class="c_modal" id="updateModal">
	<div class="u_modal_body">
		<div class="m_body">
		<div class="close_btn" id="updateClose">X</div>
			<div id="updateModalArea" class="show">
			<form method="POST" enctype="multipart/form-data" id="updateForm">
				<div id="createImgArea">
					<div class="modal_label">소모임 썸네일</div>
					<img id="updatePreview" style="max-width: 100px;">
					<div class="imgLabel">
						<label for="updateImgFile">
							<span class="btn-upload">썸네일 업로드</span>
						</label>
					</div>
  					<input type="file" name="img" id="updateImgFile" value="" />
				</div>
				<input type="hidden" value="" name="gNo" id="updateGno">
				<div class="modal_label">수정할 소모임 소개</div>
				<input type="text" class="input_box" id="updateIntro" name="intro" />
			</form>
				<div class="modal_btn save" id="updateBtn">소모임 수정</div>
			</div>
		</div>
	</div>
</div>