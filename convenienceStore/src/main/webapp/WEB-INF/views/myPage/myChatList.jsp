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
		padding-left: 40px;
	}
	div.couponTitle {
		align-items: center;
		font-size: 20px;
		font-weight: bold;
	}
	div.couponTitle > div {
		flex: 1;
		padding: 40px;
	}
	div.couponList {
		border: 1px solid #eee;
	}
	div.couponList > div {
		flex: 1;
		padding: 40px;
	}
	div.couponList:hover {
		border: 1px solid #1E90FF;
	
	}
	div.listPadding {
		padding: 10px 0px;
	}
	
	
</style>

</head>
<body>

	<div class="frame">
		<div class="table">
		<div class="userCouponTitle">
			<div class="flex couponTitle">
				<h2>문의내역</h2>
				<p>${login.userid }님</p>
			</div>
		</div>
			
			<div class="couponBox">
				<div class="flex couponTitle">
					<div>문의날짜</div>
					<div>진행상태</div>
				</div>
				<c:forEach var="dto" items="${list }">
					<div class="listPadding">
						<div class="flex couponList">
							<div>${dto.chatDate }</div>
							<c:if test="${dto.status == 0}">
								<div>진행중</div>
							</c:if>
							<c:if test="${dto.status == 1 }">
								<div>완료</div>
							</c:if>

						</div>
					</div>
				</c:forEach>
				
			</div>
			
			
		
		</div>
	
	</div>
</body>
</html>