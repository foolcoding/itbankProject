<hr>

# 프로젝트 소개
**네이버 api를 활용한 로그인, 토스 api 를 활용한 상품 결제, 채팅문의, 카카오맵 기반 서비스   
매장관리, 상품픽업, 나만의 냉장고 등의 기능을 구현한 편의점 사이트 입니다.**


# 프로젝트 선정이유
**모두에게 친숙한 소재를 다루고 싶었으며, '우리동네편의점' 어플을 참고하여 프로젝트를 진행하였습니다.**

<hr>


### 개발 인원 
최현웅, 오예랑, 김유빈, 황민정, 배진호 (5인)



### 개발기간
24.03.07 ~ 24.04.04 (약 1달)



### 개발환경
**BackEnd** : Java, JSP, Spring Framework  
**DataBase** : Oracle DB  
**FrontEnd** : JavaScript, HTML, CSS, 
**Server** : Tomcat
**Collaboration** : Git  

**Tools** : sts / SQL developer / VScode


### 의존성
HikariCP, mybatis, commons-fileupload,   
jackson-databind, spring-websocket,   
spring-integration-stomp, jakarta.mail, scribejava  


### 사용한 API
naver login API  
Kakao map API
toss payments API

<br>

### 요구사항 명세서

![image](https://github.com/foolcoding/itbankProject/assets/141770025/f8fc03e8-83dd-4b08-887d-d46bd540921e)


<br>

### ERD

![ERD 수정](https://github.com/foolcoding/itbankProject/assets/141770025/6c132d98-48c3-41bb-991b-83565271f377)





<hr>

## 멤버별 역할

### 오예랑
- 회원 CRUD
- 마이페이지
- 로그인 api 적용
- python 데이터 크롤링


### 로그인 & 회원가입 & 마이페이지
- 아이디, 이메일 중복 불가능  
- Hash 를 이용한 비밀번호 처리  
- Ajax 를 이용한 이메일 인증  
- 일반회원과 매장점주 회원은 다르게 회원가입됨  
- 네이버 로그인 api를 이용하여 상황에 맞게 데이터 처리  
- (DB와 데이터를 비교하여, 간편 회원가입 / 로그인 / 계정연동을 진행함) 


### 데이터 크롤링
- beautiful soup 와 selinium 을 이용하여 편의점 사이트에 있는 상품 데이터를 추출


<hr>

### 황민정
- 상품 CRUD
- 매장 페이지
- js를 이용한 전체적인 frontend 영역
- 필터기능 / 즐겨찾기 기능 / 리뷰 기능


### 상품/매장
- 상품 및 매장 목록 출력하기
- 상품 찜 추가 및 삭제
- 매장 즐겨찾기 추가 및 삭제
- 별점을 이용한 리뷰 추가 및 삭제
- 상품 장바구니에 담기


<hr>

### 최현웅
- 이벤트 관리
- 쿠폰 관리
- 토스 api 적용
- 쿠폰이나 행사 상품에 따른 결제 처리

  
### 결제 및 이벤트
- 이벤트 게시판 관리
- toss payments api를 이용한 결제
- 행사 상품 금액 처리
- 쿠폰 발급 및 적용 가능
- 나만의 냉장고에 저장


<hr>

### 김유빈
- 1:1 문의 채팅
- 채팅방 관리
- FAQ게시판 관리

  
### 1:1 문의
- 웹소켓 / STOMP프로토콜을 활용한 고객과 관리자간의 실시간 1:1채팅
- Ajax를 이용하여 데이터 실시간 반영
- DataBase에 채팅내역을 저장하고,
저장되어 있는 채팅 데이터 불러오기 가능


<hr>

### 배진호
- 카카오 지도 api 적용
- 위치기반 매장 추천
- 사용자 위치 지정
- 관리자 페이지

### 5. 카카오맵 기반 서비스
- 회원의 현재 위치 지정 기능
- 회원과 가까운 거리의 매장부터 출력
- 선택한 매장의 위치
- 재고가 있는 매장의 위치



### 회원 권한에 따른 기능

**[ 일반 회원 모드 ]** 
- 로그인  
- 회원가입  
- 리뷰작성  
- 매장 즐겨찾기, 상품 찜  
- 매장 및 상품 목록 보기  
- 1:1 문의  
- 결제   
- 내 위치설정   


**[ 매장 점주 모드 ]**
- 로그인
- 매장 재고관리
- 회원가입


**[ 웹마스터 모드 ]**
- 고객센터 관리
- 전체 상품관리
- 점주 가입신청 관리

