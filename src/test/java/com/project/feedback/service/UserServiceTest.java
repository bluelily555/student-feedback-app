package com.project.feedback.service;

import com.project.feedback.application.BoardService;
import com.project.feedback.application.CourseService;
import com.project.feedback.application.FindService;
import com.project.feedback.application.TaskService;
import com.project.feedback.domain.dao.Impl.UserDAOImpl;
import com.project.feedback.infra.outgoing.repository.CourseRepository;
import com.project.feedback.infra.outgoing.repository.CourseUserRepository;
import com.project.feedback.repository.TokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.mock;

class UserServiceTest {

    UserDAOImpl userDAO = mock(UserDAOImpl.class);
    CourseUserRepository courseUserRepository = mock(CourseUserRepository.class);
    CourseRepository courseRepository = mock(CourseRepository.class);
    TokenRepository tokenRepository = mock(TokenRepository.class);
    BCryptPasswordEncoder encoder;
    FindService findService;
    CourseService courseService;
    TaskService taskService;
    BoardService boardService;


}