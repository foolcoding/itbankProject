<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
	.deleteForm {
		width: 900px;
		margin: auto;
		padding-top: 60px;
		padding-bottom: 300px;
	}	
	form {
		text-align: center;
	}
	input {
		width: 330px;
		height: 35px;
		padding: 5px;
		font-size: 18px;
		border: 1px solid #ddd;
  		border-radius: 10px;
  		margin-left: 10px;
	}	
	input[type="submit"] {
		width: 120px;
		padding: 10px;
		height: 50px;
		background-color: #1E90FF;
		color: white;
		font-weight: bold;
		font-size: 16px;
		border-radius: 15px;
	}
	h3 {
		font-size: 23px;
		color : #4E5968;
		font-weight: bold;
		padding-left: 50px;
		padding-bottom: 70px;
	}
	 div.leftText {
		padding-right: 270px;
		padding-bottom: 10px;
		color : #4E5968;
		font-size: 16px;
	}
	div.inputPadding {
		padding-bottom: 60px;
	}
	p {
		color : #4E5968;
		font-size: 17px;
		font-weight: bold;
	}


</style>


</head>
<body>



<div class="frame">
	<div class="deleteForm">
	
		<h3>회원 탈퇴</h3>
		
		<form method="POST">
			<div class="inputPadding">
				<div class="leftText">아이디</div>
				<input type="text" name="userid" value="${login.userid }" readonly>
			</div>
			<div class="inputPadding">
				<div class="leftText">비밀번호</div>
				<input type="password" name="userpw" required="required">
			</div>
			<p>진짜 ... 가시려고요?</p>
			<p><input type="submit" value="회원탈퇴"></p>
		</form>	
	
	
	</div>

</div>





</body>
</html>