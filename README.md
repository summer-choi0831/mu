# 포인트 API 


- H2 DB 사용
- API에서 회원정보는 회원에게 부여된 번호 (long, 중복되지 않음) 이외의 다른 정보는 전달받지 않음
- 각 API 에서 회원번호 이외에 request, response는 자유롭게 구성
- 회원별 적립금 합계는 마이너스가 될수 없슴


## Build
- install Azul Zulu OpenJDK 15 
  - see https://foojay.io/today/azul-zulu-openjdk-15-on-raspberry-pi/
- checkout git project
  - git clone https://github.com/summer-choi0831/mu.git
- build project
  - ./gradlew build
- start spring boot
  - java -jar build/libs/mu-0.0.1-SNAPSHOT.jar

## API
### 회원별 포인트 합계 조회
```
GET http://localhost:8080/point?memberNo=1001

// res
{
  "success": true,
  "result": {
    "points": [
      {
        "id": 1,
        "memberNo": 1001,
        "amount": 300.00,
        "expirationDateTime": "2023-06-14T20:42:00.087724",
        "accDateTime": "2022-06-14T20:42:00.148",
        "lastUpdateDateTime": "2022-06-14T20:42:00.148"
      },
      {
        "id": 2,
        "memberNo": 1001,
        "amount": 300.00,
        "expirationDateTime": "2023-06-14T20:42:02.996589",
        "accDateTime": "2022-06-14T20:42:02.997",
        "lastUpdateDateTime": "2022-06-14T20:42:02.997"
      }
    ],
    "totalAmount": 600.00
  },
  "httpStatus": "OK"
}

```
### 회원별 포인트 적립/사용 내역 조회 (페이징 처리 필수, 사용취소된 내역은 조회되지 않음)

```
GET http://localhost:8080/point/history?memberNo=1001&size=10

{
  "success": true,
  "result": {
    "list": [
      {
        "id": 1,
        "memberNo": 1001,
        "amount": 300.00,
        "pointAccType": "ACC",
        "createDate": "2022-06-14T20:42:00.287",
        "modifiedDate": "2022-06-14T20:42:00.287"
      } ... // 중략
      {
        "id": 7,
        "memberNo": 1001,
        "amount": 10.00,
        "pointAccType": "USE",
        "createDate": "2022-06-14T20:44:10.368",
        "modifiedDate": "2022-06-14T20:44:10.368"
      }
    ],
    "page": {
      "number": 0,
      "pageSize": 10,
      "totalCount": 7
    }
  },
  "httpStatus": "OK"
}

```

### 회원별 포인트 적립
```
POST http://localhost:8080/point
Content-Type: application/json

{
  "memberNo": 1001,
  "amount": 300,
  "pointAccType": "ACC"
}

// res
{
  "success": true,
  "result": null,
  "httpStatus": "NO_CONTENT"
}
```
### 회원별 포인트 사용
```
POST http://localhost:8080/point
Content-Type: application/json

{
  "memberNo": 1001,
  "amount": 10,
  "pointAccType": "USE"
}

// res
{
  "success": true,
  "result": null,
  "httpStatus": "NO_CONTENT"
}

```

## 다음의 경우 가산점이 부과됩니다.

- 적립된 포인트의 사용기간 구현 (1년)
  - expirationDateTime 구현
- 회원별 포인트 사용취소 API 개발 (포인트 사용 api 호출하는 쪽에서 rollback 처리를 위한 용도)
```
GET http://localhost:8080/rollback?pointHistId=2

// res

{
  "success": true,
  "result": null,
  "httpStatus": "NO_CONTENT"
}
```

- 포인트 사용시 우선순위는 먼저 적립된 순서로 사용
  - expirationDateTime 이 적은 순으로 사용하도록 구현
- Unit test 및 Integration test 작성
  - com.example.mu.aggregate.PointAggregateTest
  

