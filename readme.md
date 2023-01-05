# Student Feedback App

수업중 자신이 개발한 코드를 업로드 하여 진도를 Check하고 다른 학생들이 작성한 코드를 작성하기 위한 앱 입니다.



## 구동방법

아래 두가지 Envronment Variable을 넣고 실행 합니다.

| Environment Variable       | 예제                                                         |      |
| -------------------------- | ------------------------------------------------------------ | ---- |
| SPRING_DATASOURCE_URL      | jdbc:mysql://ec2-1-23-456-789.ap-northeast-2.compute.amazonaws.com:3306/student-feedback-app |      |
| SPRING_DATASOURCE_PASSWORD | ex12345678                                                   |      |



- 회원목록 조회는 Admin만 가능 합니다.
- 그래서 회원 가입 후 DB에 들어가 해당 User의 Role을 ADMIN으로 바꿔주어야 합니다.

## 접속 방법

http://localhost:8080 으로 접속하면 초기 화면이 보입니다.



## ERD

[ERD](https://app.diagrams.net/#G1Vh3notO6oMFYSy4GcQSlctRlbKeuyoHX)

