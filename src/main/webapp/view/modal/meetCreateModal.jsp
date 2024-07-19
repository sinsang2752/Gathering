<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
$(function(){
	$("#meetCreateClose").on("click", closeMeetCreate);
	$("#metCreateBtn").on("click",createMeet);
	$("#meetCreateImgFile").on("change", function(e) {
		var file = e.target.files[0];
		if(isImageFile(file)) {
		var reader = new FileReader(); 
		reader.onload = function(e) {
		 	$("#meetCreatePreview").attr("src", e.target.result);
		}
		reader.readAsDataURL(file);
		} else {
		    alert("이미지 파일만 첨부 가능합니다.");
		    $("#meetCreateImgFile").val("");
			$("#meetCreatePreview").attr("src", "");
		}
	});
	
});

function openMeetCreate() {
	if(sessionChk()  == 0) return;
	var gNo = $("#subGathGno").val();
	$("#metCreateGno").val(gNo);
	$("#meetCreateModal").addClass("show");
}

function closeMeetCreate() {
	$("#meetCreateModal").removeClass("show");
	initMeetCreate();
}

function initMeetCreate() {
	$("#createImgFile").val("");
	$("#metCreateTitle").val("");
	$("#metCreateContent").val("");
	$("#metCreatePrice").val("");
	$("#metCreatePlace").val("");
	$("#metCreateDate").val("");
	$("#metCreateMaxMem").val("");
	$("#meetCreatePreview").attr("src", "");
}

function createMeet() {
	var img = $("#meetCreateImgFile").val();
	var title = $("#metCreateTitle").val();
	var content = $("#metCreateContent").val();
	var price = $("#metCreatePrice").val();
	var place = $("#metCreatePlace").val();
	var createDate = $("#metCreateDate").val();
	var maxMem = $("#metCreateMaxMem").val();
	var gNo = $("#metCreateGno").val();
	
	if(img == "") {
		alert("썸네일을 첨부해주세요");
		return;
	}
	if (title == "") {
		alert("모임 이름을 입력해주세요");
		$("#metCreateTitle").focus();
		return;
	}
	if (content == "") {
		alert("소개를 입력해주세요");
		$("#metCreateContent").focus();
		return;
	}
	if (price == "") {
		alert("비용를 입력해주세요");
		$("#metCreatePrice").focus();
		return;
	}
	if (place == "") {
		alert("장소를 입력해주세요");
		$("#metCreatePlace").focus();
		return;
	}
	if (createDate == "") {
		alert("날짜를 입력해주세요");
		return;
	}
	if (maxMem == "") {
		alert("최대인원을 입력해주세요");
		$("#metCreateMaxMem").focus();
		return;
	}
	
	var createForm = $("#metCreateForm")[0];  	           
    var createDatas = new FormData(createForm);
    console.log(createDatas);
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "createMeet.do",
		data: createDatas,
		processData: false,    
	    contentType: false,      
	    cache: false,
		success: function(result, status, xhr) {
			if(xhr.getResponseHeader("result") == 1) {
				alert("모임이 개설되었습니다.");
				closeMeetCreate();
				getSubGathInfo(gNo);
			} else {
				alert("모임 개설에 실패했습니다.");
			}
		}, error: function(e) {
			console.log(e);
		}
	});
}

</script>
<div class="c_modal" id="meetCreateModal">
	<div class="met_modal_body">
		<div class="m_body">
		<div class="close_btn" id="meetCreateClose">X</div>
			<div id="createModalArea" class="show">
			<form method="POST" enctype="multipart/form-data" id="metCreateForm">
				<div id="createImgArea">
					<div class="modal_label">모임 썸네일</div>
					<img id="meetCreatePreview" style="max-width: 100px;">
					<div class="imgLabel">
						<label for="meetCreateImgFile">
							<span class="btn-upload">썸네일 업로드</span>
						</label>
					</div>
  					<input type="file" name="img" id="meetCreateImgFile" value="" />
				</div>
				<input type="hidden" value="" name="gNo" id="metCreateGno">
				<div class="modal_label">모임 이름</div>
				<input type="text" class="input_box" id="metCreateTitle" name="title" />
				<div class="modal_label">모임 설명</div>
				<input type="text" class="input_box" id="metCreateContent" name="content" />
				<div class="modal_label">모임 비용</div>
				<input type="number" class="input_box" id="metCreatePrice" name="price" />
				<div class="modal_label">모임 장소</div>
				<input type="text" class="input_box" id="metCreatePlace" name="place" />
				<div class="modal_label">모임 날짜</div>
				<input type="datetime-local" class="input_box" id="metCreateDate" name="expDate">
				<div class="modal_label">모임 최대 인원</div>
				<input type="number" class="input_box" id="metCreateMaxMem" name="maxMem" />
			</form>
				<div class="modal_btn save" id="metCreateBtn">모임 개설</div>
			</div>
		</div>
	</div>
</div>