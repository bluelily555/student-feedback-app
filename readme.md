# Student Feedback App

수업중 자신이 개발한 코드를 업로드 하여 진도를 Check하고 다른 학생들이 작성한 코드를 작성하기 위한 앱 입니다.


<div align="center">
 <img src="https://img.shields.io/badge/SpringBoot-6DB33F.svg?logo=Spring-Boot&logoColor=white" />
 <img src="https://img.shields.io/badge/SpringSecurity-6DB33F.svg?logo=Spring-Security&logoColor=white" />
 <img src="https://img.shields.io/badge/MySQL-3776AB.svg?logo=MySql&logoColor=white" />
 <img src="https://img.shields.io/badge/Docker-2496ED.svg?logo=Docker&logoColor=white" />
 <img src="https://img.shields.io/badge/AmazonEC2-FF9900.svg?logo=Amazon-EC2&logoColor=white" />
</div>


## 구동방법

아래 두가지 Envronment Variable을 넣고 실행 합니다.

| Environment Variable       | 예제                                                         |      |
| -------------------------- | ------------------------------------------------------------ | ---- |
| SPRING_DATASOURCE_URL      | jdbc:mysql://ec2-1-23-456-789.ap-northeast-2.compute.amazonaws.com:3306/student-feedback-app |      |
| SPRING_DATASOURCE_PASSWORD | ex12345678                                                   |      |



- 회원목록 조회는 Admin만 가능 합니다.
- 그래서 회원 가입 후 DB에 들어가 해당 User의 Role을 ADMIN으로 바꿔주어야 합니다.

[Intelli J에서 구동 방법]](https://krksap.tistory.com/2183)

## 접속 방법

http://localhost:8080 으로 접속하면 초기 화면이 보입니다.

[웹 링크](http://ec2-3-38-111-117.ap-northeast-2.compute.amazonaws.com:8083/)

[스웨거](http://ec2-3-38-111-117.ap-northeast-2.compute.amazonaws.com:8083/swagger-ui/)

## ERD

[ERD](https://app.diagrams.net/#G1Vh3notO6oMFYSy4GcQSlctRlbKeuyoHX)