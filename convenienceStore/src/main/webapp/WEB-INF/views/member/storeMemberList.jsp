<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	
	h2.ListCheckTitle {
		padding-top: 80px;
		padding-bottom: 30px;
	}
	p.ListCheckTitle {
		padding-bottom: 50px;
	}
	div.storeDetail {
		flex: 1;
		padding-bottom: 20px;
	}
	div.storeDetailData {
		flex: 5;
	}
	.paddingleft {
		padding-left: 50px;
	}
	.buttonPadding {
		padding-left: 150px;
	}
	.buttonTop {
		padding-top: 30px;
	}
	.storeMemberBorder {
		border: 1px solid grey;
		padding: 10px;
	}

</style>


</head>
<body>

	<div class="frame paddingleft">
		<h2 class="ListCheckTitle">매장점주 가입대기목록</h2>
		<p class="ListCheckTitle">매장점주의 가입을 승인 및 삭제할 수 있습니다.</p>
		
		<div id="root"></div>
	</div>
	
<script>

	
    
    function getHTMLfromJson(json) {
    	const cpath = '${cpath}'
		let tag = ''
		json.forEach(dto => {
			tag += `<div class="storeMemberBorder">`
			tag += `	<div class="flex">`
			tag += `		<div class="storeDetail">매장명</div><div class="storeDetailData">\${dto.store_name }</div>`
			tag += `	</div>`
			tag += `	<div class="flex">`
			tag += `		<div class="storeDetail">ID</div><div class="storeDetailData">\${dto.userid}</div>`
			tag += `	</div>`
			tag += `	<div class="flex">`
			tag += `		<div class="storeDetail">이름</div><div class="storeDetailData">\${dto.name}</div>`
			tag += `	</div>`
			tag += `	<div class="flex buttonTop">`
			tag += `		<div><button type="button" class="checkButton">승인</button>`
			tag += `			<input type="hidden" name="userid" value="\${dto.userid }" id="userid">`
			tag += `			<span id="result1"></span>`
			tag += `		</div>`
			tag += `		<div class="buttonPadding">`
			tag += `			<button type="button" class="deleteButton">거부</button>`
			tag += `			<input type="hidden" name="userid" value="\${dto.userid }" id="userid">`
			tag += `			<span id="result2"></span>`
			tag += `		</div>`
			tag += `	</div>`
			tag += `</div>`
		})	
		
		console.log(json)
		console.log(tag)
		return tag
	}

    async function loadHandler(){
    	const cpath = '${cpath}'
		const url = cpath + '/managerList'
		const json = await fetch(url).then(resp => resp.json())
		const tag = getHTMLfromJson(json)

		const root = document.querySelector('#root')
		root.innerHTML = tag
	    document.querySelector(".checkButton").addEventListener("click", checkButtonHandler)
	    document.querySelector(".deleteButton").addEventListener("click", deleteButtonHandler)
	}
    
    async function checkButtonHandler(){
    	const cpath = '${cpath}'
    	const userid = document.getElementById('userid').value
    	const url = cpath + '/storeMemberList?userid=' + userid
		const text = await fetch(url).then(resp => resp.text())
		document.getElementById("result1").innerText = text
		loadHandler()
    }

    async function deleteButtonHandler(){
    	const cpath = '${cpath}'
    	const userid = document.getElementById('userid').value
    	const url = cpath + '/deleteManager?userid=' + userid
		const text = await fetch(url).then(resp => resp.text())
		document.getElementById("result2").innerText = text
		loadHandler()
    }
    	

    window.addEventListener('DOMContentLoaded', loadHandler)
 
</script>

	
</body>
</html>