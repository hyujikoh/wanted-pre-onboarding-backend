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
  > src
    > main
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
    > test.java.com.ohj.wanted_internship_backend : 테스트 패키지!!
      > member 
        MemberApiTest.java : api 단위 테스트
        MemberServiceTest.java : 서비스 로직 테스트
        
      > post
        PostTest.java : post 도메인 테스트 
    
```

## 3. 상세 정보

과제 API 를 구성하고 있는 세부적인 패키지 구조는 아래와 같다.
- domain name(ex. post, member)
  - controller : 해당 도메인의 api 진입점 역할을 하는 controller를 작성 함
  - service : 해당 도메인의 비즈니스 로직에 대한 규격을 작성한 인터페이스
  - serviceImpl : 해당 도메인의 상세한 비즈니스 로직을 작성한 Class
  - Repository : data 영속성 처리를 위한 인터페이스
  - RepositoryJpa : 인터페이스를 상속 받아서 데이터 처리를 JPA 를 통해 구현한 인터페이스


기술 스택 : Spring boot 2.7.6, JPA, Hibernate, Swagger, JWT, Mysql 8.0, JAVA 11

git Repo : https://github.com/hyujikoh/wanted-pre-onboarding-backend

swagger API : http://localhost:8010/swagger-ui/index.html

데모영상 : https://drive.google.com/file/d/1v2PHlEum8m_LxD-4G-CZuw5pm7bnjCc2/view?usp=drive_link

DB ERD Diagram 

![img.png](img.png)

구현 영상 : (링크 삽입)

## 4 app 실행방법

- 전제 조건
  - db/db.sql 파일에 있는 sql 문 실행하여 테이블 생성
  - shell 스크립트 기반일 경우, java 11 이 있어야한다.
  - IDE(EX. 인텔리제이) 기반 일 경우, java 11 이 적용되어야한다. 

1. shell 스크립트로 실행

```shell
./gradelw clean build 
java -jar build/libs/wanted_internship_bakend-0.0.1-SNAPSHOT.jar
```

2. IDE 환경에서 실행(인텔리제이 기준 실행)

그냥 run 을 돌려서 실행한다. 이때 java 11이 적용이 되어야한다!!!

## 5 구현 방법 및 이유에 대한 간략한 설명

이번 과제에서 작성한 api 요구사항은 각각 member, post 도메인에 각각 1개의 api 를 예시로 설명을 하겠습니다. 

### member 

1. 우선 member 도메인은 controller 에서는 다음과 같이 의존성을 주입하였습니다
```java
public class MemberController {
    private final MemberService memberService;
    
    /*
     * 이하 코드 생략
     * */
}
```
2. controller 에서는 service 인터페이스에 의존적으로 사용 했습니다. 실제 서비스 로직을 주입을 받아도 되지만, 
서비스 로직 이 바뀌면 controller 가 영향을 받기 때문에, service 라는 인터페이스에 의존적이게 소프트웨어 원칙을 적용을 하였습니다.

3. 이번 인증 인가 부분에는 스프링 시큐리티를 적용하지 않고, jwtManager 라는 클래스를 통해 토큰을 발급하는 로직을 작성하였습니다.
실제 해당 로직에서 토큰을 발급 할 경우, 유효기간을 3년으로 지정하였습니다. 

```java
public class JwtManager {

    private static final String JWT_SECRET_KEY = "UwKYibQQgkW7g-*k.ap9kje-wxBHb9wdXoBT4vnt4P3sJWt-Nu";

    /*
    JWT 생성
    @param id
    @return String
     */
    public static String createJwt(Member member) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("id", String.valueOf(member.getId()))
                .claim("username", member.getUserEmail())
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365 * 3))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }
}
```

4. 원래와 같으면 토큰이 탈취되는 경우를 대비하여, accessToken의 유효기간을 짧게 하고, refreshToken 을 통하여 보안 이슈를 해결하는 방법이 있지만, 
해당 과제에서는 오직 accessToken 만 발급하는 로직으로 작성하였습니다.

### **회원 가입 API**

#### **요청**

- Method: POST
- URL: **`member/join`**
- Content-Type: **`application/json`**

#### Request Body

```json
{
    "userEmail": "oh@naver.com",
    "password": "12345678"
}

```

#### **응답**

- Status Code: 200 OK
- Content-Type: **`application/json`**

#### Response Body

```json
{
    "isSuccess": true,
    "code": 1000,
    "message": "요청에 성공하였습니다.",
    "result": {
        "id": 2,
        "userEmail": "oh@naver.com",
        "accessToken": "[token value]"
    }
}

```


### **로그인 API**

#### **요청**

- Method: POST
- URL: **`member/logIn`**
- Content-Type: **`application/json`**

#### Request Body

```json
{
    "userEmail": "oh@naver.com",
    "password": "12345678"
}

```

#### **응답**

- Status Code: 200 OK
- Content-Type: **`application/json`**

#### Response Body

```json
{
  "isSuccess": true,
  "code": 1000,
  "message": "요청에 성공하였습니다.",
  "result": {
    "id": 2,
    "userEmail": "oh@naver.com",
    "accessToken": "[token value]"
  }
}
```

### post
1. post 로직에서는 생성, 수정, 삭제 같은 api 요청시에 header 에 있는 `X-ACCESS-TOKEN` 을 추출 한 다음 파싱 하여 토큰의 유효성을 확인한다.

```java
public class PostServiceImpl implements PostService {

    @Override
    public Post write(PostReq postReq) {
        long id = Long.parseLong(JwtManager.getId());
        Member member = memberRepository.findById(id).get();

        Post post = postBuilder(postReq, member);
        postRepository.save(post);

        return post;
    }
}
```
2. 위와 같은 write 로직에서 `JwtManager.getId()` 를 통해 long 타입의 데이터를 추출을 합니다. 추출하는 로직은 다음과 같습니다. 
만일 유효하지 않은 토큰 값인 경우, enum 으로 처리한 vaildation 값을 통해서 에러메시지를 res 합니다.

```java
public class JwtManager {
    public static String getId() {
        //1. JWT 추출
        String jwt = getJwt();

        // 2. JWT parsing
        Jws<Claims> claims = parseToClaims(jwt);

        // 3. id 추출
        Claims claimsBody = claims.getBody();
        try {
            return claimsBody.get("id", String.class);
        } catch (JwtException e) {
            return String.valueOf(claimsBody.get("id", String.class));
        }
    }


    private static Jws<Claims> parseToClaims(String accessToken) {
        try {
            return Jwts.parser() // Jwt가 유효하지 않다면 parseClaimsJws() 에서 JwtException이 발생한다.
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception e) {
            throw new BaseException(INVALID_JWT);
        }
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public static String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

}

```

### **글 생성 API**

#### **요청**

- Method: POST
- URL: **`/post/write`**
- Headers:
  - **`X-ACCESS-TOKEN`**: 사용자의 액세스 토큰
- Content-Type: **`application/json`**

#### Request Body

```json

{
    "subject": "this is subject",
    "content": "this is content"
}

```

#### **응답**

- Status Code: 200 OK
- Content-Type: **`application/json`**

#### Response Body

```json

{
    "isSuccess": true,
    "code": 1000,
    "message": "요청에 성공하였습니다.",
    "result": {
        "author": {
            "id": 2,
            "userEmail": "oh@naver.com",
            "accessToken": null},
        "id": 1,
        "subject": "this is subject",
        "content": "this is content"
    }
}

```


### **글 수정 API**

#### **요청**

- Method: PUT
- URL: **`/post/write`**
- Headers:
  - **`X-ACCESS-TOKEN`**: 사용자의 액세스 토큰
- Content-Type: **`application/json`**

#### Request Body

```json

{
  "id" : 1,
  "subject" : "fix subject",
  "content" : "fix content"
}

```

#### **응답**

- Status Code: 200 OK
- Content-Type: **`application/json`**

#### Response Body

```json

{
  "isSuccess": true,
  "code": 1000,
  "message": "요청에 성공하였습니다.",
  "result": {
    "author": {
      "id": 2,
      "userEmail": "oh@naver.com",
      "accessToken": null
    },
    "id": 1,
    "subject": "fix subject",
    "content": "fix content"
  }
}

```


### **글 삭제 API**

#### **요청**

- Method: DELETE
- URL: **`/post/write`**
- Headers:
  - **`X-ACCESS-TOKEN`**: 사용자의 액세스 토큰
- Content-Type: **`application/json`**

#### Request Body

```json
{
  "id" : 1
}

```

#### **응답**

- Status Code: 200 OK
- Content-Type: **`application/json`**

#### Response Body

```json

{
  "isSuccess": true,
  "code": 1000,
  "message": "요청에 성공하였습니다."
}

```

### **글 상세조회 API**

#### **요청**

- Method: GET
- URL: **`/post/{postId}`**
- Content-Type: **`application/json`**

#### Request Body

```json

```

#### **응답**

- Status Code: 200 OK
- Content-Type: **`application/json`**

#### Response Body

```json

{
  "isSuccess": true,
  "code": 1000,
  "message": "요청에 성공하였습니다.",
  "result": {
    "author": {
      "id": 2,
      "userEmail": "oh@naver.com",
      "accessToken": null
    },
    "id": 1,
    "subject": "fix subject",
    "content": "fix content"
  }
}

```

### **게시글 페이징 조회  API**

#### **요청**

- Method: GET
- URL: **`/post/list`**
- Content-Type: **`application/json`**
- params 
  - page(int) : 페이지 수
  - size(int) : 페이지당 게시글 
#### Request Body

```json

```

#### **응답**

- Status Code: 200 OK
- Content-Type: **`application/json`**

#### Response Body

```json

{
  "isSuccess": true,
  "code": 1000,
  "message": "요청에 성공하였습니다.",
  "result": {
    "content": [
      {
        "author": {
          "id": 2,
          "userEmail": "oh@naver.com",
          "accessToken": null
        },
        "id": 5,
        "subject": "this is subject45",
        "content": "this is content45"
      },
      {
        "author": {
          "id": 2,
          "userEmail": "oh@naver.com",
          "accessToken": null
        },
        "id": 4,
        "subject": "this is subject32",
        "content": "this is content32"
      },
      {
        "author": {
          "id": 2,
          "userEmail": "oh@naver.com",
          "accessToken": null
        },
        "id": 3,
        "subject": "this is subject2",
        "content": "this is content2"
      },
      {
        "author": {
          "id": 2,
          "userEmail": "oh@naver.com",
          "accessToken": null
        },
        "id": 2,
        "subject": "this is subject1",
        "content": "this is content1"
      },
      {
        "author": {
          "id": 2,
          "userEmail": "oh@naver.com",
          "accessToken": null
        },
        "id": 1,
        "subject": "fix subject",
        "content": "fix content"
      }
    ],
    "pageable": {
      "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
      },
      "offset": 5,
      "pageSize": 5,
      "pageNumber": 1,
      "paged": true,
      "unpaged": false
    },
    "last": true,
    "totalPages": 2,
    "totalElements": 10,
    "size": 5,
    "number": 1,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "first": false,
    "numberOfElements": 5,
    "empty": false
  }
}
```




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
