<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>


<!DOCTYPE html>
<html>
<head>

<style>
		input {
			width: 300px;
			height: 30px;
			padding: 5px;
			font-size: 15px;
			border: 1px solid #ddd;
  			border-radius: 10px;
		}
		.joinForm {
 			padding-left: 30%;
            padding-bottom: 300px;
            padding-top: 100px;
        }
        .joinLogo {
           font-size: 25px;
           padding-top: 50px;
           padding-bottom: 30px;
           color : #5d6a7c;
        }
	
        div.submitPadding {
			padding: 40px 0px;
		}
		input[type="submit"] {
			width: 150px;
			padding: 10px;
			height: 45px;
			background-color: #1E90FF;
			color: white;
			font-weight: bold;
			font-size: 15px;
		}
		button.idCheck {
			border: 1px solid grey;
			width: 100px;
			padding: 10px;
		}
	
		div.idCheckPadding {
			padding-left: 100px;
		}
		button#checkNickNameButton {
			font-size: 15px;
			width: 130px;
			height: 40px;
			padding: 5px;
		}
		button#checkIdButton {
			font-size: 15px;
			width: 130px;
			height: 40px;
			padding: 5px;
		}
		div.title {
			font-size: 15px;
			color : #4E5968;
			padding-top: 30px;
			padding-bottom: 10px;
			font-weight: bold;
		}
		#checkIdButton {
			width: 290px;
			height: 30px;
			padding: 5px;
			font-size: 16px;
			border: 1px solid #ddd;
			color : #4E5968;
		}
		#checkNickNameButton {
			width: 290px;
			height: 30px;
			padding: 5px;
			font-size: 16px;
			border: 1px solid #ddd;
			color : #4E5968;
		}

</style>
</head>
<body>



<div class="frame">
	<div class="joinForm">
	<h2 class="joinLogo">매장 점주 회원가입</h2>

	
   <form method="POST" class="joinInput">
    
      	<div class="title">아이디</div>
      		<div class="flex">
      		<input id="userid" type="text" name="userid" required autofocus>
      		<div class="idCheckPadding">
      			<button type="button" id="checkIdButton">ID 중복확인</button>	
      			<span id="result1"></span> 
      		</div>
      </div>
      
      <div class="title">비밀번호</div>
      <input type="password" name="userpw" required>
      <div class="title">이메일</div>
      <input type="email" name="email" required>
      
      
      <div class="title">닉네임</div>
      <div class="flex">
    	<input id="nickname" type="text" name="nickname" required>
      	<div class="idCheckPadding">
      		<button type="button" id="checkNickNameButton">닉네임 중복확인</button>	
      		<span id="result2"></span> 
      	</div>
      </div>
      <div class="title">전화번호</div>
      <input type="text" name="pnum" placeholder="-로 구분하여 입력하세요" required>
      
      <div class="submitPadding">
      	<p><input id="submitButton" type="submit" value="회원계정 생성하기" disabled="disabled"></p>
      </div>
   
   </form>
	</div>
</div>

<script>

let statusCheck = {
	    func1: false,
	    func2: false
	};



const cpath = '${cpath}'
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById("checkIdButton").addEventListener("click", async function(){ // async 키워드 추가
        const userId = document.getElementById('userid').value
        const url = cpath + '/checkUserid?userid=' + userId
        const text = await fetch(url).then(resp => resp.text())
        document.getElementById("result1").innerText = text // 변환된 텍스트를 화면에 표시
    
        if(text == "사용 가능한 아이디입니다") {
            statusCheck.func1 = true;
        } else {
            statusCheck.func1 = false;
        }
        updateButtonStatus();
        
    })
})

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById("checkNickNameButton").addEventListener("click", async function(){
        const nickName = document.getElementById('nickname').value
        const url = cpath + '/checkNickName?nickname=' + nickName
        const text = await fetch(url).then(resp => resp.text())
        document.getElementById("result2").innerText = text // 변환된 텍스트를 화면에 표시
    	
        if(text == "사용 가능한 닉네임입니다") {
            statusCheck.func2 = true;
        } else {
            statusCheck.func2 = false;
        }
        updateButtonStatus();
        
    })
})


function updateButtonStatus() {
    // 두 함수의 결과가 모두 true일 때만 버튼을 활성화
    if(statusCheck.func1 && statusCheck.func2) {
        document.getElementById("submitButton").disabled = false;
    } else {
        document.getElementById("submitButton").disabled = true;
    }
}


</script>




</body>
</html>