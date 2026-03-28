# 2026 신입 Back-End 개발자 코딩 과제 - 간단한 CMS REST API

2026년도 신입 Back-End 개발자 코딩 과제입니다.
간단한 CMS(Contents Management System) REST API 를 구현하는 것이 목표입니다.

외부 자료 검색 및 AI 도구 사용을 허용합니다. 다만, 제출물에 활용한 도구와 방식을 간단하게 명시해주시기 바랍니다.

## Spec

- Java 25
- Spring Boot 4
- Spring Security
- JPA
- H2 (db)
- Lombok (필요시)

## 과제 목표

- 간단한 CMS 콘텐츠 관리 API 를 구현 해주세요.
- DB Schema 모두 구현해주세요.
- DB 는 h2 를 사용해주세요.
- 가능한 예외처리도 구현해주세요.
- 필요하다고 생각되는 부분은 추가로 구현해도 됩니다.

## 데이터 모델

### Contents

| 컬럼명                | 이름  | 설명          | 데이터 타입                      | 비고 |
|--------------------|-----|-------------|-----------------------------|----|
| id                 | 아이디 | 고유 아이디      | bigint primary key not null |    |
| title              | 제목  | contents 제목 | varchar(100) not null       |    |
| description        | 내용  | contents 내용 | text                        |    |
| view_count         | 조회수 | 조회수         | bigint not null             |    |
| created_date       | 생성일 | 생성한 날짜      | timestamp                   |    |
| created_by         | 생성자 | 생성한 사용자     | varchar(50) not null        |    |
| last_modified_date | 수정일 | 마지막 수정일     | timestamp                   |    |
| last_modified_by   | 수정자 | 마지막 수정한 사용자 | varchar(50)                 |    |

## 구현 기능

### 콘텐츠 관련 CRUD

시스템에 등록된 콘텐츠에 대한 CRUD 를 필수로 구현해주세요.

#### 기능
- 콘텐츠 추가
- 콘텐츠 목록 조회
  - 반드시 페이징 처리를 해주세요.
- 콘텐츠 상세 조회
- 콘텐츠 수정
- 콘텐츠 삭제


### 로그인
- Spring Security 를 이용해서 로그인을 필수로 구현해주세요.
- 로그인 방식은 자유롭게 선택하여 구현하되, `README.md` 에 명시해주세요
- Role
    - 관리자(ADMIN)
    - 사용자(USER)

### 접근 권한

- 접근 권한을 필수로 구현해주세요.
- 콘텐츠 생성자 본인만 수정 + 삭제 가능하게 구현해주세요.
- 단, 관리자(ADMIN) 인 경우 모든 콘텐츠에 대해 수정 + 삭제할 수 있게 구현해주세요.

## 구현 상세 내용

### 로그인 및 보안
- **로그인 방식**: Spring Security 기반의 **Form Login** 및 **HTTP Basic Authentication**을 구현하였습니다.
- **비밀번호 암호화**: `BCryptPasswordEncoder`를 사용하여 안전하게 암호화하여 저장합니다.
- **사용자 정보**: H2 Database의 `members` 테이블에서 사용자 정보를 관리하며, `UserDetailsService`를 커스터마이징하여 연동하였습니다.
- **초기 데이터**: `h2-data.sql`을 통해 테스트용 계정을 생성하였습니다.
  - 관리자: `admin` / `password`
  - 사용자1: `user1` / `password`
  - 사용자2: `user2` / `password`

### 콘텐츠 관리 (CRUD)
- **JPA Auditing**: `created_date`, `created_by`, `last_modified_date`, `last_modified_by` 컬럼은 JPA Auditing 기능을 통해 자동으로 관리됩니다.
- **조회수**: 콘텐츠 상세 조회(`GET /api/contents/{id}`) 시 조회수가 1씩 증가합니다.
- **페이징**: 목록 조회 시 Spring Data JPA의 `Pageable`을 사용하여 페이징 처리를 구현하였습니다.

### 접근 권한 (RBAC)
- **수정/삭제 권한**: 콘텐츠의 `created_by`와 현재 로그인한 사용자의 `username`을 비교하여 본인이 작성한 콘텐츠만 수정 및 삭제가 가능하도록 구현하였습니다.
- **관리자 권한**: `ADMIN` 역할을 가진 사용자는 본인이 작성하지 않은 콘텐츠에 대해서도 수정 및 삭제가 가능합니다.

### 예외 처리
- `@RestControllerAdvice`를 사용하여 전역 예외 처리(`GlobalExceptionHandler`)를 구현하였습니다.
- 존재하지 않는 콘텐츠 접근(404), 권한 없음(403) 등에 대해 적절한 응답을 반환합니다.

### 사용된 도구 및 참고 자료
- **AI 도구**: Google Gemini CLI를 활용하여 프로젝트 구조 설계 및 코드 구현을 진행하였습니다.
- **참고 자료**: Spring Boot Reference Documentation, Spring Security Reference.

## REST API Docs

### 1. 콘텐츠 목록 조회 (페이징)
- **Method**: `GET`
- **URL**: `/api/contents`
- **Params**: `page` (default 0), `size` (default 10)
- **Auth**: USER, ADMIN

### 2. 콘텐츠 상세 조회
- **Method**: `GET`
- **URL**: `/api/contents/{id}`
- **Auth**: USER, ADMIN

### 3. 콘텐츠 추가
- **Method**: `POST`
- **URL**: `/api/contents`
- **Body**: 
  ```json
  {
    "title": "제목",
    "description": "내용"
  }
  ```
- **Auth**: USER, ADMIN

### 4. 콘텐츠 수정
- **Method**: `PUT`
- **URL**: `/api/contents/{id}`
- **Body**: 
  ```json
  {
    "title": "수정할 제목",
    "description": "수정할 내용"
  }
  ```
- **Auth**: 작성자 본인 또는 ADMIN

### 5. 콘텐츠 삭제
- **Method**: `DELETE`
- **URL**: `/api/contents/{id}`
- **Auth**: 작성자 본인 또는 ADMIN
