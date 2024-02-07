# 프로젝트 : 읽는 곳곳
## 🚀 소개
이 프로젝트는 스위프 3기 10팀 읽는곳곳 프로젝트의 백엔드 레파지토리입니다.

## 👥 기여자 Backend
- [@dltjdgh0428](https://github.com/dltjdgh0428)
- [@kf3907](https://github.com/kf3907)

## 📌주요기능
- 로그인
- 지도 기능 
- 독서 기록 기능
- 타이머 기능
- 내 기록 공유기능
- 독서 총 시간 기록 기능

### API 사용
- 소셜 로그인 : 카카오 API 사용
- 지도 API : 카카오 API 사용
- 책 Search API : 네이버 API 사용


## 📋 협업 규칙
### 브랜치 전략
- Main: 테스트가 모두 완료된 코드 병합
- dev: feature/기능명 브랜치 병합 전 체크 후 rebase → commit → merge 순으로 진행
- feature/기능명: 개별 기능 구현 후 PR. 코드 검토 후 dev에 병합

### 작업 흐름

1. 생성한 Branch에서 issue에 해당하는 작업을 진행합니다.
2. 자신의 repo에 commit 및 push 합니다. (push 할 때는 본인의 당일 작업한 것들과 함께 comment를 남깁니다.)
3. merge 하기 전 충돌이 나지 않게 주의합니다.

### 커밋 컨벤션

- feat: 새로운 기능 추가
  - 예: feat: 사용자 등록 기능 추가
- fix: 버그 수정
  - 예: fix: 로그인 버그 수정
- style: 코드 스타일 변경 (공백, 포맷팅 등)
  - 예: style: 코드 서식 수정
- refactor: 코드 리팩터링 (기능 변경 없음)
  - 예: refactor: 데이터베이스 연결 리팩터링
- docs: 문서 변경
  - 예: docs: 설명서 업데이트
- test: 테스트 코드 추가 또는 수정
  - 예: test: 회원가입 테스트 추가
- chore: 그 외의 작업 (빌드, 도구 설정, 패키지 업데이트 등)
  - 예: chore: 빌드 스크립트 업데이트

### 코드 리뷰
백엔드 기능별 이슈에 따라 
1. feature 브랜치에서 작업 → 
2. develop 브랜치로 PR 보내기 → 
3. 서로의 PR을 코드 리뷰 하고 반영이 되면 merge합니다.


## 🛠 기술 스택
- Backend: Java17, Spring Boot 3.2.2
- Database: MariaDB
- ORM: spring data JPA
- 인증: OAuth 소셜 로그인

## 🤝 기여 방법
- 프로젝트를 Fork합니다.
- feature/기능명 브랜치를 생성합니다 (git checkout -b feature/YourFeature).
- 변경 사항을 커밋합니다 (git commit -am 'Add some YourFeature').
- 브랜치에 Push합니다 (git push origin feature/YourFeature).
- 새로운 Pull Request를 요청합니다.