package com.project.feedback.service;

import com.project.feedback.domain.dto.user.EmailAuthResponse;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender emailSender;
    private final UserService userService;

    public static final String ePw = createKey();

    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);

        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);//보내는 대상
        message.setSubject("이메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 feedback application입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("pauljungho.text@gmail.com","feedback"));//보내는 사람

        return message;
    }
    public static String createKey(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i = 0; i < 8; i++){
            int id = rnd.nextInt(3);
            switch (id){
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }
    @Override
    public EmailAuthResponse sendSimpleMessage(String to) throws Exception {
        MimeMessage msg = createMessage(to);

        // db에서 찾음
        if(userService.checkEmailValid(to)){
            try{
                emailSender.send(msg);
            }catch (MailException e){
                e.printStackTrace();
                throw new IllegalAccessException();
            }
            return EmailAuthResponse.builder()
                    .requestedEmail(to)
                    .status("SUCCESS").build();
        }else {
            return EmailAuthResponse.builder()
                    .requestedEmail(to)
                    .status("FAIL")
                    .message("EMAIL이 없습니다.")
                    .build();
        }
    }
}
