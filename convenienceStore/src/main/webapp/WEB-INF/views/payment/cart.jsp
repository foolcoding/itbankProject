<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="https://js.tosspayments.com/v1" defer></script>
<style>
	.cartFrame{
		width: 1200px;
		margin: 30px auto;
		border: 1px solid #DFE3E8;
		background-color: #F2F2F2;
	}
	div.cart1 > div:first-child {
		font-size: 40px;
		margin-left: 80px;
		margin-top: 40px;
	}
	div.cart1 > div.flex{
		margin-right: 20px;
	}
	div.cart1 > div.flex > div{
		margin: 20px;
		font-size: 20px;
		color: grey;
		margin-top: 50px;
	}
	div.storeName{
		margin: 100px 0 0 20px;
		font-size: 27px;
		border: 1px solid #DFE3E8;
		height: 60px;
		text-align: center;
		padding-top: 30px;
		width: 500px;
	}
	div.cart{
		width: 1100px;
		margin: 50px auto 0 auto;
		position: relative;
	}
	div.cartDetail{
		margin: 20px;
		width: 730px;
		height: 280px;
		border: 1px solid #DFE3E8;
	}
	input[type="checkbox"]{
		border: 1px solid black;
		width: 30px;
		height: 30px;
		border-radius: 20px;
		margin-left: 25px;
		margin-top: 50px;
	}
	div.couponList{
		margin-top: 50px;
		margin-right: 100px;
	}
	div.image{
		margin: 23px;
		width: 230px;
		height: 230px;
		border: 1px solid black;
		border: 0;
	}
	div.image > img{
		width: 230px;
		height: 230px;
	}
	div.title, div.cnt, div.price, div.my, div.eventCnt{
		font-size: 22px;
		font-weight: bold;
	}
	div.delete{
		margin: 20px;
		width: 370px;
		font-size: 20px;
	}
	div.cnt, div.price, div.my, div.eventCnt{
		margin: 20px;
		margin-top: 30px;
	}
	div.payment{
		margin-top: 20px;
		width: 300px;
		height: 250px;
		border: 1px solid #DFE3E8;
		position: sticky;
		top: 20px;
	}
	div.allPrice, div.discPrice{
		margin: 20px;
		font-size: 17px;
	}
	div.resultPrice{
		text-align: right;
		margin: 20px;
		font-size: 17px;
	}
	button.purchaseBtn{
		background-color: #346AFF;
		color: white;
		width: 260px;
		height: 50px;
		margin: 0 20px 20px 20px;
		text-align: center;
		font-size: 18px;
		border: 0;
		border-radius: 10px;
	}
	button.minus, button.plus{
		width: 50px;
		height: 50px;
		font-size: 20px;
		font-weight: bold;
		border: 1px solid #DFE3E8;
	}
	input[type="number"].realCnt{
		font-size: 20px;
		font-weight: bold;
		width: 50px;
		height: 44px;
		border: 1px solid #DFE3E8;
		text-align: center;
	}
	div.selectAll{
		margin-left: 80px;
		height: 100px;
	}
	div.blankCart{
		margin: 100px;
	}
	.modal{
		color: #000000;
	}
    .box{
    	z-index: 3;
    	width: 900px;
    	height: 600px;
    	background-color: white;
    	position: fixed;
    	top: 50%;
    	left: 50%;
    	transform: translate(-50%, -50%);
    	border-radius: 20px;
    	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    	overflow: scroll;
    	overflow-y: auto;
    }
    .overlay{
    	z-index: 2;
    	position: fixed;
    	top: 0;
    	left: 0;
    	width: 100%;
    	height: 100%;
    	background-color: rgba(0, 0, 0, 0.5);
    }
    .hidden{
    	display: none;
    }
	.modal-header {
	    font-size: 25px;
	    font-weight: bold;
	    margin: 30px;
	    color: #333;
	}
	.couponSelect{
		background-color: #F1F1F1;
    	padding: 10px 20px;
    	text-align: center;
    	display: block;
    	width: 100px;
    	height: 40px;
    	margin: 0 auto;
    	cursor: pointer;
    	border: 0;
    	border-radious: 10px;
	}
	.couponSelect:hover{
		cursor: pointer;
	}
  	#close {
		background-color: #F1F1F1;
    	padding: 10px 20px;
    	text-align: center;
    	display: block;
    	width: 100px;
    	height: 40px;
    	margin: 0 auto;
    	cursor: pointer;
    	border: 0;
    	border-radious: 10px;
  	}
  	#close:hover{
  		background-color: #D8D8D8;
  		transition-duration: 0.2s;
  	}
  	table{
  		margin: 0 auto;
  		width: 95%;
  	}
  	th{
  		height: 50px;
  		border-top: 1px solid #666666;
  		border-bottom: 1px solid #666666;
  	}
  	td{
  		padding: 20px;
  	}
  	tbody > tr:hover{
  		background-color: #F7F7F7;
  		transition-duration: 0.2s;
  	}
  	tbody > tr > td:first-child {
		margin-left: 50px;
		font-weight: bold;
	}
	.coupon-discvalue{
		text-align: center;
	}
	#open{
		background-color: #F1F1F1;
    	padding: 10px 20px;
    	text-align: center;
    	display: block;
    	width: 150px;
    	height: 60px;
    	margin: 0 auto;
    	cursor: pointer;
    	border: 0;
    	border-radious: 10px;
    	font-size: 19px;
    	font-weight: bold;
	}
	#open:hover{
		background-color: #D8D8D8;
  		transition-duration: 0.2s;
	}
	.closeImg{
		margin: 30px;
		cursor: pointer;
	}
</style>
    <div class="modal hidden">
	    <input type="hidden" id="selectedCouponDiscValue" value="">
		<input type="hidden" id="selectedCouponIdx" value="">
        <div class="box">
        	<div class="flex sb">
        	<div class="modal-header">적용 가능 쿠폰 리스트</div>
        	<div class="closeImg"><img src="${cpath }/resources/image/cart/modalx.png"></div>
        	</div>
        	<table>
        	<thead>
	        	<tr>
	        		<th>쿠폰명</th>
	        		<th>할인금액</th>
	        		<th>쿠폰 선택</th>
	        	</tr>
        	</thead>
        	<tbody>
            <c:forEach var="coupon" items="${userCouponList }">
            	<tr>
            		<td data-idx="${coupon.idx }">${coupon.title }</td>
            		<td class="coupon-discvalue">
            			${coupon.discvalue }${fn:contains(coupon.discvalue,'%') ? '' : '원'}
            		</td>
            		<td class="coupon-btn">
            			<button class="couponSelect" data-discvalue="${coupon.discvalue}" data-idx="${coupon.idx}">쿠폰 선택</button>
            		</td>
            	</tr>
			</c:forEach>
			</tbody>
			</table>
			<p align="center"><button id="close">닫기</button></p>
        </div>
        <div class="overlay"></div>
    </div>

<div class="cartFrame">
	<div class="flex sb cart1">
		<div>장바구니</div>
		<div class="flex">
			<div><b>장바구니</b></div>
			<div>주문완료</div>
		</div>
	</div>
	<c:if test="${empty list }">
		<div class="blankCart">
			<img src="${cpath }/resources/image/cart/blankCart.png" width="1000px" height="1000px">
		</div>
	</c:if>
	<c:if test="${not empty list }">
	<div class="storeName"><a href="${cpath }/product/storeList/${store_idx}">현재 매장 : ${storeName }</a></div>
	<div class="selectAll flex sb">
		<div>
			<input type="checkbox" id="selectAll">
			<label for="selectAll">전체 선택</label>
		</div>
		<div class="couponList">
			<button id="open">쿠폰선택</button>
		</div>
	</div>
	<div class="cart2">
		<div class="cart flex">
			<div class="cartItems">
				<c:forEach var="cart" items="${list }">
				<div class="cartDetail flex">
					<div><input type="checkbox" class="productCheckbox" name="idx" value="${cart.idx }"></div>
					<div class="image">
						<c:if test="${fn:contains(cart.image, 'http')}">
							<img src='${cart.image}'>
						</c:if>
						<c:if test="${not fn:contains(cart.image, 'http')}">
							<img src='${cpath }/upload/${cart.image}'>
						</c:if>
					</div>
					<div>
						<div class="delete flex sb">
							<div class="title">
								<div class="productName">
									${cart.name }
								</div>				
								<c:if test="${not fn:contains(cart.event,'없음') }">
									[${cart.event }]
								</c:if>
							</div>
							<div><a href="${cpath }/payment/cart/delete/${cart.idx}">삭제</a></div>
						</div>
						<div>
							<div class="price">${cart.price }</div>
							<div class="flex sb">
								<div class="cnt">개수 : ${cart.cnt }</div>
								<div class="eventCnt">
									${cart.eventCnt > 0 ? '행사증정 : '.concat(cart.eventCnt) : '' }
								</div>
							</div>
							<div class="my flex sb">
								<div>나만의 냉장고</div>
								<div class="flex">
									<button type="button" class="minus">-</button>
									<input type="number" class="realCnt" value="0" min="0" max="${cart.cnt + cart.eventCnt }" readonly>
									<button type="button" class="plus">+</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				</c:forEach>
			</div>
			<div class="payment">
				<div class="allPrice flex sb">
					<div>총 상품 가격</div>
					<div id="allPrice"><b>0</b>원</div>
				</div>
				<div class="discPrice flex sb">
					<div>할인 금액</div>
					<div id="discPrice"><b>-0</b>원</div>
				</div>
				<hr>
				<div class="resultPrice">
					<div id="resultPrice"><b>0</b>원</div>
				</div>
				<button id="purchaseBtn" class="purchaseBtn">구매하기 (<span id="checkedCount">0</span>)</button>
			</div>
		</div>
	</div>
	</c:if>
</div>

<script>

	// 모달 눌렀을때 버튼
	const btnOpen = document.getElementById('open')
	const btnClose = document.getElementById('close')
	const closeImg = document.querySelector('div.closeImg')
	const modal = document.querySelector('div.modal')
	
	const openHandler = function(event){
		modal.classList.remove('hidden')
	}
	
	const closeHandler = function(event){                    
	    modal.classList.add('hidden')
	}
	
	btnOpen.onclick = openHandler
	btnClose.onclick = closeHandler
	closeImg.onclick = closeHandler

	// 나만의 냉장고 버튼 마이너스와 플러스 한번에 불러옴
	document.querySelectorAll('.minus, .plus').forEach(function(button) {
	    button.addEventListener('click', function(event) {
	    	// 버튼 클래스에 마이너스가 포함되어 있는지
	        const isMinus = event.target.classList.contains('minus')
	        // 마이너스가 맞다면 버튼의 다음에있는 요소, 아니라면(플러스) 그 전에 있는 요소 참조 (input type="number")
	        const input = isMinus ? event.target.nextElementSibling : event.target.previousElementSibling
	        let currentValue = +input.value
	        const maxCount = input.getAttribute('max')
	
	        if (isMinus && currentValue > 0) {
	            input.value = currentValue - 1
	        } else if (!isMinus && currentValue < maxCount) {
	            input.value = currentValue + 1
	        }
	    })
	})
	
	// 체크박스 누르면 가격 합산해서 표시, 체크된 체크박스 개수
	function updateCheckedCountAndPrice() {
		let total = 0
		let sumPrice = 0
	    let checkboxes = document.querySelectorAll('.productCheckbox:checked')
	    let discvalue = document.getElementById('selectedCouponDiscValue').value

	    // 체크된 체크박스 개수
	    document.getElementById('checkedCount').textContent = checkboxes.length
	    
	    // 체크된 상품 개수 * 가격
	    checkboxes.forEach(function(checkbox) {
	        let cartItem = checkbox.closest('.cartDetail')
	        let cnt = +cartItem.querySelector('.cnt').textContent.replace('개수 : ', '').trim()
	        let price = +cartItem.querySelector('.price').textContent.replace(',', '').replace('가격 : ', '').replace('원', '').trim()
	        
	        sumPrice += cnt * price
    	})
        if(discvalue.includes('%')){	// % 포함될때 할인 계산
        	discvalue = +discvalue.replace('%', '')
        	discvalue = 100 / discvalue
	    	discvalue = sumPrice / discvalue
	    }
        else{	// % 포함되지않을때 toLocaleString() 적용시키기 위함
			discvalue = +discvalue
		}
     	total = sumPrice  - discvalue
		if(total < 0){	// 할인금액이 총 상품 가격 넘을때 0원으로 표시
			total = 0
		}
	    document.getElementById('allPrice').innerHTML = '<b>' + sumPrice.toLocaleString() + '</b>원'
 	    document.getElementById('discPrice').innerHTML = '<b>-' + discvalue.toLocaleString() + '</b>원'
	    document.getElementById('resultPrice').innerHTML = '<b>' + total.toLocaleString() + '</b>원'
	    return total
	}
	
	// 체크박스에 변화가생기면 updateCheckedCountAndPrice 함수 실행
	document.querySelectorAll('.productCheckbox').forEach(function(checkbox) {
	    checkbox.addEventListener('change', updateCheckedCountAndPrice)
	})
	
	// 쿠폰 선택 버튼을 누르면 쿠폰 선택화면 띄움
	document.querySelectorAll('.couponSelect').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const discValue = event.target.getAttribute('data-discvalue')
            const idx = event.target.getAttribute('data-idx')
            document.getElementById('selectedCouponDiscValue').value = discValue
            document.getElementById('selectedCouponIdx').value = idx
            updateCheckedCountAndPrice()
            modal.classList.add('hidden')
        })
    })
	
	// 가격에 천원단위로 ,붙이는 함수 (Locale)로 변환
	function intToLocale(){
		const divPrice = document.querySelectorAll('.price')
		
		divPrice.forEach(e => {
			let price = +e.textContent.trim()
			e.innerText = '가격 : ' + price.toLocaleString() + '원'
		})
	}
	
	// 전체 선택하면 체크박스들이 전부 체크 되고 가격합산되게 함
	document.addEventListener('DOMContentLoaded', function() {
	    let selectAllCheckbox = document.getElementById('selectAll')
	    
	    selectAllCheckbox.addEventListener('change', function() {
	        let productCheckboxes = document.querySelectorAll('.productCheckbox')
	        
	        // 모든 상품 체크박스와 전체선택 체크박스의 상태가 동일하게 만듬
	        productCheckboxes.forEach(function(checkbox) {
	            checkbox.checked = selectAllCheckbox.checked
	        })
	        
	        updateCheckedCountAndPrice()
	    })
	    
	    // 상품이 체크된 박스의 개수와 상품의 개수가 같으면 체크, 반대면 체크 안댐
	    document.querySelectorAll('.productCheckbox').forEach(function(checkbox) {
	        checkbox.addEventListener('change', function() {
	            let allChecked = document.querySelectorAll('.productCheckbox:checked').length === document.querySelectorAll('.productCheckbox').length
	            selectAllCheckbox.checked = allChecked

	            updateCheckedCountAndPrice()
	        })
	    })
	})
	
	window.addEventListener("DOMContentLoaded", updateCheckedCountAndPrice)
	window.addEventListener("DOMContentLoaded", intToLocale)
	
	// 현제시간 기준으로 랜덤값 생성
	function generateUniqueRandom() {
	    const timePart = new Date().getTime()
	    const randomPart = Math.random().toString(36).substring(2, 15)
	    return timePart + '-' + randomPart
	}
	
	// 구매하기 버튼 눌렀을때 실행
	document.getElementById('purchaseBtn').addEventListener('click', function() {
		
		const tossPayments = TossPayments('test_ck_6bJXmgo28eNOA6yQyObw3LAnGKWx')
		const amount = updateCheckedCountAndPrice()
		const orderId = generateUniqueRandom()
		
		// 폼 데이터 생성
	    let formData = new FormData()
		
		let successUrl = window.location.origin + '${cpath}/payment/success?'
		
		// 체크된 상품의 목록을 가져옴
	    let checkedItems = document.querySelectorAll('.productCheckbox:checked');
	    let orderName = '픽업주문';
	    if(checkedItems.length > 0) {
	        // 첫 번째 상품의 이름을 가져옴
	        let firstItemName = checkedItems[0].closest('.cartDetail').querySelector('.productName').textContent.trim();
	        // 첫 번째 상품명 뒤에 "외 n건" 추가
	        let length = checkedItems.length - 1
	        orderName = firstItemName + (length > 1 ? '외' + length + '건' : '')
	        successUrl += 'orderName=' + orderName + '&'
	    }

	    // 체크박스의 value(cart의 idx)를 담음
	    document.querySelectorAll('.productCheckbox:checked').forEach(function(item) {
	        formData.append('idx', item.value)
	    	successUrl += 'idx=' + item.value + '&'
	    	
	    	// 나만의 냉장고 개수 받기
	    	let storageCnt = item.closest('.cartDetail').querySelector('.realCnt').value
	    	successUrl += 'storageCnt=' + storageCnt + '&'
	    	
	    })
	    
	    // 선택된 쿠폰의 idx를 successUrl에 추가
	    let selectedCouponIdx = document.getElementById('selectedCouponIdx').value
	    if(!selectedCouponIdx){
	    	selectedCouponIdx = 0
	    }
	    successUrl += 'couponIdx=' + selectedCouponIdx  + '&'
	    
	    // 아무 체크도 없을때
	    if(formData.getAll('idx').length == 0) {
	        alert('상품을 선택해주세요.')
	        return
	    }
		if(formData.getAll('idx').length > 0 && amount == 0){
			alert('할인 금액이 총 상품 가격을 넘을 수 없습니다.')
	        return
		}
		
		successUrl += 'method=카드&'
		
		tossPayments.requestPayment('카드', {	
			orderId: orderId,
			orderName: orderName,
			amount: amount,
			successUrl: successUrl,
			failUrl: window.location.origin + '${cpath}/payment/fail'
		})
	})
    
</script>
</body>
</html>