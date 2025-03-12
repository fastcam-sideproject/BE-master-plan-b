package com.example.masterplanbbe;

import com.example.masterplanbbe.common.config.MailConfig;
import com.example.masterplanbbe.common.config.RedisConfig;
import com.example.masterplanbbe.common.security.config.SecurityConfig;
import com.example.masterplanbbe.common.security.handler.CustomLogoutHandler;
import com.example.masterplanbbe.common.security.handler.OAuth2SuccessHandler;
import com.example.masterplanbbe.common.security.jwt.JwtService;
import com.example.masterplanbbe.common.security.jwt.TokenUtils;
import com.example.masterplanbbe.domain.chat.controller.ChatController;
import com.example.masterplanbbe.domain.chat.repository.RedisChatRepository;
import com.example.masterplanbbe.domain.chat.service.ChatBatchService;
import com.example.masterplanbbe.domain.chat.service.ChatService;
import com.example.masterplanbbe.domain.chat.service.RedisPublisher;
import com.example.masterplanbbe.domain.chat.service.RedisSubscriber;
import com.example.masterplanbbe.domain.chat.util.SnowflakeIdGenerator;
import com.example.masterplanbbe.domain.member.controller.DeployController;
import com.example.masterplanbbe.domain.member.controller.MemberController;
import com.example.masterplanbbe.domain.member.service.MemberService;
import com.example.masterplanbbe.domain.post.service.LikePostService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

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
