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
	div.Title {
		align-items: center;
		font-size: 20px;
		font-weight: bold;
	}
	div.Head > div {
		flex: 1;
		padding: 40px;
	}
	div.listData {
		border: 1px solid #eee;
	}
	div.listData > div {
		flex: 1;
		padding: 40px;
	}
	div.listData:hover {
		border: 1px solid #1E90FF;
	
	}
	div.listPadding {
		padding: 10px 0px;
	}
	div.miniTitle > div {
		flex: 1;
	}
	

</style>

</head>
<body>

	<div class="frame">
		<div class="table">
		<div class="Title">
			<div class="flex miniTitle">
				<div><a href="${cpath }/myPage/myReview?userid=${login.userid}">상품 리뷰</a></div>
				<div><a href="${cpath }/myPage/myStoreReview?userid=${login.userid }">매장 리뷰</a></div>
			</div>
		</div>
			
			<div class="ListBox">
				<div class="flex Head">
					<div>매장명</div>
					<div>별점</div>
					<div>작성일</div>
				</div>
				<c:forEach var="dto" items="${list }">
					<div class="listPadding">
						<div class="flex listData">
							<div>${dto.name }</div>
							<div>${dto.score }</div>
							<div>${dto.reviewDate }</div>
							
						</div>
					</div>
				</c:forEach>
				
			</div>
			
			
		
		</div>
	
	</div>
</body>
</html>