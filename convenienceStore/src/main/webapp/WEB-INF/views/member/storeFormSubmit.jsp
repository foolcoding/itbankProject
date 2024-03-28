<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
		.joinForm {
           width: 1200px;
            margin: auto;
            padding-bottom: 300px;
        }
		.joinLogo {
           font-size: 35px;
           padding-top: 50px;
           padding-bottom: 30px;
        }
     	p.joinContent {
           font-size: 22px;
           padding-bottom: 100px;
        }	
		div.submitPadding {
			padding: 40px 0px;
		}
		input[type="submit"] {
			width: 150px;
			padding: 10px;
			height: 45px;
			background-color: #d5e4fb;
			color: grey;
			font-weight: bold;
			font-size: 15px;
		}
		input {
			width: 300px;
			height: 30px;
			padding: 5px;
			font-size: 15px;
		}
		select {
			width: 300px;
			height: 30px;
			padding: 5px;
			font-size: 16px;
		}
		div.storePadding {
			padding-bottom: 30px;
		}

</style>

</head>
<body>

<div class="frame">
	<div class="joinForm">

	<h2 class="joinLogo">GS25 매장 점주 회원가입</h2>
	
	<p class="joinContent">GS25와 함께 해주셔서 감사합니다. 오늘도 즐거운 하루되세요<br>
		매장점주는 웹마스터의 승인을 통해, 해당 지점 관리기능을 이용할 수 있습니다. 
	</p>

	<form method="POST" class="joinInput">
      <div class="storePadding">
      	매장 선택 : <select name="store_idx">
        			<c:forEach var="item" items="${list}">
            		<option value="${item.idx}">${item.name}</option>
            		<div>${item.idx }</div>
        			</c:forEach>
      			  </select>
      </div>
      <div class="title">아이디</div>
	  <p><input type="text" name="userid" value="${dto.userid }"><p>
	  <div class="title">이름</div>
      <p><input type="text" name="name" required></p>
      
      <div class="submitPadding">
      	<p><input type="submit" value="점주 가입완료"></p>
      </div>
	</form>
</div>
</div>
</body>
</html>