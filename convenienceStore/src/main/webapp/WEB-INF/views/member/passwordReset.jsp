<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
		.resetForm {
			width: 1200px;
    		margin: auto;
    		padding-bottom: 300px;
    		padding-left: 50px;
		}
		h2.resetMainLogo {
			padding-top: 70px;
			padding-bottom: 50px;
			font-size: 35px;
		}
		div.submitPadding {
			padding: 40px 0px;
		}
		input[type="submit"] {
			width: 200px;
			padding: 10px;
			height: 45px;
			background-color: #d5e4fb;
			color: grey;
			font-weight: bold;
			font-size: 15px;
		}
		.idCheckPadding {
			padding: 0px 50px;
		}
		button#checkUserid {
			border: 1px solid grey;
			width: 90px;
			padding: 10px;
			height: 41px;
		}
		input {
			width: 250px;
			padding: 10px;
			font-size: 17px;
		}

</style>
</head>
<body>

<div class="frame">
	<div class="resetForm">
	<h2 class="resetMainLogo">비밀번호 재설정</h2>
	
		<form>
			<div class="flex">
    			<input id="userid" type="text" name="userid" placeholder="ID" required>
      			<div class="idCheckPadding">
      				<button type="button" id="checkUserid">ID 확인</button>	
      				<span id="result1"></span> 
 		     	</div>
      		</div>
		</form>
		
		<form>
			<p>
				<input type="email" name="email" placeholder="email" disabled>
				<button>인증번호 발송</button>	
			</p>
			<p class="message"></p>
		</form>
		
		<div class="auth box hidden">
			<form>
				<h3>인증번호 확인</h3>
				<p>
					<input type="text" name="authNumber" placeholder="인증번호 입력">
					<button>인증 확인</button>	
				</p>
				<p class="message"></p>
			</form>
		</div>
		
		<div class="reset1 box hidden">
			<form>
				<input type="password" name="userpw" placeholder="비밀번호 입력">
				<input type="password" name="doubleCheck" placeholder="비밀번호 재입력">
					
				<button>일치 여부 확인</button>
				<p class="message"></p>
			</form>
		</div>
		
		<div class="reset2 box hidden">
			<form>
				<button id="resetButton" disabled>비밀번호 재설정</button>
			
				<p class="message"></p>
			</form>
		</div>
		
		<div class="goLogin box hidden">
			<a href="${cpath }/member/login">로그인 하러가기</a>
		</div>

	</div>
</div>


<script>
	const cpath = '${cpath}'
	document.addEventListener('DOMContentLoaded', function() {
    	document.getElementById("checkUserid").addEventListener("click", async function(){
        	const userId = document.getElementById('userid').value
        	const url = cpath + '/exist?userid=' + userId
        	const text = await fetch(url).then(resp => resp.text())
        	document.getElementById("result1").innerText = text 
        	
        	// 이메일 입력 필드를 찾습니다.
        	const emailInput = document.querySelector('input[name="email"]');
        	
        	// "계정이 존재합니다" 문자열에 따라 이메일 입력 필드를 활성화 또는 비활성화 합니다.
        	if (text === "계정이 존재합니다.") {
            	emailInput.disabled = false; // 계정이 존재하면 이메일 입력을 활성화
        	} else {
            	emailInput.disabled = true; // 계정이 존재하지 않으면 이메일 입력을 비활성화
        	}
        	
    	})
	})


	const mailSendForm = document.forms[1]		//	form은 배열로 바로 불러올 수 있음
	const authForm = document.forms[2]
	
	mailSendForm.onsubmit = async function(event) {
		event.preventDefault()
		const url = cpath + '/sendMail'
		const opt = {
				method: 'POST',
				body : JSON.stringify ({
					address: event.target.querySelector('input').value
				}),
				headers: {
					'Content-Type' : 'application/json;charset=utf-8'
				}
		}
		const result = await fetch(url, opt).then(resp => resp.text())
		const message = event.target.querySelector('p.message')
		if(result == 1) {
			message.innerText = '메일을 전송했습니다'
			message.style.color = 'blue'
			document.querySelector('.auth').classList.remove('hidden')
		}
		else {
			message.innerText = '메일을 보낼 수 없습니다'
			message.style.color = 'red'
		}
	}
	

	authForm.onsubmit = async function(event) {
		event.preventDefault()
		
		const inputNumber = event.target.querySelector('input').value
		const url =  cpath + '/authNumber/' + inputNumber	
		const result = await fetch(url).then(resp => resp.text())
		const message = event.target.querySelector('p.message')
		
		if(result == 1) {
			message.innerText = '인증 성공'
			message.style.color = 'blue'
			document.querySelector('.reset1').classList.remove('hidden')
			document.querySelector('.reset2').classList.remove('hidden')
		}
		else {
			message.innerText = '인증 실패'
			message.style.color = 'red'
		}
	}

	
	const resetAuthForm = document.forms[3]
	
	resetAuthForm.onsubmit = async function(event) {
		event.preventDefault()
		
		const url = cpath + '/doubleChecking'
		const opt = {
				method: 'POST',
				body : JSON.stringify ({
					userpw : event.target.querySelector('input[name="userpw"]').value,
					doubleCheck : event.target.querySelector('input[name="doubleCheck"]').value
				}),
				headers: {
					'Content-Type' : 'application/json;charset=utf-8'
				}
		}
		const result = await fetch(url, opt).then(resp => resp.text())
		const message = event.target.querySelector('p.message')
		
		const resetButton = document.getElementById('resetButton')
		
		if(result == 1) {
			message.innerText = '비밀번호가 일치합니다.'
			resetButton.disabled = false
		}
		else {
			message.innerText = '비밀번호가 불일치합니다.'
			resetButton.disabled = true
		}
	}
	
	
	const resetFinishForm = document.forms[4]
	
	resetFinishForm.onsubmit = async function(event) {
		
		event.preventDefault()
		
		const url = cpath + '/resetFinish'
		const opt = {
				method: 'POST',
				body : JSON.stringify ({
					userid : document.forms[0].querySelector('input[name="userid"]').value,
					userpw : document.forms[3].querySelector('input[name="userpw"]').value
				}),
				headers: {
					'Content-Type' : 'application/json;charset=utf-8'
				}
		}
		const result = await fetch(url, opt).then(resp => resp.text())
		const message = event.target.querySelector('p.message')
		if(result == 1) {
			message.innerText = '비밀번호 재설정을 성공했습니다.'
			document.querySelector('.goLogin').classList.remove('hidden')
		}
		else {
			message.innerText = '비밀번호 재설정을 실패하였습니다.'
		}
	}
	


	
</script>


</body>
</html>