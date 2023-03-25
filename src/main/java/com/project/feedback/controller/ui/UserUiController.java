package com.project.feedback.controller.ui;

import com.project.feedback.domain.dto.board.BoardListResponse;
import com.project.feedback.domain.dto.course.AddStudentRequest;
import com.project.feedback.domain.dto.course.CourseDto;
import com.project.feedback.domain.dto.repository.RepositoryResponse;
import com.project.feedback.domain.dto.task.TaskFilterInfo;
import com.project.feedback.domain.dto.user.*;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserUiController {

    private final UserService userService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final CourseService courseService;
    private final FindService findService;
    private final BoardService boardService;
    private final EmailServiceImpl emailServiceImpl;
    private final EmailService emailService;

    @GetMapping
    public String list(Model model, @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CourseDto> courses = courseService.courses();
        model.addAttribute("courseList", courses);
        model.addAttribute("addStudentRequest", new AddStudentRequest());

        UserListResponse res = userService.getUserList(pageable,"","");
        model.addAttribute("userList", res.getContent());
        model.addAttribute("nowPage", res.getPageable().getPageNumber() + 1);
        model.addAttribute("lastPage", res.getTotalPages());

        UserFilterInfo userFilterInfo = new UserFilterInfo();
        model.addAttribute("userFilterInfo", userFilterInfo);
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
        session.setMaxInactiveInterval(240 * 60); // Session이 240분동안 유지
       
        return "redirect:/courses/students";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        // request에서 token 가져와서 기존 token 없애는 로직
        userService.logout(request);
        session.removeAttribute("jwt");
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("userJoinRequest", new UserJoinRequest());
        return "users/join";
    }

    @ResponseBody
    @PostMapping("/changeRole")
    public String changeRole(@RequestBody Map<String,List> map, Model model){
        System.out.println(map);
        String role =  map.get("userRole").get(0).toString(); System.out.println(role+"입니다.");
        int size = map.get("userList").size();
        for(int i =0; i < size; i++){
            userService.changeRoles(Long.valueOf(String.valueOf(map.get("userList").get(i))), role);
        }
        return "/users";
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
    public String myPage(Authentication auth, Model model,  @PageableDefault(size = 5) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        UserEntity user = findService.findUserByUserName(auth.getName());
        String userName = user.getUserName();
        CourseEntity course = findService.findCourseByUserId(user);
        List<RepositoryResponse> repositories = findService.findRepositoriesByUser(user).stream()
                .map(RepositoryResponse::of).toList();
        // 받은 커멘트 수
        int countOfComments = userService.getCountOfComments(user.getId());
        int countOfLikes = userService.getCountOfLikes(user.getId());
        BoardListResponse boardListDtoList = boardService.getBoardListByUserId(user.getId(), pageable);
        boardListDtoList.getContent().forEach(boardListDto -> boardListDto.setLikes(likeService.countLikesOfBoard(boardListDto.getId())));
        boardListDtoList.getContent().forEach(boardListDto -> boardListDto.setComments(commentService.countCommentsOfBoard(boardListDto.getId())));
        model.addAttribute("user", user);
        model.addAttribute("commentCount", countOfComments);
        model.addAttribute("likeCount", countOfLikes);
        model.addAttribute("boardList", boardListDtoList.getContent());
        model.addAttribute("nowPage", boardListDtoList.getPageable().getPageNumber() +1);
        model.addAttribute("lastPage", boardListDtoList.getTotalPages());
        model.addAttribute("userName", auth.getName());
        model.addAttribute("course", course);
        model.addAttribute("repositories", repositories);
        return "users/my";
    }
    @ResponseBody
    @GetMapping("/check")
    public boolean idCheck(@RequestParam String userName, Model model){
        // userName 중복 체크
        boolean check = true;
        try{
            userService.idCheck(userName);
        }catch (CustomException e){
            if(e.getErrorCode() == ErrorCode.DUPLICATED_USER_NAME){
                check = false;
            }
        }
        return check;
    }

    @ResponseBody
    @PostMapping("/emailSend")
    public String emailSend(@RequestParam String email) throws  Exception{
        return emailService.sendSimpleMessage(email);
    }

    @ResponseBody
    @PostMapping("/emailConfirm")
    public String emailConfirm(@RequestParam String code,@RequestParam String check, Model model){
        String ePw = EmailServiceImpl.ePw;
        if(ePw.equals(code)){
            model.addAttribute("emailConfirm", true);
        }else{
            model.addAttribute("emailConfirm", false);
            model.addAttribute("message", "잘못된 입력입니다.");
        }
        if(check.equals("find")){
            return "users/findId";
        }else{
            return "users/join";
        }
    }

    @GetMapping("/pw")
    public String getPwUpdate(Model model, Authentication auth){
        model.addAttribute("userName", auth.getName());
        model.addAttribute("userChangePwRequest", new UserChangePwRequest());
     return "users/pw";
    }

    @PutMapping("/pw")
    public String pwUpdateByLogin(@ModelAttribute UserChangePwRequest req, HttpSession session){
        userService.updatePwByLogin(req);
        session.removeAttribute("jwt");
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/find_id")
    public String idFind(){
        return "users/find_id";
    }

    @PostMapping("/find_id")
    public String pwFind(@RequestParam String email, Model model){
        UserEntity user = findService.findUserByEmail(email);
        String msg = "회원님의 아이디는" + user.getUserName() + "입니다.\n로그인 해주세요.";
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("message", msg);
        model.addAttribute("nextUrl", "/users/login");
        return "users/find_id";
    }

    @GetMapping("/find_pw")
    public String getPwFind(Model model){
        model.addAttribute("userFindPwRequest", new UserFindPwRequest());
        return "users/find_pw";
    }

    @PutMapping("/find_pw")
    public String pwUpdateByAnonymous(@ModelAttribute UserFindPwRequest req, Model model){
        userService.updatePwByAnonymous(req);
        model.addAttribute("message", "비밀번호가 변경되었습니다.\n새로 로그인해주세요.");
        model.addAttribute("nextUrl", "/");
        return "users/find_pw";
    }

    //이름, 사용자명 필터링해서 user 목록 출력
    @PostMapping("/filter")
    public String getUsersByFitler(@ModelAttribute UserFilterInfo userFilterInfo, RedirectAttributes redirectAttribute, Authentication auth) {
        String filter = userFilterInfo.getFilter();
        String name = userFilterInfo.getName();
        redirectAttribute.addAttribute("filter", filter);
        redirectAttribute.addAttribute("name", name);

        if(name == ""){ // 입력 값 없는 경우
            return "redirect:/users";
        }
        return "redirect:/users/filter";
    }

    @GetMapping("/filter")
    public String listByFilter(Model model, @PageableDefault(size = 20) Pageable pageable, @RequestParam String filter, @RequestParam String name) {
        UserListResponse res =  userService.getUserList(pageable, filter, name);
        System.out.print(filter + "filter");
        model.addAttribute("userList", res.getContent());
        model.addAttribute("nowPage", res.getPageable().getPageNumber() + 1);
        model.addAttribute("lastPage", res.getTotalPages());


        List<CourseDto> courses = courseService.courses();
        model.addAttribute("courseList", courses);
        model.addAttribute("addStudentRequest", new AddStudentRequest());

        UserFilterInfo userFilterInfo = new UserFilterInfo(filter, name);
        model.addAttribute("userFilterInfo", userFilterInfo);
        return "users/show";
    }
}
