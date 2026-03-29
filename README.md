# 2026 신입 Back-End 개발자 코딩 과제 - 간단한 CMS REST API

본 프로젝트는 (주)맑은기술 2026년도 신입 Back-End 개발자 채용을 위한 코딩 과제 결과물입니다.
Spring Boot를 기반으로 콘텐츠 관리(CMS) 기능을 수행하는 REST API를 구현하였습니다.

## 프로젝트 개요

- **목표**: 간단한 CMS 콘텐츠 관리 API 구현 (CRUD, 페이징, 권한 제어)
- **핵심 기능**:
  - 콘텐츠 추가, 목록 조회(페이징), 상세 조회(조회수 증가), 수정, 삭제
  - Spring Security를 이용한 인증 및 인가 (Role 기반 접근 제어)
  - 작성자 본인 또는 관리자(ADMIN)만 수정/삭제 가능한 권한 로직

## 기술 스택 (Spec)

- **언어**: Java 25
- **프레임워크**: Spring Boot 4.0.3
- **보안**: Spring Security (Form Login, HTTP Basic Auth)
- **데이터베이스**: H2 Database (In-memory)
- **ORM**: Spring Data JPA
- **문서화**: SpringDoc OpenAPI (Swagger UI)
- **기타**: Lombok, Gradle

## 실행 방법

### 1. 로컬 환경 실행
```bash
./gradlew bootRun
```
- API 접속 주소: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:test)

### 2. Docker를 이용한 실행
```bash
docker compose up --build
```
- Docker 환경에서도 동일하게 8080 포트로 접속 가능합니다.

## 사용자 계정 및 권한

시스템 시작 시 Application.java의 CommandLineRunner를 통해 다음 계정들이 자동으로 생성됩니다.

| Username | Password | Role | 설명 |
| :--- | :--- | :--- | :--- |
| **admin** | password | **ADMIN** | 모든 콘텐츠 수정/삭제 가능 |
| **user1** | password | **USER** | 본인 작성 콘텐츠만 수정/삭제 가능 |
| **user2** | password | **USER** | 본인 작성 콘텐츠만 수정/삭제 가능 |

## 프로젝트 문서 (docs/)

상세한 설계 및 명세는 docs 폴더 내 파일들을 참고해 주세요.
- **[ERD]**: docs/erd.puml (Member와 Contents 관계도)
- **[Sequence Diagram]**: docs/permission-sequence.puml (수정/삭제 시 권한 체크 로직)
- **[API 명세서]**: docs/api-spec.md (상세 요청/응답 형식)
- **[Swagger UI]**: 서버 실행 후 http://localhost:8080/swagger-ui/index.html 접속

## 추가 구현 내용 및 특징

1.  **입력 데이터 검증**: Bean Validation(@Valid, @NotBlank 등)을 적용하여 안정적인 데이터 처리를 보장하며, 한글로 된 명확한 에러 메시지를 반환합니다.
2.  **전역 예외 처리**: @RestControllerAdvice를 통해 404, 403, 400 등의 에러 상황을 일관된 JSON 형식으로 처리합니다.
3.  **JPA Auditing**: BaseTimeEntity를 통해 생성/수정 일시와 작성자를 자동으로 기록합니다.
4.  **테스트 코드**: 권한 체크 로직(작성자 여부에 따른 403 에러 등)을 포함한 핵심 기능 테스트를 구현하였습니다.

## 사용 도구 및 참고 자료

- **AI 도구**: Google Gemini CLI를 활용하여 프로젝트 아키텍처 설계, 도메인 모델링, 반복적인 보일러플레이트 코드 생성 및 문서화 작업을 효율적으로 진행하였습니다.
- **참고 자료**: Spring Boot Reference, Spring Security Architecture, SpringDoc OpenAPI Documentation.


