package com.project.feedback.controller.api;

import com.project.feedback.domain.Response;
import com.project.feedback.service.UserService;
import com.project.feedback.domain.dto.user.UserChangeRoleRequest;
import com.project.feedback.domain.dto.user.UserChangeRoleResponse;
import com.project.feedback.domain.dto.user.UserJoinRequest;
import com.project.feedback.domain.dto.user.UserJoinResponse;
import com.project.feedback.domain.dto.user.UserListResponse;
import com.project.feedback.domain.dto.user.UserLoginRequest;
import com.project.feedback.domain.dto.user.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest req) {
        UserJoinResponse res = userService.saveUser(req);
        return Response.success(res);
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest req) {
        UserLoginResponse res = userService.login(req);
        return Response.success(res);
    }

    @PostMapping("/{userId}/role/change")
    public Response<UserChangeRoleResponse> changeRole(@PathVariable Long userId, @RequestBody UserChangeRoleRequest req) {
        UserChangeRoleResponse res = userService.changeRole(userId, req);
        return Response.success(res);
    }


    @GetMapping
    public Response<UserListResponse> list(
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        UserListResponse res = userService.getUserList(pageable);
        return Response.success(res);
    }

}
