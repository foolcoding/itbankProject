<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

	p {
		font-size: 18px;
	}
	.myPageBox {
		width: 1000px;
		margin: auto;
		padding-bottom: 300px;
	}
	.myPageTop {
		padding-top: 150px;
		padding-bottom: 50px;
   	 	align-items: center;
	}
	.myPageTitle {
		color : #1E90FF;
		font-size: 50px;
		padding-right: 60px;
	}
	.myPageUserid {
		font-size: 35px;
		color : #4E5968;
	}
	.myLocation {
		align-items: center;
		padding-bottom: 100px;
	}
	#LocationPadding {
		padding-right: 60px;
	}
	#LocationButton {
		background-color: #1E90FF;
		width: 250px;
		padding: 10px;
		font-size: 15px;
		border: 1px solid #ddd;
  		border-radius: 10px;
		color : white;
		box-sizing: content-box;
	}
	.myList {
		width: 900px;
		border: 1px solid #ddd;
		height: 130px;
		text-align: center;
		align-items: center;
		border-radius: 5px;
	}
	.listBox {
		width: 20px;
		height: 30px;
		font-size: 18px;
		flex: 1;
		
	}

	.myInfo, .payInfo {
		border-bottom: 1px solid #eee;
	}
	.myInfo {
		padding-top: 80px;
	}
	.chatInfo {
		padding-bottom: 80px;
	}
	.headTitle {
		font-size: 28px;
		color : #4E5968;
	}

	div.flex > p {
		flex: 1;
	}
	.logoutButton {
		width: 150px;
		height: 40px;
		padding: 5px;
		font-size: 16px;
		border: 1px solid #ddd;
		color : #4E5968;
  		border-radius: 7px;
  		font-weight: bold;
	}
	.infoBox {
		width: 902px;
	}
</style>

</head>
<body>


<div class="frame">
	<div class="myPageBox">
		<div class="flex myPageTop">
			<h2 class="myPageTitle">WELCOME</h2>
			<p class="myPageUserid">${login.userid } 님</p>
		</div>
		<div class="flex myLocation">
			<div id="LocationPadding"><a href="${cpath }/myPage/myPageLocation"><button id="LocationButton">현재 위치 지정하러가기</button></a></div>
		
		<c:if test="${empty list}">
		    <p>위치가 지정되어 있지 않습니다.</p>
		</c:if>
		<c:forEach var="dto" items="${list}">
		    <c:if test="${dto != null}">
		        <p>${dto.address}</p>
		    </c:if>
		    <c:if test="${dto == null}">
		        <p>위치가 지정되어있지 않습니다</p>
		    </c:if>
		</c:forEach>

		</div>
		<div class="flex myList">
			<div class="listBox"><a href="${cpath }/myPage/myBasket">장바구니</a></div>
			<div class="listBox"><a href="${cpath }/myPage/myCoupon?userid=${login.userid}">보유쿠폰</a></div>
			<div class="listBox"><a href="${cpath }/myPage/myLikeList?userid=${login.userid}">찜 목록</a></div>
			<div class="listBox"><a href="${cpath }/myPage/myReview?userid=${login.userid}">나의 후기</a></div>
		</div>
		<div class="infoBox">
		<div class="myInfo">
			<h3 class="headTitle">나의 정보</h3>
			<div class="flex">
				<p><a href="${cpath}/myPage/myInfo?userid=${login.userid}">개인정보 관리</a></p>
				<p><a href="${cpath}/myPage/myDelete">회원탈퇴</a></p>
			</div>
		</div>
		<div class="payInfo">
			<h3 class="headTitle">주문 정보</h3>
			<div class="flex">
				<p><a href="${cpath }/myPage/myShopping?userid=${login.userid}">주문 내역</a></p>
				<p><a href="${cpath }/myPage/myShoppingSave?userid=${login.userid}">나만의 냉장고</a></p>
			</div>
		</div>
		<div class="chatInfo">
			<h3 class="headTitle">문의 내역</h3>
			<div class="flex">
				<p><a href="${cpath }/myPage/myChatList?userid=${login.userid}">1:1 문의 내역</a></p>
				<p><a href="${cpath }/myPage/myFAQ">자주 묻는 질문</a></p>
			</div>
		</div>
		
		<div>
			<a href="${cpath }/member/logout"><button class="logoutButton">로그아웃</button></a>
		</div>
	</div>
</div>
</div>

</body>
</html>