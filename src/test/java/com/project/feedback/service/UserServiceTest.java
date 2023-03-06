package com.project.feedback.service;

import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.CourseUserRepository;
import com.project.feedback.repository.TokenRepository;
import com.project.feedback.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.mock;

class UserServiceTest {

    UserRepository userRepository = mock(UserRepository.class);
    CourseUserRepository courseUserRepository = mock(CourseUserRepository.class);
    CourseRepository courseRepository = mock(CourseRepository.class);
    TokenRepository tokenRepository = mock(TokenRepository.class);
    BCryptPasswordEncoder encoder;
    FindService findService;
    CourseService courseService;
    TaskService taskService;
    BoardService boardService;
    UserService userService = new UserService(userRepository, courseUserRepository, courseRepository, tokenRepository,
            encoder, findService, courseService, taskService, boardService);


}