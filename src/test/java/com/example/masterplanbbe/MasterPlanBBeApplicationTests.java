package com.example.masterplanbbe;

import com.example.masterplanbbe.infrastructure.configuration.MailConfig;
import com.example.masterplanbbe.infrastructure.configuration.RedisConfig;
import com.example.masterplanbbe.infrastructure.configuration.SecurityConfig;
import com.example.masterplanbbe.infrastructure.security.handler.CustomLogoutHandler;
import com.example.masterplanbbe.infrastructure.security.handler.OAuth2SuccessHandler;
import com.example.masterplanbbe.infrastructure.security.jwt.JwtService;
import com.example.masterplanbbe.infrastructure.security.jwt.TokenUtils;
import com.example.masterplanbbe.presentation.controller.ChatController;
import com.example.masterplanbbe.infrastructure.repository.RedisChatRepository;
import com.example.masterplanbbe.application.service.ChatBatchService;
import com.example.masterplanbbe.application.service.ChatService;
import com.example.masterplanbbe.application.service.RedisPublisher;
import com.example.masterplanbbe.application.service.RedisSubscriber;
import com.example.masterplanbbe.infrastructure.util.SnowflakeIdGenerator;
import com.example.masterplanbbe.presentation.controller.DeployController;
import com.example.masterplanbbe.presentation.controller.MemberController;
import com.example.masterplanbbe.domain.service.MemberService;
import com.example.masterplanbbe.application.service.LikePostService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.Executor;

@SpringBootTest
@ActiveProfiles("test")
class MasterPlanBBeApplicationTests {

	@MockBean
	RedisConfig redisConfig;

	@MockBean
	RedisConnectionFactory redisConnectionFactory;

	@MockBean
	MailConfig mailConfig;

	@MockBean
	SecurityConfig securityConfig;

	@MockBean
	CustomLogoutHandler customLogoutHandler;

	@MockBean
	TokenUtils tokenUtils;

	@MockBean
	OAuth2SuccessHandler oAuth2SuccessHandler;

	@MockBean
	JwtService jwtService;

	@MockBean
	RedisPublisher redisPublisher;

	@MockBean
	RedisSubscriber redisSubscriber;

	@MockBean
	ChatController chatController;

	@MockBean
	ChatService chatService;

	@MockBean
	RedisChatRepository redisChatRepository;

	@MockBean
	ChatBatchService chatBatchService;

	@MockBean
	DeployController deployController;

	@MockBean
	MemberService memberService;

	@MockBean
	MemberController memberController;

	@MockBean
	LikePostService likePostService;

	@MockBean
	PasswordEncoder passwordEncoder;

	@MockBean
	SnowflakeIdGenerator snowflakeIdGenerator;

	@Test
	void contextLoads() {
	}

}
