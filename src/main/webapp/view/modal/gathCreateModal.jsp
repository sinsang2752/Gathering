<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
$(function(){
	$("#createGathArea").on("click", openCreate);
	$("#createClose").on("click", closeCreate);
	$("#createBtn").on("click", creataGath);
	$("#createImgFile").on("change", function(e) {
		var file = e.target.files[0];
		if(isImageFile(file)) {
		var reader = new FileReader(); 
		reader.onload = function(e) {
		 	$("#createPreview").attr("src", e.target.result);
		}
		reader.readAsDataURL(file);
		} else {
		    alert("이미지 파일만 첨부 가능합니다.");
		    $("#createImgFile").val("");
			$("#createPreview").attr("src", "");
		}
	});
	
});

function openCreate() {
	if(sessionChk()  == 0) return;
	$("#createModal").addClass("show");
}

function closeCreate() {
	$("#createModal").removeClass("show");
	initCreate();
}

function initCreate() {
	$("#createImgFile").val("");
	$("#createTitle").val("");
	$("#createIntro").val("");
	$("#createPreview").attr("src", "");
}

</script>
<div class="c_modal" id="createModal">
	<div class="c_modal_body">
		<div class="m_body">
		<div class="close_btn" id="createClose">X</div>
			<div id="createModalArea" class="show">
			<form method="POST" enctype="multipart/form-data" id="createForm">
				<div id="createImgArea">
					<div class="modal_label">소모임 썸네일</div>
					<img id="createPreview" style="max-width: 100px;">
					<div class="imgLabel">
						<label for="createImgFile">
							<span class="btn-upload">썸네일 업로드</span>
						</label>
					</div>
  					<input type="file" name="img" id="createImgFile" value="" />
				</div>
				<input type="hidden" value="" name="cNo" id="createCno">
				<input type="hidden" value="" name="cName" id="createCname">
				<div class="modal_label">소모임 이름</div>
				<input type="text" class="input_box" id="createTitle" name="title" />
				<div class="modal_label">소모임 소개</div>
				<input type="text" class="input_box" id="createIntro" name="intro" />
			</form>
				<div class="modal_btn save" id="createBtn">소모임 개설</div>
			</div>
		</div>
	</div>
</div>