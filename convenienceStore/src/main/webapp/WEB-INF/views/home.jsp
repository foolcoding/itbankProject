<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
	<style>
        body {
            overflow-x: hidden;
        }
        .main1 {
            width: 1200px;
            height: 300px;
            position: relative;
            margin: 50px auto;
        }

        .mainImg {
            position: relative;
            height: 300px;
            overflow: hidden;
        }
        .mainImg > .item {
            box-sizing: border-box;
            position: absolute;
            width: 1200px;
            height: 300px;
            line-height: 300px;
            text-align: center;
            font-size: 40px;
            transition-duration: 1s;
            border-radius: 10px;
        }
        .mainImg > .item:nth-child(5n + 1) { background-color: rgb(252, 190, 199);}
        .mainImg > .item:nth-child(5n + 2) { background-color: rgb(253, 253, 195);}
        .mainImg > .item:nth-child(5n + 3) { background-color: rgb(172, 252, 172);}
        .mainImg > .item:nth-child(5n + 4) { background-color: rgb(165, 217, 250);}
        .mainImg > .item:nth-child(5n + 0) { background-color: rgb(255, 196, 255);}

        .arrow {
            width: 1200px;
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            font-size: 60px;
            color: rgba(165, 164, 164, 0.4);
        }
        .arrow > div:hover {
            color: grey;
            cursor: pointer;
            transition-duration: 0.3s;
        }
        .main2 {
            margin: 50px auto;
        }
        .main2 > div {
            position: relative;
            width: 550px;
            height: 350px;
            border: 2px dashed red;
        }
        .product-top10 > div:first-child,
        .storeLikeTop > div:first-child {
            position: absolute;
            width: 100%;
            top: 10px;
            text-align: center;
            font-size: 40px;
            font-weight: bold;
        }

        .main3 > div {
            width: 550px;
            height: 500px;
            border: 2px dashed red;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .event-sub { position: relative; }
        .event-sub > div {
            height: 150px;
            width: 550px;
        }
        .event-sub > div:hover {
            cursor: pointer;
        }
        .event-sub > div:nth-child(1) {background-color: rgb(236, 255, 184);}
        .event-sub > div:nth-child(2) {background-color: rgb(250, 121, 121);}
        .event-sub > div:nth-child(3) {background-color: rgb(143, 223, 226);}
        
    </style>
</head>
<body>

    <main class="frame">

        <div class="main1">
            <div class="mainImg">
                <div class="item">3</div>
                <div class="item">4</div>
                <div class="item">5</div>
                <div class="item">1</div>
                <div class="item">2</div>
            </div>
            <div class="arrow flex sb">
                <div direction="-1">&nbsp; &#10096;</div>
                <div direction="1">&#10097; &nbsp;</div>
            </div>
        </div>
        
        <div class="main2 flex sb">
            <div class="product-top10">
                <div class="product-top10-text">오늘의 상품</div>
                <div class="product-top10-img"></div>
            </div>
            <div class="storeLikeTop">
                <div class="storeLikeTop-text">오늘의 가맹점</div>
            </div>
        </div>
        <div class="main3 flex sb">
            <div class="event-main">
                <div class="event-main-img">
                    <img src="resources/image/home/기본1.png" width="550px" height="500px">
                </div>
                <div class="event-main-img hidden">
                    <img src="resources/image/home/기본2.png" width="550px" height="500px">
                </div>
                <div class="event-main-img hidden">
                    <img src="resources/image/home/기본3.png" width="550px" height="500px">
                </div>
            </div>
            <div class="event-sub">
                <div class="event-sub-img">클릭! 이벤트1</div>
                <div class="event-sub-img">클릭! 이벤트2</div>
                <div class="event-sub-img">클릭! 이벤트3</div>
            </div>
        </div>
    </main>

    <script>
 	// main1

    const MainImgArr = Array.from(document.querySelectorAll('.mainImg > .item'))
    let isAnimating = false
    
    function mainSlider(event) {
        if(isAnimating) return
        
        isAnimating = true
        const unit = 1200
        const mid = Math.trunc(MainImgArr.length / 2)
        const direction = +event.target.getAttribute('direction')

        if (direction == 1) MainImgArr.push(MainImgArr.shift())
        else MainImgArr.splice(0, 0, MainImgArr.pop())

        MainImgArr.forEach((e, index) => {
            e.style.opacity = 0.2
            e.style.left = -(unit * (mid-index)) + 'px'
        })

        MainImgArr[mid].style.opacity = 1
        MainImgArr[0].style.opacity = 0
        MainImgArr[MainImgArr.length-1].style.opacity = 0

        setTimeout(() => {
            isAnimating = false
        }, 500)
        
    }


    document.querySelectorAll('.arrow > div').forEach(e => e.onclick = mainSlider)
	for(let i = 0; i < 5; i++) {
	    document.querySelector('.arrow > div:last-child').dispatchEvent(new Event('click'))
	}
    setInterval(() =>
        document.querySelector('.arrow > div:last-child').dispatchEvent(new Event('click'))
    , 4000)


        // main3
    const subArr = Array.from(document.querySelectorAll('.event-sub > div'))

    function mainEventClick(event) {
        const mainArr = Array.from(document.querySelectorAll('.event-main > div'))
        const index = subArr.findIndex(e => e == event.target)

        mainArr.forEach((e, idx) => {
            if(idx === index) {
                e.classList.remove('hidden');
            } else {
                e.classList.add('hidden')
            }
        })
    }
    
    document.querySelectorAll('.event-sub > div').forEach(e => e.onclick = mainEventClick)

        


    </script>

</body>
</html>