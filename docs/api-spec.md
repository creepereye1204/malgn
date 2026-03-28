# API Specification (간단한 CMS REST API)

이 문서는 시스템의 주요 API 엔드포인트와 요청/응답 형식을 기술합니다.

---

## 공통 사항
- **Base URL:** `http://localhost:8080`
- **Authentication:** Basic Auth (ID: `user1`, PW: `password`)
- **Common Error Response:**
  ```json
  {
    "message": "Error message"
  }
  ```

---

## 1. Contents API

### 1.1 콘텐츠 목록 조회
- **Method:** `GET`
- **Path:** `/api/contents`
- **Query Params:**
  - `page`: 페이지 번호 (0부터 시작, 기본값 0)
  - `size`: 한 페이지 크기 (기본값 10)
  - `sort`: 정렬 기준 (예: `id,desc`)
- **Response (200 OK):** Pageable JSON 객체

### 1.2 콘텐츠 상세 조회
- **Method:** `GET`
- **Path:** `/api/contents/{id}`
- **Response (200 OK):**
  ```json
  {
    "id": 1,
    "title": "제목",
    "description": "내용",
    "viewCount": 5,
    "createdBy": "user1",
    "createdDate": "2026-03-28T12:00:00",
    "lastModifiedBy": "user1",
    "lastModifiedDate": "2026-03-28T12:00:00"
  }
  ```

### 1.3 콘텐츠 추가
- **Method:** `POST`
- **Path:** `/api/contents`
- **Request Body:**
  ```json
  {
    "title": "새 제목",
    "description": "새 내용"
  }
  ```
- **Response (201 Created):** `id` (Long)

### 1.4 콘텐츠 수정
- **Method:** `PUT`
- **Path:** `/api/contents/{id}`
- **Request Body:** 위와 동일
- **Response (200 OK)**

### 1.5 콘텐츠 삭제
- **Method:** `DELETE`
- **Path:** `/api/contents/{id}`
- **Response (204 No Content)**
