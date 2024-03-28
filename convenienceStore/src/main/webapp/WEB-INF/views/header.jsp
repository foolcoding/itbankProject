<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cpath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>우리 동네 편의점</title>
<style>
      body{
         margin: 0;
         padding: 0;
      }
      .frame {
           width: 1200px;
           margin: auto;
       }
       .flex {
           display: flex;
       }
       .sb {
           justify-content: space-between;
       }
       ul > li {
          list-style: none;
          padding-left: 0px;
       }
       ul {
          padding-left: 0px;
       }
       a {
           color: inherit;
           text-decoration: none;
       }
       a:hover {
           text-decoration: underline;
       }
       .hidden {
           display: none;
       } 
       .headerMenu > div {
          margin-left: 20px;
       }
       .headerMenu > div > a ,
       div.locationSelect > a{
          font-size: 17px;
       }
       .topForm {
          width: 1200px;
           margin: auto;
       }
       .topMenuBorder {
          width: 100%;
          height: 120px;
          border-top: 5px solid #0072BC;
          border-bottom: 2px solid #DFDFDF;
       }
       .topList {
          width: 1200px;
       }
       .topButton {
          padding-top: 20px;
          padding-bottom: 50px;
       }
       #logo {
          width: 130px;
          padding-left: 0px;
       }
       .menuText > li {
          flex : 1;
          display: flex;
          justify-content: center; 
          align-items: center; 
          font-size: 23px;
          padding-bottom: 5px;
       }
       .header-cart {
        	position: relative;
       }
       .header-cart-count {
        	position: absolute;
        	font-size: 13px;
        	right: -13px;
        	top: -13px;
        	height: 18px;
        	width: 18px;
        	background-color: red;
        	border-radius: 40px;
        	text-align: center;
        	color: white;
       }
</style>
</head>
<body>

   <div class="frame">
      <div class="topButton flex sb">
      <div class="locationSelect">
         <a href="${cpath }/myLocation">현재 위치 지정하러 가기</a>
      </div>
      <div class="headerMenu flex">
         <c:if test="${empty login }">
            <div><a href="${cpath }/member/login">로그인</a></div>
            <div><a href="${cpath }/member/join">회원가입</a></div>
         </c:if>
         <c:if test="${not empty login }">
            <div>${login.nickname } 님</div>
            <div><a href="${cpath }/member/logout">로그아웃</a></div>
            <c:if test="${login.role == 1 }">
            	<div><a href="${cpath }/member/storeMemberList">점주가입 목록</a></div>
            </c:if>
            <c:if test="${login.role == 3 }">
            	<div class="header-cart"><a href="${cpath }/payment/cart">장바구니</a><div class="${count == '0' ? '':'header-cart-count' }">${count == '0' ? '':count }</div></div>
	            <div><a href="${cpath }/myPage/myPageMain?userid=${login.userid}">마이페이지</a></div>
	            <div><a href="${cpath }/inquiry">1:1 문의</a></div>
            </c:if>
         </c:if>
      </div>
      </div>
   </div>
      <div class="topMenuBorder">
         <div class="topList frame">
            <ul class="flex menuText">
               <li><a href="${cpath }/"><img id="logo" src="${cpath }/resources/image/home/GS25로고.png"></a></li>
               <c:if test="${login.role == 3 || login == null }">
               		<li><a href="${cpath }/product/list">재고찾기</a></li>
               </c:if>
               <c:if test="${login.role == 2 }">
               		<li><a href="${cpath }/productShop/productList">지점관리</a></li>
               </c:if>
               <c:if test="${login.role == 1 }">
               		<li><a href="${cpath }/productAdmin/adminPage">상품관리</a></li>
               </c:if>
               <li><a href="${cpath }/store/list">주문하기</a></li>
               <li><a href="${cpath}/event/list/1">쿠폰</a></li>
               <li><a href="#">프로젝트 소개</a></li>
            </ul>
         </div>
      </div>
   


