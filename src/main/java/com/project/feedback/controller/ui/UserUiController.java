package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.dto.board.CodeWriteDto;
import com.project.feedback.domain.dto.board.CommentWriteDto;
import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.dto.user.*;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserUiController {

    private final UserService userService;
    private final CourseService courseService;
    private final FindService findService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final CodeService codeService;
    private final EmailServiceImpl emailServiceImpl;
    private final EmailService emailService;
    @GetMapping
    public String list(Model model, @PageableDefault(size = 20) Pageable pageable) {
        List<CourseDto> courses = courseService.courses();
        model.addAttribute("courseList", courses);
        model.addAttribute("addStudentRequest", new AddStudentRequest());
        UserListResponse res = userService.getUserList(pageable);
        model.addAttribute("userList", res.getContent());
        model.addAttribute("nowPage", res.getPageable().getPageNumber() + 1);
        model.addAttribute("lastPage", res.getTotalPages());
        return "users/show";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginRequest req, HttpServletRequest httpServletRequest, Model model) {
        String jwtToken = "";
        try {
            jwtToken = userService.login(req).getJwt();
        } catch (CustomException e) {
            if(e.getErrorCode() == ErrorCode.USERNAME_NOT_FOUND) {
                model.addAttribute("message", "아이디가 존재하지 않습니다");
                model.addAttribute("nextUrl", "/users/login");
                return "users/login";
            }
            if(e.getErrorCode() == ErrorCode.INVALID_PASSWORD) {
                model.addAttribute("message", "비밀번호가 틀렸습니다.");
                model.addAttribute("nextUrl", "/users/login");
                return "users/login";
            }
        } catch (Exception e) {
            throw e;
        }

        // 기존 Session 파기 후 새로 생성
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);

        // login 후 받은 jwt Token 값을 session에 넣어줌
        session.setAttribute("jwt", "Bearer " + jwtToken);
        session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("jwt");
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("userJoinRequest", new UserJoinRequest());
        return "users/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute UserJoinRequest req, Model model) {
        try {
            userService.saveUser(req);
        } catch(CustomException e) {
            if(e.getErrorCode() == ErrorCode.DUPLICATED_USER_NAME) {
                model.addAttribute("message", "UserName이 중복됩니다");
                model.addAttribute("nextUrl", "/users/join");
                model.addAttribute("userJoinRequest", new UserJoinRequest());
                return "users/join";
            } else if (e.getErrorCode() == ErrorCode.DUPLICATED_EMAIL) {
                model.addAttribute("message", "Email이 중복됩니다");
                model.addAttribute("nextUrl", "/users/join");
                model.addAttribute("userJoinRequest", new UserJoinRequest());
                return "users/join";
            }
        } catch (Exception e) {
            throw e;
        }

        model.addAttribute("userLoginRequest", new UserLoginRequest());
        model.addAttribute("message", "회원가입이 완료 되었습니다\n로그인 해주세요");
        model.addAttribute("nextUrl", "/users/login");
        return "users/login";
    }
    @GetMapping("/my")
    public String myPage(Authentication auth, Model model){
        UserEntity user = findService.findUserByUserName(auth.getName());
        String userName = user.getUserName();
        List<BoardWriteDto> boardWriteDtoList = boardService.getBoardListByUserName(userName);
        List<CommentWriteDto> commentWriteDtoList = commentService.getCommentListByUserName(userName);
        List<CodeWriteDto> codeWriteDtoList = codeService.getCodeListByUserName(userName);
        int commentCount = commentWriteDtoList.size();
        model.addAttribute("codeList", codeWriteDtoList);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("boardList", boardWriteDtoList);
        model.addAttribute("userName", auth.getName());
        return "users/my";
    }
    @ResponseBody
    @PostMapping("/emailSend")
    public String emailSend(@RequestParam String email) throws  Exception{
        return emailService.sendSimpleMessage(email);
    }
    @ResponseBody
    @PostMapping("/emailConfirm")
    public String emailConfirm(@RequestParam String code, Model model){
        String ePw = EmailServiceImpl.ePw;
        if(ePw.equals(code)){
            model.addAttribute("emailConfirm", true);
        }else{
            model.addAttribute("emailConfirm", false);
            model.addAttribute("message", "잘못된 입력입니다.");
        }
        return "users/join";
    }
}
