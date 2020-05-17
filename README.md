# 1. Blue Jeju Project
> 백기선의 `스프링과 JPA 기반 웹 애플리케이션 개발`을 수강과 동시에 개발을 진행해갈 예정

- 목표
    - 실제 사용될 수 있을 정도의 웹사이트를 개발 및 배포한다.
        - Spring
        - PostGreSQL
        - 
    - CI/CD 환경
    - AWS 자동배포
    - Docker / 쿠버네티스 환경 설정
    - Spring Cloud
        1. Spring Cloud Netflix
        2. Spring Cloud Kubernetes
        
- Git branch
    - lecture: Lecture 강의 들으며 뼈대 생성
    - dev: 실제 Blue-Jeju 프로젝트(최종적으로 여기로 병합)

- layout 관리
  - Repository 또한 `Domain` level로 취급한다.
  - Repository를 controller에 사용하겠다.
  - Account와 같은 도메인과 다른 package이지만, 같은 level로 취급하겠다는 뜻
  - **대신 Service나 Controller를 `Domain`과 `Repository` Entity에서 참조하지 않겠다.**

<!-- TOC -->

- [1. Blue Jeju Project](#1-blue-jeju-project)
  - [1.1. 계정 관리](#11-%ea%b3%84%ec%a0%95-%ea%b4%80%eb%a6%ac)
    - [1.1.1. 기능](#111-%ea%b8%b0%eb%8a%a5)
    - [1.1.2. Account 도메인 클래스](#112-account-%eb%8f%84%eb%a9%94%ec%9d%b8-%ed%81%b4%eb%9e%98%ec%8a%a4)
    - [1.1.3. 회원 가입: Controller](#113-%ed%9a%8c%ec%9b%90-%ea%b0%80%ec%9e%85-controller)
    - [1.1.4. 회원 가입: View](#114-%ed%9a%8c%ec%9b%90-%ea%b0%80%ec%9e%85-view)
    - [1.1.5. 회원 가입: Form 검증](#115-%ed%9a%8c%ec%9b%90-%ea%b0%80%ec%9e%85-form-%ea%b2%80%ec%a6%9d)
    - [1.1.6. 회원 가입: Form-Submit 처리](#116-%ed%9a%8c%ec%9b%90-%ea%b0%80%ec%9e%85-form-submit-%ec%b2%98%eb%a6%ac)
    - [1.1.7. 회원 가입: 리팩토링 및 테스트](#117-%ed%9a%8c%ec%9b%90-%ea%b0%80%ec%9e%85-%eb%a6%ac%ed%8c%a9%ed%86%a0%eb%a7%81-%eb%b0%8f-%ed%85%8c%ec%8a%a4%ed%8a%b8)
    - [1.1.8. 회원 가입: 패스워드 인코더](#118-%ed%9a%8c%ec%9b%90-%ea%b0%80%ec%9e%85-%ed%8c%a8%ec%8a%a4%ec%9b%8c%eb%93%9c-%ec%9d%b8%ec%bd%94%eb%8d%94)

<!-- /TOC -->

## 1.1. 계정 관리

### 1.1.1. 기능
- 회원 가입
- 이메일 인증
- 로그인
- 로그아웃
- 프로필 추가 정보 입력
- 프로필 이미지 등록
- 알림 설정
- 패스워드 수정
- 패스워드를 잊어버렸습니다
- 관심 주제(태그) 등록
- 주요 활동 지역 등록


### 1.1.2. Account 도메인 클래스
- Account 도메인에 필요한 데이터
  - 로그인
  - 프로필
  - 알림 설정

### 1.1.3. 회원 가입: Controller
- 회원 가입 폼에서 입력 받을 수 있는 정보를 "닉네임", "이메일", "패스워드" `폼 객체`로 제공한다.


### 1.1.4. 회원 가입: View

- 부트스트랩
  - 네비게이션 바 만들기
  - 폼 만들기
- 타임리프
  - SignUpForm 타입 객체를 폼 객체로 설정하기
- 웹(HTML, CSS, JavaScript)
  - 제약 검증 기능 사용하기
  - 닉네임 (3~20자, 필수 입력)
  - 이메일 (이메일 형식, 필수 입력)
  - 패스워드 (8~50자, 필수 입력)

### 1.1.5. 회원 가입: Form 검증
- 회원 가입 폼 검증
  - JSR 303 애노테이션 검증
    - 값의 길이, 필수값
  - 커스텀 검증
    - 중복 이메일, 닉네임 여부 확인
  - 폼 에러 있을 시, 폼 다시 보여주기.


### 1.1.6. 회원 가입: Form-Submit 처리
- 회원 가입 처리
  - 회원 정보 저장
  - 인증 이메일 발송
  - 처리 후 첫 페이지로 리다이렉트 (`Post-Redirect-Get 패턴`)

### 1.1.7. 회원 가입: 리팩토링 및 테스트

- 테스트
  - 잘못된 값
    -  다시 폼이 보여지는가?
  - 정상적인 값
    - 이미 가입한 데이터와 중복 여부
    - 이메일 발송 여부

### 1.1.8. 회원 가입: 패스워드 인코더

- Account 엔티티 저장 시 패스워드 인코딩

- 스프링 시큐리티 권장 `PasswordEncoder`
  - `PasswordEncoderFactories.createDelegatingPasswordEncoder()`
  - 여러 해시 알고리듬을 지원하는 패스워드 인코더
  - 기본 알고리듬 `bcrypt`

- 해싱 알고리즘(`bcrypt`)과 솔트(`salt`)
  - 기본적으로 spring은 bcrypt에 salt를 추가한다.
  - salt값은 인스턴스에 따라, 여러가지 요소들에 따라서 계속 바뀌며, 이를 기반으로 해커가 hash 알고리즘 규칙을 파악하기 어렵도록 만든다.
