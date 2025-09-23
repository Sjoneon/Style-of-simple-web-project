# SOS (Style of Simple)

**패션을 잘 모르는 20~30대 남자들을 위한 옷 쇼핑몰**

SOS는 패션에 대한 지식이 부족한 20~30대 남성을 대상으로 하는 온라인 쇼핑몰입니다. 키워드 기반 추천 시스템을 통해 사용자의 체형과 스타일 선호도에 맞는 의류를 제안하여, 패션 초보자도 쉽게 스타일링할 수 있도록 지원합니다.

## 기술 스택

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)

### Backend
- **Language**: Java
- **Framework**: Spring Boot 2.7
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven 3.9.9
- **Server**: Apache Tomcat (Embedded)

### Database
- **RDBMS**: MySQL 8.0
- **ORM**: Spring Data JPA

### Frontend
- **Languages**: HTML5, CSS3, JavaScript ES6
- **Styling**: Custom CSS

## 시스템 아키텍처

```
Browser ↔ Spring Boot Web Application ↔ MySQL Database
                       ↓
              Apache Tomcat Server
```

클라이언트-서버 아키텍처를 기반으로 하며, Spring Boot의 내장 Tomcat을 통해 웹 애플리케이션을 서빙합니다. RESTful API 설계 원칙을 따라 HTTP 프로토콜로 통신하며, Thymeleaf를 통해 서버 사이드 렌더링을 수행합니다.

## 핵심 기능

### 판매자 시스템
- **인증 관리**: 사업자 등록번호 기반 로그인/로그아웃
- **상품 관리**: CRUD 기능을 통한 상품 등록, 수정, 삭제
- **재고 관리**: 실시간 재고 현황 및 주문 상태 모니터링
- **고객 지원**: 문의 답변 시스템을 통한 고객 서비스
- **키워드 시스템**: 상품별 추천 키워드 등록 및 관리

### 사용자 시스템
- **회원 관리**: 회원가입, 로그인, 개인정보 관리
- **상품 탐색**: 카테고리별 상품 분류 및 검색 기능
- **주문 시스템**: 장바구니, 주문 처리, 주문 내역 조회
- **문의 시스템**: 상품 및 서비스 관련 문의 등록
- **추천 서비스**: 사용자 입력 키워드 기반 상품 추천

## 데이터베이스 설계

### 핵심 테이블 구조
- **사용자 (User)**: 고객 정보, 인증 데이터, 주소 정보
- **판매자 (Seller)**: 사업자 정보, 사업자 등록번호, 연락처
- **상품 (Product)**: 상품 정보, 가격, 재고, 카테고리 분류
- **주문 (Order)**: 주문 정보, 결제 상태, 배송 상태
- **주문상세 (OrderDetail)**: 주문별 상품 상세 정보, 수량, 단가
- **카테고리 (Category)**: 상품 분류 체계, 계층 구조
- **키워드 (Keyword)**: 추천 시스템용 태그, 카테고리별 분류
- **장바구니 (Cart)**: 임시 주문 정보, 사용자별 상품 저장
- **문의 (Inquiry)**: 고객 문의, 답변 관리

## 프로젝트 구조

```
sosos/
├── .mvn/wrapper/                           # Maven Wrapper 설정
│   └── maven-wrapper.properties
├── .vscode/                                # VS Code 개발 환경 설정
│   ├── launch.json                         # 디버깅 설정
│   └── settings.json                       # 에디터 설정
├── src/
│   ├── main/
│   │   ├── java/com/prosos/sosos/
│   │   │   ├── SososApplication.java       # Spring Boot 애플리케이션 진입점
│   │   │   ├── config/                     # 애플리케이션 설정
│   │   │   │   ├── WebConfig.java          # Spring MVC 설정
│   │   │   │   ├── SecurityConfig.java     # 보안 설정
│   │   │   │   └── DatabaseConfig.java     # 데이터베이스 설정
│   │   │   ├── controller/                 # 웹 컨트롤러 계층
│   │   │   │   ├── HomeController.java     # 메인 페이지 컨트롤러
│   │   │   │   ├── UserController.java     # 사용자 관련 요청 처리
│   │   │   │   ├── SellerController.java   # 판매자 관련 요청 처리
│   │   │   │   ├── ProductController.java  # 상품 관련 요청 처리
│   │   │   │   ├── OrderController.java    # 주문 관련 요청 처리
│   │   │   │   ├── CartController.java     # 장바구니 요청 처리
│   │   │   │   └── InquiryController.java  # 문의 관련 요청 처리
│   │   │   ├── service/                    # 비즈니스 로직 계층
│   │   │   │   ├── UserService.java        # 사용자 비즈니스 로직
│   │   │   │   ├── SellerService.java      # 판매자 비즈니스 로직
│   │   │   │   ├── ProductService.java     # 상품 관리 로직
│   │   │   │   ├── OrderService.java       # 주문 처리 로직
│   │   │   │   ├── CategoryService.java    # 카테고리 관리 로직
│   │   │   │   ├── KeywordService.java     # 키워드 관리 로직
│   │   │   │   └── RecommendService.java   # 추천 알고리즘 로직
│   │   │   ├── repository/                 # 데이터 접근 계층
│   │   │   │   ├── UserRepository.java     # 사용자 데이터 접근
│   │   │   │   ├── SellerRepository.java   # 판매자 데이터 접근
│   │   │   │   ├── ProductRepository.java  # 상품 데이터 접근
│   │   │   │   ├── OrderRepository.java    # 주문 데이터 접근
│   │   │   │   ├── CategoryRepository.java # 카테고리 데이터 접근
│   │   │   │   ├── KeywordRepository.java  # 키워드 데이터 접근
│   │   │   │   └── InquiryRepository.java  # 문의 데이터 접근
│   │   │   ├── model/                      # 도메인 모델 계층
│   │   │   │   ├── User.java               # 사용자 엔티티
│   │   │   │   ├── Seller.java             # 판매자 엔티티
│   │   │   │   ├── Product.java            # 상품 엔티티
│   │   │   │   ├── Order.java              # 주문 엔티티
│   │   │   │   ├── OrderDetail.java        # 주문 상세 엔티티
│   │   │   │   ├── Category.java           # 카테고리 엔티티
│   │   │   │   ├── Cart.java               # 장바구니 엔티티
│   │   │   │   ├── Keyword.java            # 키워드 엔티티
│   │   │   │   ├── ProductKeyword.java     # 상품-키워드 매핑 엔티티
│   │   │   │   ├── Inquiry.java            # 문의 엔티티
│   │   │   │   └── InquiryAnswer.java      # 문의 답변 엔티티
│   │   │   └── dto/                        # 데이터 전송 객체
│   │   │       ├── request/                # 요청 DTO
│   │   │       │   ├── UserSignupRequest.java
│   │   │       │   ├── ProductCreateRequest.java
│   │   │       │   ├── OrderCreateRequest.java
│   │   │       │   └── InquiryCreateRequest.java
│   │   │       └── response/               # 응답 DTO
│   │   │           ├── UserInfoResponse.java
│   │   │           ├── ProductListResponse.java
│   │   │           ├── OrderHistoryResponse.java
│   │   │           └── ApiResponse.java
│   │   └── resources/
│   │       ├── application.properties      # 기본 설정
│   │       ├── application-dev.properties  # 개발 환경 설정
│   │       ├── application-prod.properties # 운영 환경 설정
│   │       ├── static/                     # 정적 리소스
│   │       │   ├── css/
│   │       │   │   ├── main.css           # 공통 스타일
│   │       │   │   ├── product.css        # 상품 관련 스타일
│   │       │   │   ├── user.css           # 사용자 페이지 스타일
│   │       │   │   └── seller.css         # 판매자 페이지 스타일
│   │       │   ├── js/
│   │       │   │   ├── main.js            # 공통 JavaScript
│   │       │   │   ├── cart.js            # 장바구니 기능
│   │       │   │   ├── recommend.js       # 추천 기능
│   │       │   │   └── validation.js      # 폼 검증
│   │       │   └── images/
│   │       │       ├── logo.png           # 브랜드 로고
│   │       │       ├── products/          # 상품 이미지
│   │       │       └── icons/             # UI 아이콘
│   │       ├── templates/                  # Thymeleaf 템플릿
│   │       │   ├── index.html             # 메인 페이지
│   │       │   ├── user/                  # 사용자 페이지
│   │       │   │   ├── login.html
│   │       │   │   ├── signup.html
│   │       │   │   ├── profile.html
│   │       │   │   └── order-history.html
│   │       │   ├── seller/                # 판매자 페이지
│   │       │   │   ├── login.html
│   │       │   │   ├── dashboard.html
│   │       │   │   ├── product-manage.html
│   │       │   │   └── order-manage.html
│   │       │   ├── product/               # 상품 페이지
│   │       │   │   ├── list.html
│   │       │   │   ├── detail.html
│   │       │   │   └── category.html
│   │       │   ├── cart/
│   │       │   │   └── cart.html
│   │       │   ├── inquiry/
│   │       │   │   ├── list.html
│   │       │   │   └── form.html
│   │       │   └── fragments/             # 재사용 가능한 템플릿 조각
│   │       │       ├── header.html
│   │       │       ├── footer.html
│   │       │       ├── navigation.html
│   │       │       └── pagination.html
│   │       └── data.sql                   # 초기 데이터 스크립트
│   └── test/                              # 테스트 코드
│       └── java/com/prosos/sosos/
│           ├── controller/                # 컨트롤러 단위 테스트
│           ├── service/                   # 서비스 단위 테스트
│           ├── repository/                # 리포지토리 테스트
│           └── integration/               # 통합 테스트
├── target/                                # Maven 빌드 출력
├── .gitignore                            # Git 제외 파일 설정
├── pom.xml                               # Maven 프로젝트 설정
├── mvnw                                  # Maven Wrapper (Unix)
├── mvnw.cmd                              # Maven Wrapper (Windows)
└── README.md
```

### 아키텍처 설계 원칙

**계층형 아키텍처 (Layered Architecture)**
Spring Boot의 표준 계층 구조를 따라 각 계층이 명확한 책임을 가지도록 설계했습니다.

- **Presentation Layer (Controller)**: HTTP 요청/응답 처리, 데이터 검증, 사용자 인터페이스
- **Business Layer (Service)**: 비즈니스 로직 구현, 트랜잭션 관리, 도메인 규칙 적용
- **Persistence Layer (Repository)**: 데이터 접근 추상화, JPA를 통한 CRUD 연산
- **Domain Layer (Model)**: 비즈니스 도메인 객체, 엔티티 관계 정의

**의존성 역전 원칙 (Dependency Inversion Principle)**
- 인터페이스 기반 설계로 느슨한 결합 구현
- Spring IoC 컨테이너를 통한 의존성 주입
- 테스트 용이성과 확장성 확보

**단일 책임 원칙 (Single Responsibility Principle)**
- 각 클래스와 패키지가 하나의 변경 이유만 가지도록 설계
- 관심사의 분리를 통한 유지보수성 향상
- 코드 재사용성과 가독성 증대

## 설치 및 실행

### 사전 요구사항
- **JDK**: 11 이상
- **MySQL**: 8.0 이상
- **Maven**: 3.6 이상 (또는 내장 Maven Wrapper 사용)

### 환경 설정

1. **저장소 복제**
   ```bash
   git clone [repository-url]
   cd sosos
   ```

2. **데이터베이스 설정**
   ```sql
   CREATE DATABASE sos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'sos_user'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON sos_db.* TO 'sos_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **애플리케이션 설정**
   ```properties
   # application-dev.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sos_db
   spring.datasource.username=sos_user
   spring.datasource.password=password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

4. **빌드 및 실행**
   ```bash
   # Maven Wrapper 사용 (권장)
   ./mvnw clean install
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   
   # 또는 Maven 직접 사용
   mvn clean install
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

5. **애플리케이션 접속**
   ```
   http://localhost:8080
   ```

## 향후 개발 계획

### 기능 개선
- 추가 기능 구현 필요 (검색, 결제, 반품/교환)
- 추천 알고리즘 고도화 (기계학습 모델 적용)
- OpenAI API 연동을 통한 AI 스타일 컨설팅 서비스

### 기술적 개선
- 코드 리팩토링 및 성능 최적화
- 단위 테스트 및 통합 테스트 커버리지 확대
- CI/CD 파이프라인 구축
- Docker 컨테이너화 및 클라우드 배포

### 사용자 경험 개선
- 반응형 웹 디자인 적용
- Progressive Web App (PWA) 전환
- 실시간 알림 시스템 구축

## 개발자 정보

- **개발자**: 송재원
- **학과**: 컴퓨터공학과
- **소속**: 서원대학교
- **프로젝트 기간**: 2024y_03m~06m, 2024y_09m~11m
