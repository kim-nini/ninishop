## 🖥️프로젝트 소개
Ninishop은 마이크로서비스 아키텍처(MSA)를 기반으로 한 **이커머스 플랫폼**입니다. <br>
각 서비스가 독립적으로 배포 및 확장 가능하여, 유연하고 효율적인 운영이 가능합니다.

## ✨주요기능
### User service
- 이메일 인증을 통한 회원가입 (JavaMailSender, Rediss)
- 개인정보 암호화
- jwt 토큰을 활용한 로그인
- 마이페이지를 통한 사용자 정보 업데이트

### Product service
- 상품 리스트 조회
- 상품 상세정보 조회
- 재고 관리

### Order service
- 장바구니 CRUD 및 주문
- 주문 상품에 대한 상태 조회 (주문 후 D+1에 배송중, D+2일에 배송완료)
- 주문 상품 취소
  - 주문 상태가 배송중이 되기 이전까지만 취소 가능
  - 취소 후 상품 재고 복구
  - 취소 후 상태는 취소완료로 변경
- 상품에 대한 반품
  - 배송 완료 후 D+1 까지만 반품 가능
  - 반품 신청 후 D+1에 재고 반영
  - 재고 반영 이후 상태는 반품완료로 변경


## 🚀설치 및 실행 방법
### (1) 사전 요구 사항
- Docker
- Docker Compose
- Postman(선택)

### (2) 시작하기
1. GitHub 저장소를 클론합니다.
   ```sh
   git clone https://github.com/kim-nini/ninishop.git
   cd ninishop
   ```
2. Docker Compose를 사용하여 모든 서비스를 빌드하고 실행합니다.
   ```sh
   docker-compose up --build
   ```
3. 모든 서비스가 정상적으로 시작되면, cURL 또는 Postman을 사용하여 API를 테스트할 수 있습니다.
  
## 📜API 명세서
 👉🏻 [ninishop API 명세서 바로가기](https://documenter.getpostman.com/view/34469315/2sA3QwcAMo "ninishop API 명세서")

## 📐아키텍처
![아키텍처-001 (1)](https://github.com/kim-nini/ninishop/assets/144877020/35075726-eecc-404f-be1f-f211d6270e24)
### (1) 기술 스택
- Backend: Java21,Spring Boot 3.2.5,  Spring Data JPA
- Database: MySQL8.0, Redis
- Microservices Architecture: Eureka, Gateway (Spring Cloud 2023.0.0)
- Docker, Apache JMeter
### (2) 서비스 구성
1. Eureka (Service Discovery) : 서비스 디스커버리 및 동적 포트 할당
2. Spring Cloud Gateway (API Gateway) : 유동적인 라우팅 및 부하 분산
3. MySQL: 주요 데이터 저장을 위한 데이터베이스
4. Redis: 인메모리 기반 데이터 스토리지, redisson
5. Docker: 개별 컨테이너로 분리하여 간편한 설치 및 일관된 실행 환경 제공

## ✏️ERD
![image](https://github.com/kim-nini/ninishop/assets/144877020/a77cacbf-0567-4bd9-9897-8930ae304b9b)


## 💡성능 최적화 및 트러블슈팅
### 성능 최적화
[Redisson 분산락을 통한 동시성 성능 최적화](https://velog.io/@duddjektjtro/ninishop-성능-최적화-Redisson-분산락을-통한-동시성-성능-최적화)
### 트러블 슈팅
[OpenFeign 타입오류](https://velog.io/@duddjektjtro/ninishop-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-OpenFeign-%ED%83%80%EC%9E%85%EC%98%A4%EB%A5%98)
