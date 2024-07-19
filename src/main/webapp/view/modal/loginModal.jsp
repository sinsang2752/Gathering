<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script>
$(function() {
	$("#loginBtn").on("click", openLogin);
	$("#loginClose").on("click", closeLogin);
	$("#signup_btn").on("click", signup);
	$("#logPw").on("keyup", function(key){
		if(key.keyCode==13) {
				login();
			}
		});
	
	//이미지 미리보기
	$("#imgFile").on("change", function(e) {
		var file = e.target.files[0];
		if(isImageFile(file)) {
		var reader = new FileReader(); 
		reader.onload = function(e) {
		 	$("#preview").attr("src", e.target.result);
		}
		reader.readAsDataURL(file);
		} else {
		    alert("이미지 파일만 첨부 가능합니다.");
		    $("#imgFile").val("");
			$("#preview").attr("src", "");
		}
	});
})

//업로드 파일 이미지 파일인지 확인
function isImageFile(file) {
	// 파일명에서 확장자를 가져옴
	var ext = file.name.split(".").pop().toLowerCase(); 
	return ($.inArray(ext, ["jpg", "jpeg", "gif", "png"]) === -1) ? false : true;
}

function openLogin() {
	$("#loginModal").addClass("show");
}

function closeLogin() {
	$("#loginModal").removeClass("show");
	$("#signupArea").removeClass("show");
	$("#loginArea").addClass("show");
	$(".selDiv").children().removeClass("topSel");
	$(".loginBox").addClass("topSel");
	$(".modal_body").css("height", "320px");
	initLogin();
}

function selDiv(sel) {
	if(sel == "login") {
		$("#signupArea").removeClass("show");
		$("#loginArea").addClass("show");
		$(".selDiv").children().removeClass("topSel");
		$(".loginBox").addClass("topSel");
		$(".modal_body").css("height", "320px");
	} else {
		$("#loginArea").removeClass("show");
		$("#signupArea").addClass("show");
		$(".selDiv").children().removeClass("topSel");
		$(".signupBox").addClass("topSel");
		$(".modal_body").css("height", "500px");
	}
}

function signup() {
	var id = $("#signId").val();
	var pw = $("#signPw").val();
	var name = $("#signName").val();
	
	if(id == "") {
		alert("ID를 입력해주세요");
		$("#signId").focus();
		return;
	}
	if (pw == "") {
		alert("패스워드를 입력해주세요");
		$("#signPw").focus();
		return;
	}
	if (name == "") {
		alert("이름을 입력해주세요");
		$("#signName").focus();
		return;
	}
	
    var form = $("#signUpForm")[0];  	           
    var data = new FormData(form);  	     
    console.log(data)
    $.ajax({             
    	type: "POST",          
        enctype: 'multipart/form-data',  
        url: "signup.do",        
        data: data,          
        processData: false,    
        contentType: false,      
        cache: false,               
        success: function (result, status, xhr) { 
        	var code = xhr.getResponseHeader("result");
        	if(code == 1) {
				closeLogin();
				alert("회원가입이 완료되었습니다.");
			} else if(code == 2) {
				alert("이미 존재하는 아이디입니다.");
			} else {
				alert("회원가입에 실패했습니다..");
			}         
        },          
        error: function (e) {  
        	console.log("ERROR : ", e);
        	alert("회원가입에 실패했습니다..");    
         }     
	}); 
}

function initLogin() {
	$("#logId").val("");
	$("#logPw").val("");
	$("#imgFile").val("");
	$("#signId").val("");
	$("#signPw").val("");
	$("#signName").val("");
}

</script>
<div class="modal" id="loginModal">
	<div class="modal_body">
		<div class="m_body">
			<div class="close_btn" id="loginClose">X</div>
			<div class="selDiv">
				<div class="loginBox topSel" onclick="selDiv('login')">로그인</div>
				<div class="signupBox" onclick="selDiv('signup')">회원가입</div>
			</div>
			<div id="loginArea" class="selectedArea show">
				<div class="modal_label">아이디</div>
				<input type="text" class="input_box" id="logId" />
				<div class="modal_label">패스워드</div>
				<input type="password" class="input_box" id="logPw" />
				<div class="modal_btn save" id="log_btn">로그인</div>
			</div>
			<div id="signupArea" class="selectedArea">
			<form method="POST" enctype="multipart/form-data" id="signUpForm">
				<div id="signupImgArea">
					<div class="modal_label">프로필 이미지</div>
					<img id="preview" style="max-width: 100px;">
					<div class="imgLabel">
						<label for="imgFile">
							<span class="btn-upload">이미지 업로드</span>
						</label>
					</div>
  					<input type="file" name="uploadFile" id="imgFile" value="" />
				</div>
				<div class="modal_label">아이디</div>
				<input type="text" class="input_box" id="signId" name="id" />
				<div class="modal_label">패스워드</div>
				<input type="password" class="input_box" id="signPw" name="pw" />
				<div class="modal_label">이름</div>
				<input type="text" class="input_box" id="signName" name="name" />
			</form>
				<div class="modal_btn save" id="signup_btn">회원가입</div>
			</div>
		</div>
	</div>
</div>