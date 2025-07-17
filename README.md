# Library Management System
도서관 관리 시스템 - Spring Boot와 MySQL을 사용한 웹 애플리케이션

![Java](https://img.shields.io/badge/Java-11-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 목차
- [소개](#소개)
- [기능](#기능)

## 소개
서울도서관 스마트도서관 소장자료 현황 open api를 활용한 도서대여 서비스

## 기능
회원가입시 작성한 패스워드를 Bycrypt 방식을 이용한 Hash암호화로 저장
로그인시 Hash암호화된 패스워드와 평문 패스워드를 비교 로그인
도서현황 확인

## 주요 기능
- 📚 도서 관리
- 👥 사용자 관리
- 📋 대출 관리
- 📊 통계 및 리포트

### 도서 관리
- `GET /api/books` - 모든 도서 조회
- `POST /api/books` - 새 도서 등록
- `PUT /api/books/{id}` - 도서 정보 수정
- `DELETE /api/books/{id}` - 도서 삭제

### 사용자 관리
- `GET /api/users` - 모든 사용자 조회
- `POST /api/users` - 새 사용자 등록
- `PUT /api/users/{id}` - 사용자 정보 수정
- `DELETE /api/users/{id}` - 사용자 삭제

### 대출 관리
- `GET /api/loans` - 모든 대출 조회
- `POST /api/loans` - 새 대출 등록
- `PUT /api/loans/{id}` - 대출 정보 수정
- `DELETE /api/loans/{id}` - 대출 삭제


## 🔗 서울도서관 OpenAPI
- [서울도서관 소장자료 현황정보 OpenAPI](https://data.seoul.go.kr/dataList/OA-15413/S/1/datasetView.do)


## 📌 ERD (Entity Relationship Diagram)

![ERD](./library.png)

---

## 🗃️ 테이블 구조

### 🔹 User (회원)

| 필드명       | 설명         | 타입     |
|--------------|--------------|----------|
| `id`         | 회원 고유 ID (PK) | BIGINT |
| `name`       | 이름         | VARCHAR |
| `email`      | 이메일       | VARCHAR |
| `phone`      | 전화번호     | VARCHAR |
| `created_at` | 가입일       | DATETIME |
| `updated_at` | 수정일       | DATETIME |

---

### 🔹 Book (도서)

| 필드명        | 설명           | 타입     |
|---------------|----------------|----------|
| `id`          | 책 고유 ID (PK)   | BIGINT |
| `title`       | 제목           | VARCHAR |
| `author`      | 작가           | VARCHAR |
| `is_available`| 대여 가능 여부   | BOOLEAN |
| `created_at`  | 등록일         | DATETIME |
| `updated_at`  | 수정일         | DATETIME |

---

### 🔹 Rental (대여 기록)

| 필드명        | 설명            | 타입     |
|---------------|-----------------|----------|
| `id`          | 대여 기록 ID (PK) | BIGINT |
| `user_id`     | 회원 고유 ID (FK) | BIGINT |
| `book_id`     | 책 고유 ID (FK)   | BIGINT |
| `rent_date`   | 대여 일자        | DATETIME |
| `return_date` | 반납 일자        | DATETIME |

---


## 🔗 테이블 관계

- `User (1) : (N) Rental`  
  → 한 명의 회원은 여러 건의 대여 기록을 가질 수 있습니다.

- `Book (1) : (N) Rental`  
  → 한 권의 책은 여러 번 대여될 수 있습니다.

---