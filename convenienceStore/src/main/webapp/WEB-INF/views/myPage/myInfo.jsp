<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.tablePadding {
		padding-left: 80px;
		padding-top: 100px;
		padding-bottom: 300px;
	}

	table {
		width: 800px;
		border: 1px solid #ccc;
	}
	th {
		width: 120px;
		background-color: #eef;
		color: #444;
	}
	td {
		font-size: 17px;
	}
	input {
			width: 270px;
			height: 25px;
			padding: 5px;
			font-size: 15px;
			border: 1px solid #ddd;
  			border-radius: 5px;
	}
</style>
</head>
<body>

	<div class="frame">
		<div class="tablePadding">
		<c:forEach var="dto" items="${list }">
			<table border="1" cellpadding="20" cellspacing="0" style="border-collapse: collapse; border-color: #ccc;">
				<tr>
					<th>아이디</th>
					<td>
						${dto.userid }
						<input type="hidden" name="userid" value="${dto.userid }">
					</td>
				</tr>
				<tr>
					<th>닉네임</th>
					<td>${dto.nickname }</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="password" name="userpw">
					</td>
				</tr>
				<tr>
					<th>비밀번호 재확인</th>
					<td>
						<div class="flex">
							<form>
							<input type="password" name="doubleCheck">
							<button>일치 여부 확인</button>
							<p class="message"></p>
							</form>
							
						</div>
							<form>
								<button id="resetButton" disabled>비밀번호 재설정</button>
								<p class="result"></p>
							</form>
					</td>
					
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<form>
							<input type="email" name="email" value="${dto.email }">
							<button>수정</button>
							<p class="update"></p>
						</form>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<form>
							<input type="text" name="pnum" value="${dto.pnum }">
							<button>수정</button>
							<p class="updatePnum"></p>
						</form>
					</td>
				</tr>
		
			</table>
		</c:forEach>
		</div>
		
		<div class="delete">
			<form>
				<input type="text" name="" value="${dto.pnum }">
				<button>수정</button>
				<p class="updatePnum"></p>
			</form>
		
		</div>
	</div>

<script>
	
const checkingForm = document.forms[0]
const cpath = '${cpath}'

checkingForm.onsubmit = async function(event) {
	event.preventDefault()
	
	const url = cpath + '/doubleChecking'
	const opt = {
			method: 'POST',
			body : JSON.stringify ({
				userpw : document.querySelector('input[name="userpw"]').value,
				doubleCheck : document.querySelector('input[name="doubleCheck"]').value
			}),
			headers: {
				'Content-Type' : 'application/json;charset=utf-8'
			}
	}
	const result = await fetch(url, opt).then(resp => resp.text())
	const message = event.target.querySelector('p.message')
	
	const resetButton = document.getElementById('resetButton')
	
	if(result == 1) {
		message.innerText = '*비밀번호가 일치합니다.'
		resetButton.disabled = false
	}
	else {
		message.innerText = '*비밀번호가 불일치합니다.'
		resetButton.disabled = true
	}
}



const resetFinishForm = document.forms[1]

resetFinishForm.onsubmit = async function(event) {
	
	event.preventDefault()
	
	const url = cpath + '/resetFinish'
	const opt = {
			method: 'POST',
			body : JSON.stringify ({
				userid : document.querySelector('input[name="userid"]').value,
				userpw : document.querySelector('input[name="userpw"]').value
			}),
			headers: {
				'Content-Type' : 'application/json;charset=utf-8'
			}
	}
	const result = await fetch(url, opt).then(resp => resp.text())
	const message = event.target.querySelector('p.result')
	if(result == 1) {
		message.innerText = '비밀번호 재설정을 성공했습니다.'
		message.style.color = 'blue'
	}
	else {
		message.innerText = '비밀번호 재설정을 실패하였습니다.'
	}
}


const EmailResetForm = document.forms[2]

EmailResetForm.onsubmit = async function(event) {
	
	event.preventDefault()
	
	const url = cpath + '/EmailReset'
	const opt = {
			method: 'POST',
			body : JSON.stringify ({
				userid : document.querySelector('input[name="userid"]').value,
				email : document.querySelector('input[name="email"]').value
			}),
			headers: {
				'Content-Type' : 'application/json;charset=utf-8'
			}
	}
	const result = await fetch(url, opt).then(resp => resp.text())
	const message = event.target.querySelector('p.update')
	if(result == 1) {
		message.innerText = '변경 성공'
	}
	else {
		message.innerText = '변경실패'
	}
}



const pnumResetForm = document.forms[3]

pnumResetForm.onsubmit = async function(event) {
	
	event.preventDefault()
	
	const url = cpath + '/pnumReset'
	const opt = {
			method: 'POST',
			body : JSON.stringify ({
				userid : document.querySelector('input[name="userid"]').value,
				pnum : document.querySelector('input[name="pnum"]').value
			}),
			headers: {
				'Content-Type' : 'application/json;charset=utf-8'
			}
	}
	const result = await fetch(url, opt).then(resp => resp.text())
	const message = event.target.querySelector('p.updatePnum')
	if(result == 1) {
		message.innerText = '변경 성공'
	}
	else {
		message.innerText = '변경실패'
	}
}



</script>

</body>
</html>