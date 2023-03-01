package com.project.feedback.service;

import com.project.feedback.domain.Role;
import com.project.feedback.domain.dto.user.*;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.CourseUserEntity;
import com.project.feedback.domain.entity.TokenEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.exception.CustomException;
import com.project.feedback.auth.JwtTokenUtil;
import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.CourseUserRepository;
import com.project.feedback.repository.TokenRepository;
import com.project.feedback.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CourseUserRepository courseUserRepository;
    private final CourseRepository courseRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder encoder;
    private final FindService findService;

    @Value("${jwt.token.secret}")
    private String secretKey;
    @Value("${dummy.default-password}")
    private String defaultPw;
    //  한시간
    private long accessExpireTimeMs = 1000 * 60 * 60;
    // 3시간
    private long refreshExpireTimeMs = 1000 * 60 * 60 * 6;

    public UserJoinResponse saveUser(UserJoinRequest req) {

        // userName 중복 체크
        userRepository.findByUserName(req.getUserName()).ifPresent(user -> {
            throw new CustomException(ErrorCode.DUPLICATED_USER_NAME, "UserName(" + req.getUserName() + ")이 중복입니다.");
        });
        // email 중복 체크
        userRepository.findByEmail(req.getEmail()).ifPresent(user -> {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL, "Email(" + req.getEmail() + ")이 중복입니다.");
        });
        // 비밀번호 인코딩 후 저장
        String encodedPassword = encoder.encode( req.getPassword() );
        UserEntity savedUser = userRepository.save(req.toEntity(encodedPassword));

        return UserJoinResponse.of(savedUser);
    }
    public boolean idCheck(String userName){
        // userName 중복 체크
        userRepository.findByUserName(userName).ifPresent(user -> {
            throw new CustomException(ErrorCode.DUPLICATED_USER_NAME, "UserName(" + userName + ")이 중복입니다.");
        });
        return true;
    }
    public UserLoginResponse login(UserLoginRequest req) {

        UserEntity user = findService.findUserByUserName(req.getUserName());

        // 비밀번호 체크
        if(!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // JWT Token 발급
        String jwtToken = JwtTokenUtil.createToken(user.getUserName(), secretKey, accessExpireTimeMs);
        String refreshToken = JwtTokenUtil.createToken(user.getUserName(), secretKey, refreshExpireTimeMs);

        // todo RefreshToken구현 할 때 사용할 예정
        tokenRepository.save(TokenEntity.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());

        return new UserLoginResponse(jwtToken, refreshToken);
    }

    public UserChangeRoleResponse changeRole(Long userId, UserChangeRoleRequest req) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));

        Role newRole;

        // "admin" 또는 "student"을 입력받는다면 입력받은 값으로 Role 수정
        if(req.getRole().toLowerCase().equals("admin")) {
            newRole = Role.ROLE_ADMIN;
        } else if(req.getRole().toLowerCase().equals("student")) {
            newRole = Role.ROLE_STUDENT;
        } else {
            throw new RuntimeException();
        }

        user.setRole(newRole);
        userRepository.save(user);

        return UserChangeRoleResponse.of(user.getId(), newRole);
    }

    public void changeRoles(Long userId, String role) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
        user.setRole(Role.valueOf(role));
        userRepository.save(user);
    }

    public void setDefaultUsers(){
        UserJoinRequest[] userJoinRequests = new UserJoinRequest[4];
        userJoinRequests[0] = new UserJoinRequest("admin", defaultPw, "관리자", "aaa1@aaaaa.com");
        userJoinRequests[1] = new UserJoinRequest("student", defaultPw, "학생", "aaa2@aaaaa.com");
        userJoinRequests[2] = new UserJoinRequest("manager", defaultPw, "관리자", "aaa3@aaaaa.com");
        userJoinRequests[3] = new UserJoinRequest("teacher", defaultPw, "선생님", "aaa4@aaaaa.com");
        for(int i = 0; i < userJoinRequests.length; i++){
            try{
                findService.findUserByUserName(userJoinRequests[i].getUserName());
            }catch (CustomException e){
                if(e.getErrorCode() == ErrorCode.USERNAME_NOT_FOUND){
                    saveUser(userJoinRequests[i]);
                    if(!userJoinRequests[i].getUserName().equals("student")) changeDefaultRole(userJoinRequests[i].getUserName());
                }
            }

        }

    }

    public void addDummyUsers(){
        String names[] ={"김","이" ,"박" ,"최" ,"정" ,"강","조","윤","장","임"};
        for(int i= 0 ; i < 10; i++) {
            String userName = names[i];
            naming(userName);
        }
    }

    public void naming(String userName){
        for (int j = 0; j < 10; j++) {
            UUID one = UUID.randomUUID();
            String[] random = one.toString().split("-");
            String randomName = userName + random[0];
            String email =
                userName + random[0] + "@gmail.com";
            UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName(randomName) // 중복 불가
                .password(defaultPw)
                .realName(userName + random[0])
                .email(email)
                .build();
            saveUser(userJoinRequest);
        }
    }


    public void changeDefaultRole(String userRole){
        UserEntity user = userRepository.findByUserName(userRole)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
        Role newRole;
        if(userRole.equals("admin")){
            newRole = Role.ROLE_ADMIN;
        } else if (userRole.equals("manager")) {
            newRole = Role.ROLE_MANAGER;
        } else if (userRole.equals("teacher")) {
            newRole = Role.ROLE_TEACHER;
        } else{
            throw new RuntimeException();
        }
        user.setRole(newRole);
        userRepository.save(user);
    }
    public UserListResponse getUserList(Pageable pageable) {
        Page<UserEntity> users = userRepository.findAll(pageable);
        List<UserListDto> content = new ArrayList<>();
        for(UserEntity user : users) {
            String courseName = "";

            Optional<CourseUserEntity> courseEntityUser = courseUserRepository.findCourseEntityUserByUserId(user.getId());
            courseEntityUser.ifPresent(result -> courseEntityUser.get());

            if(courseEntityUser.isPresent()) {
                Optional<CourseEntity> courseEntity =
                    courseRepository.findById(courseEntityUser.get().getCourseEntity().getId());
                if(courseEntity.isPresent()){
                    courseName = courseEntity.get().getName();
                }
            }
            content.add(UserListDto.of(user,courseName));
        }

        return new UserListResponse(content, pageable, users);
    }
    public UserChangePwResponse updatePwByLogin(UserChangePwRequest req){
        UserEntity user = userRepository.findByUserName(req.getUserName())
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
        String userPassword = user.getPassword();
//        기존 비밀번호 체크
        if(!encoder.matches(req.getCurPassword(), userPassword)){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }else{
            // 기존 비밀번호랑 변경 비밀번호랑 같으면 안됨
            if(encoder.matches(req.getChPassword(), userPassword)){
                throw new CustomException(ErrorCode.DUPLICATED_PREV_PASSWORD);
            }else{
                String encodedPassword = encoder.encode(req.getChPassword());
                user.setPassword(encodedPassword);
                user = userRepository.save(user);
            }
        }
        return UserChangePwResponse.of(user.getUserName());
    }
    public UserFindPwResponse updatePwByAnonymous(UserFindPwRequest req){
        UserEntity user = userRepository.findByUserName(req.getUserName())
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
        // 현재 비밀번호 알필요 없음 `
        String userPassword = user.getPassword();
        if(encoder.matches(req.getChPassword(), userPassword)){
            throw new CustomException(ErrorCode.DUPLICATED_PREV_PASSWORD);
        }else{
            String encodedPassword = encoder.encode(req.getChPassword());
            user.setPassword(encodedPassword);
            user = userRepository.save(user);
        }
        return UserFindPwResponse.of(user.getUserName());
    }
    public void deleteToken(Long tokenId){
        tokenRepository.deleteById(tokenId);
    }
    public boolean refreshToken(String token){
        try {
            TokenEntity tokenEntity = findService.findTokenByCurrentToken(token);
            String refreshToken = tokenEntity.getRefreshToken();
            // refresh token expire 되었는지 여부
            if (JwtTokenUtil.isRefreshTokenExpired(refreshToken, secretKey)) {
                // refresh token 까지 만료되었으면 토큰 삭제
                deleteToken(tokenEntity.getId());
                return false;
            } else {
                // refresh token expire 안되어있으면 access token 수정
                String userName = JwtTokenUtil.getUserName(refreshToken, secretKey);
                String newAccessToken = JwtTokenUtil.createToken(userName, secretKey, accessExpireTimeMs);

                // delete 후 새로운 token 생성
                deleteToken(tokenEntity.getId());
                tokenRepository.save(TokenEntity.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(refreshToken)
                        .build());
                return true;
            }
        }catch (CustomException e){
            return false;
        }
    }
    public void logout(HttpServletRequest request){
            String authorizationHeader = request.getSession().getAttribute("jwt").toString();
            String token = authorizationHeader.split(" ")[1];
            Long curTokenId = findService.findTokenByCurrentToken(token).getId();
            deleteToken(curTokenId);
    }
 }

