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
	div.LikeTitle {
		align-items: center;
		font-size: 20px;
		font-weight: bold;
	}
	div.likeHead > div {
		flex: 1;
		padding: 40px;
	}
	div.ProductLikeList {
		border: 1px solid #eee;
	}
	div.ProductLikeList > div {
		flex: 1;
		padding: 40px;
	}
	div.ProductLikeList:hover {
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
		<div class="LikeTitle">
			<div class="flex miniTitle">
				<div><a href="${cpath }/myPage/myLikeList?userid=${login.userid}">상품 찜목록</a></div>
				<div><a href="${cpath }/myPage/myStoreLike?userid=${login.userid }">매장 찜목록</a></div>
			</div>
		</div>
			
			<div class="LikeBox">
				<div class="flex likeHead">
					<div>이벤트</div>
					<div>상품명</div>
					<div>가격</div>
				</div>
				<c:forEach var="dto" items="${list }">
					<div class="listPadding">
						<div class="flex ProductLikeList">
							<div>
							<c:if test="${dto.event != null}">
								[ ${dto.event } ]
							</c:if>
							</div>
							<div>${dto.name }</div>
							<div>${dto.price }</div>
							
						</div>
					</div>
				</c:forEach>
				
			</div>
			
			
		
		</div>
	
	</div>
</body>
</html>