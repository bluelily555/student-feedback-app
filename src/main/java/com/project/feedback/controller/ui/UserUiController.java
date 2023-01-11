package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.user.UserJoinRequest;
import com.project.feedback.domain.dto.user.UserListResponse;
import com.project.feedback.domain.dto.user.UserLoginRequest;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserUiController {

    private final UserService userService;

    @GetMapping
    public String list(Model model, @PageableDefault(size = 20) Pageable pageable) {
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
            }
        } catch (Exception e) {
            throw e;
        }

        model.addAttribute("userLoginRequest", new UserLoginRequest());
        model.addAttribute("message", "회원가입이 완료 되었습니다\n로그인 해주세요");
        model.addAttribute("nextUrl", "/users/login");
        return "users/login";
    }

}
