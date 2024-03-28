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
				<h2>보유쿠폰</h2>
				<p>${login.userid }님</p>
			</div>
		</div>
			
			<div class="couponBox">
				<div class="flex couponTitle">
					<div>쿠폰명</div>
					<div>할인</div>
					<div>발행일</div>
					<div>종료일</div>
					<div>사용여부</div>
				</div>
				<c:forEach var="dto" items="${list }">
					<div class="listPadding">
						<div class="flex couponList">
							<div>${dto.title }</div>
							<div>${dto.discValue }</div>
							<div>${dto.issueDate }</div>
							<div>${dto.endDate }</div>
							<div>${dto.usedDate }</div>
						</div>
					</div>
				</c:forEach>
				
			</div>
			
			
		
		</div>
	
	</div>
</body>
</html>