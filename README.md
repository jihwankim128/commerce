# 학습 로그

우테코 오픈 미션 과정 중의 전체 학습 내용을 정리한 문서입니다.
오픈 미션 종료 후에도 계속해서 업데이트해 나갈 예정입니다.

## 1. ATDD를 위한 REST DOCS 적용

* RestDocs 공식 문서 - [링크](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/#getting-started-using-the-snippets)
* index.doc, testController, testCode 필수

### 1-1. Gradle Migration

* Kotlin DSL에서는 Groovy와 문법이 다름
* 해당 사이트 참고 - [링크](https://docs.gradle.org/8.5/userguide/migrating_from_groovy_to_kotlin_dsl.html)
  * 본 프로젝트는 gradle 8.5 버전으로 마이그레이션 완료

### 1-2. Custom Task 추가

* Local에서 작업 시 IDE 편의성을 위한 TASK

### 1-3. Production 보안 설정

* Security 등 추가해서 보안 설정 가능
  * 이미 할 줄 아는거라서 포함 X

## 2. Web Common Module Separation

### 2-1. 전역 예외 처리

* gradle bean validation 추가
* bean validation에서 발생하는 예외 처리 관심사 분리
* 비즈니스에서 발생하는 예외 관심사 분리
  * 학습 용도이므로 IllegalArgument로 통일
* 그 외 예외 관심사 분리 (서버 에러)

### 2-2. 전역 응답 처리

* 공통 응답 템플릿을 활용하는 경우, 반복되는 형태
  * controller -> `return ApiTemplate.of(data);`
  * 반복되는 형태를 `ResponseBodyAdvice`로 공통 Wrapping 처리하며 관심사 분리
* Converter 등록 순서로 인해 String Wrapping 실패
  * `org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addDefaultHttpMessageConverters`
  * String 전용 Wrapper 도입으로 Wrapping