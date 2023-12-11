# ☁️GOORM_FRIENDS☁️ - 코딩테스트를 위한 WEB IDE 만들기

### 🕑프로젝트 기간

2023.12.01 ~ 2023.12.28

### 💡등장 배경

프로그래머스, 백준 같은 서비스를 이용하며 뭔가 허전한 점, 이런 점이 아쉽다는 생각 해보신 적 없으신가요?

프로그래머스처럼 문제와 풀이를 동시에, 백준처럼 나만의 문제집을 만들어, 코딩테스트 풀이 및 제작하였습니다.

### 코딩테스트 실력과 스터디 진행, 두 마리의 토끼를 모두 잡을 수 있도록 구름프렌즈가 도와드리겠습니다!

### 🤔개요

저희 서비스는 ~~~ 입니다

### 🛠주요 기능

### 채팅 + 코드 댓글

-

### 문제집

-

### 기타

-

### ⚙개발 환경 및 기술 스택

**OS**

- Window 10

**Backend - spring**

- JDK: 17
- Spring Boot: 3.2.0
- Gradle
- Spring Security
- Spring Data JPA
- Spring Boot Starter Oauth2 Client
- Spring Boot Starter Validation
- Json Web Token(jwt): 0.11.2
- Spring Boot Starter Data Redis
- Lombok
- Spring Boot Devtools
- Spring Boot Starter Test
- Spring Security Test
- Spring Data Redis
- Jedis (Redis client library)
- Spring Boot Starter AOP

**Frontend**

- React

**CI/CD**

- Docker
- Nginx
- github actions

**IDE**

- IntelliJ
- VS Code

**Database**

- DBMS: MySQL
- Cache: Redis

**Server**

- AWS EC2
- Ubuntu 20.04 LTS (GNU/Linux 5.4.0-1018-aws x86_64) 2
- AWS S3

### 🎨시스템 아키텍처 (참고사항)

<img src="./img/sysemarchitecture.png" width="700">

### 🔀 ERD

### 💾프로젝트 파일 구조

- Backend

```
back
└─ src
   └─ main
      ├─ java
      │  └─ com
      │     └─ project
      │        └─ helloworld
      │           ├─ aoplog
      │           ├─ config
      │           ├─ controller
      │           ├─ domain
      │           ├─ dto
      │           │  ├─ request
      │           │  └─ response
      │           ├─ elkStack
      │           │  └─ domain
      │           ├─ exception
      │           ├─ interceptor
      │           ├─ repository
      │           ├─ schedule
      │           ├─ security
      │           │  ├─ jwt
      │           │  └─ oauth2
      │           └─ service
      └─ resources
```

- Frontend

```bash
FE
├─ src
│  ├─ api
│  ├─ assets
│  │  ├─ chatbot
│  │  ├─ icon
│  │  ├─ image
│  │  │  ├─ achievement
│  │  │  ├─ emotion
│  │  │  ├─ hair
│  │  │  ├─ pants
│  │  │  ├─ pet
│  │  │  └─ shirt
│  │  └─ minimi_temp
│  ├─ components
│  │  ├─ BasicComp
│  │  ├─ BoardComp
│  │  │  └─ CommentComp
│  │  ├─ FollowComp
│  │  ├─ GuestBookComp
│  │  ├─ MainPageComp
│  │  ├─ NotifyComp
│  │  ├─ StatisticComp
│  │  └─ UserComp
│  ├─ router
│  └─ views
│     ├─ BoardView
│     └─ UserView
│        ├─ findpw
│        ├─ login
│        ├─ modify
└────────└─ register
```

### 👨🏻‍🤝‍👨🏻협업 툴

- Notion
- Git
- Zep
- Slack
- Discord

### 👨🏻‍🤝‍👨🏻협업 환경

- Gitlab
  - 코드의 버전 관리
  - MR시, 팀원이 코드 리뷰를 진행하고 피드백 게시
- Notion
  - 회의가 있을때마다 회의록을 기록하여 보관
  - 기술 확보 시, 다른 팀원들도 추후 따라할 수 있도록 보기 쉽게 작업 순서대로 정리
  - 컨벤션 정리
  - 간트차트 활용한 개발 계획 관리
  - 스토리 보드, 시퀀스 다이어그램, 기능 명세서 등 팀원 모두가 공유해야 하는 문서 관리
- Discord

### 😎팀원 역할

- 이다희
  - 팀장
  - Frontend
- 강수영
  - Frontend
  -
- 김선민
  - Backend
  -
- 변유정
  - Backend
  -
- 임동기
  - Frontend
  -
- 임소라
  - Backend
  -
- 한석규
  - Backend
  -

### 🎞기능 소개 및 화면 (참고 사항)

### 로그인 이전

<img src="./img/before_login.png" width="800">

- 일반 로그인, github로그인, 회원가입 등을 선택할 수 있는 화면

### 회원가입 및 아바타 생성

<img src="./img/sign_up.gif" width="800">

- 회원가입에 필요한 유저 정보 모두 기입 후, 아바타 디자인 가능

### 일반 로그인

<img src="./img/normal_login.gif" width="800">

- 회원가입한 아이디, 비밀번호로 로그인

### 소셜 로그인

<img src="./img/social_login.gif" width="800">

- github 계정으로 로그인

### 회원 정보 수정

<img src="./img/modify_use.gif" width="800">

- 회원가입 시 입력한 정보를 수정(이메일은 수정 불가) 하거나 확인 가능

### 회원 아바타 수정

<img src="./img/modify_avatar.gif" width="800">

- 회원가입 시 생성한 아바타를 수정하거나, github로그인 시 기본 아바타가 제공되는데 이를 수정 가능

### 메인 페이지

<img src="./img/mainPage.gif" width="800">

- 해당 홈페이지 주인의 아바타, 닉네임, 잔디, 일촌평, 미니룸 등을 확인 가능
- 로그인된 사용자의 스토리 확인 가능

### 메인 세부 - 스토리

<img src="./img/story.gif" width="800">

- 로그인된 사용자와 일촌인 다른 사용자가 게시글을 작성하게 되면, 해당 시간으로부터 24시간 동안 게시글이 노출됨

### 메인 세부 - 파도타기

<img src="./img/surffing.gif" width="800">

- 랜덤으로 추천되는 사용자의 페이지로 이동 가능

### 메인 세부 - 잔디

<img src="./img/grass.gif" width="800">

- 글을 많이 작성하면 작성할 수록 잔디의 색깔이 진해짐
- 잔디를 눌러 그 날 작성한 게시글을 확인할 수 있고, 제목 클릭 시 해당 게시글로 이동 가능

### 메인 세부 - 일촌평

<img src="./img/family_comment.gif" width="800">

- 홈페이지 주인과 일촌인 사람만이 일촌평을 작성 가능

### 게시글 조회

<img src="./img/board_read.gif" width="800">

- 카테고리 별 게시글 조회 및 전체 게시글 조회 가능

### 게시글 작성

<img src="./img/boardCreate.gif" width="800">

- 마크다운 형식으로 게시글 작성 가능

### Scrap

<img src="./img/scrap.gif" width="800">

- 모든 유저들이 작성한 게시글 scrap 가능
- scrap한 게시글은 scrap 페이지 에서 조회 및 삭제 가능

### 커뮤니티

<img src="./img/community.gif" width="800">

- 실시간 인기 검색어 10위를 확인 가능
- 검색 이전에는 다른 사용자들이 작성한 모든 글들을 최신순으로 조회 가능
- 검색 이후에는 해당 검색어와 연관 있는 글들을 조회 가능

### 방명록

<img src="./img/guest_book.gif" width="800">

- 누구나 작성 가능 (홈페이지 주인 제외)
- 공개글/비밀글 설정 가능
- 방명록에 대한 댓글은 홈페이지 주인만이 게시 가능

### 알림

<img src="./img/alaram.gif" width="800">

- 일촌 요청이 오거나, 일촌 수락이 되거나, 내 게시물에 누가 댓글을 달거나, 내 방명록에 누가 글을 남기면 알림 도착
- 댓글이나 방명록 관련 알림의 경우 클릭 시 해당 게시글이나 방명록으로 이동 가능
- 읽기 전과 읽기 후의 색이 다르게 노출
