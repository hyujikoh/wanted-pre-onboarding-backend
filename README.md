# 원티드 프리온보딩 백엔드 인턴십 사전 과제

성명 : 오현직

## 1. 프로젝트 주요 브랜치 
- 운영 브랜치 : master
- 개발 브랜치 : dev

## 2. 패키지 구조
```text
wanted_internship_bakend
  > build
  > gradle
  > db : db 테이블 초기화 sql 정보 가지고 있는 디렉토리
  > src.main
    > generated : QueryDsl 사용을 위한 QClass 들이 build 되는 디렉토리.(현재는 QueryDSL 사용 안함)
    > java
      > com.ohj.wanted_internship_backend
        WantedInternshipBakendApplication
        > app
          > common : 범용적으로 사용되는 컴포넌트가 작성되어있는 패키지
          > restapi
            > member
            > post
          > utils : jwt, 패스워드 생성 컴포넌트가 작성된 패키지
        AtfleeApplication
    > resources
      application-{profile}.yml : 스프링 프로필별 프로젝트 설정 파일
      application.yml :빌드시 꼭 포함돼야하는 설정 파일
```

## 3. 상세 정보

과제 API 를 구성하고 있는 세부적인 패키지 구조는 아래와 같다.
- domainname(ex. post, member)
  - controller : 해당 도메인의 api 진입점 역할을 하는 controller를 작성 함
  - service : 해당 도메인의 비즈니스 로직에 대한 규격을 작성한 인터페이스
  - serviceImpl : 해당 도메인의 상세한 비즈니스 로직을 작성한 Class
  - Repository : data 영속성 처리를 위한 인터페이스
  - RepositoryJpa : 인터페이스를 상속 받아서 데이터 처리를 JPA 를 통해 구현한 인터페이스


기술 스택 : Spring boot 2.7.6, JPA, Hibernate, Swagger, JWT, Mysql 8.0, JAVA 11

git Repo : https://github.com/hyujikoh/wanted-pre-onboarding-backend

swagger API : http://localhost:8010/swagger-ui/index.html

DB ERD Diagram 

![img.png](img.png)

구현 영상 : (링크 삽입)

구현 방식에 대한 설명


<details>
<summary>👻 작업 일자 👻</summary>
<div markdown="1">

8/7
1. 프로젝트 생성
2. 도메인 템플릿 구축
3. 로컬 기준 db 환경 셋팅

8/8
1. member 도메인 회원가입 테스트 기반 기능 구현

8/9
1. 기존에 스프링 시큐리티를 사용한 방식 제거
2. 회원가입 API 기능 구현(테스트 코드 포함)
3. 로그인 API 기능 구현(테스트 코드 포함)

8/10
1. 게시글 생성(테스트 코드 미 포함)
2. 게시글 수정(테스트 코드 미 포함)

8/11
1. 게시글 CRUD 작성 완료(테스트 코드 포함)

8/12
1. 테스트 코드 리팩토링
2. 단위, 통합 테스트 완료

</div>
</details>
