<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	
	.UpdatePadding {
	
		padding-top: 100px;
		padding-bottom: 300px;
	}
	h3 {
		font-size: 20px;
		color : #4E5968;
		padding-bottom: 30px;
	}
	
	#connectButton1 {
		padding: 10px;
		background-color: #1E90FF;
		border: 1px solid #ddd;
  		border-radius: 10px;
  		color: white;
  		font-size: 15px;
	}
	
	#connectButton2 {
		padding: 10px;
		background-color: #eee;
		border: 1px solid #ddd;
  		border-radius: 10px;
  		color : #4E5968;
  		font-size: 15px;
	}
	p {
		font-size: 17px;
	}
	.ButtonBox {
		padding-top: 30px;
		padding-bottom: 20px;
	}
	form {
		padding: 0px 20px;
	}

</style>

</head>
<body>


<div class="frame">
<div class="UpdatePadding">
	<h3>${oauthUser.provider } 계정 연동</h3>

	<fieldset>
		<p>
			${oauthUser.email } 로 이미 가입된 일반 계정이 있습니다<br>
			${oauthUser.provider } 계정을 연결하시면 편리하게 이용이 가능합니다
			지금 계정을 연결할게요
		</p>
		<div class="ButtonBox">
			<form method="POST" style="display: inline">
				<button id="connectButton1">네, 지금 연결할게요</button>
			</form>
			<a href="${cpath }"><button id="connectButton2">다음에 연결할게요</button></a>
		</div>
</fieldset>

</div>
</div>

</body>
</html>