<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<style>
   .boxFrame {
      width: 1200px;
       margin: auto;
   }
   table {
      border-collapse: collapse;
      border-spacing: 0;
      width: 100%;
      text-align: center;
   }
   thead > tr {
      background-color: #f8f8f8;
      border-top: 2px solid #666666;
      border-bottom: 1px solid #FFFFFF;
      height: 65px;
   }
   thead > tr > th {
      font-size: 18px;
      font-weight: bold;
   }
   tbody > tr {
      height: 100px;
      border-bottom: 1px solid #B2B2B2;
   }
   tbody > tr > td.title {
      font-size: 20px;
      font-weight: bold;
   }
   h2 {
      margin: 20px;
   }
   .center {
      text-align: center;
      color: grey;
   }
   .bold {
      font-weight: bold;
   }
   .boxFrame > div.flex {
      margin-top: 50px;
      margin-bottom: 50px;
   }

   
</style>

<div class="boxFrame">
   <div class="flex">
      <h2><a href="${cpath }/myPage/myShoppingSave?userid=${login.userid}">픽업 가능</a></h2>
      <h2><a href="${cpath }/myPage/myShoppingSaveUse?userid=${login.userid}">픽업 완료</a></h2>
      
   </div>
   
   
   <div class="boxList">
      <table>
         <thead>
            <tr>
               <th>상품명</th>
               <th>총 주문개수</th>
               <th>픽업가능 개수</th>
            </tr>
         </thead>
         <tbody>
            <c:forEach var="dto" items="${list }">
               <tr>
                  <td>${dto.name }</td>
                  <td>${dto.totalCount }</td>
                  <td>${dto.rescount}</td>
               </tr>
            </c:forEach>
         </tbody>
      </table>
   </div>
   

   
</div>

</body>
</html>