package org.example.be;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.example.be.security.JwtTokenProvider;
import org.example.be.user.LoginRequest;
import org.example.be.user.Member;
import org.example.be.user.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper om;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private MemberRepository memberRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	private Member member;

	@BeforeEach
	void setup() {
		member = Member.builder()
			.username("testuser")
			.password("password")
			.role("USER")
			.build();

		given(memberRepository.findByUsername(member.getUsername()))
			.willReturn(Optional.of(member));

		given(passwordEncoder.encode(anyString())).willAnswer(invocation -> invocation.getArgument(0));
	}

	@Test
	public void testCorsPreflightRequest() throws Exception {
		mockMvc.perform(options("/api/test-endpoint") // CORS 요청을 보낼 엔드포인트
				.header(ORIGIN, "http://localhost:3000")
				.header(ACCESS_CONTROL_REQUEST_METHOD, "GET"))
			.andExpect(status().isOk())
			.andExpect(header().exists(ACCESS_CONTROL_ALLOW_ORIGIN))
			.andExpect(header().string(ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000"))
			.andExpect(header().exists(ACCESS_CONTROL_ALLOW_METHODS))
			.andExpect(header().string(ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE,OPTIONS"));
	}

	@Test
	public void testCorsActualRequest() throws Exception {
		mockMvc.perform(get("/api/test-endpoint")
				.header(ORIGIN, "http://localhost:3000"))
			.andExpect(status().isOk())
			.andExpect(header().exists(ACCESS_CONTROL_ALLOW_ORIGIN))
			.andExpect(header().string(ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000"));
	}

	@Test
	public void testCorsWithUnauthorizedOrigin() throws Exception {
		mockMvc.perform(get("/api/test-endpoint")
				.header(ORIGIN, "http://unauthorized-origin.com"))
			.andExpect(status().isForbidden())
			.andExpect(header().doesNotExist(ACCESS_CONTROL_ALLOW_ORIGIN));
	}

	@Test
	void testLoginWithNonExistentUser_ShouldReturn401() throws Exception {
		LoginRequest request = new LoginRequest("non exists username", "");

		mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(request)))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.token").isEmpty())
			.andDo(print());
	}

	@Test
	void testLoginWithIncorrectPassword_ShouldReturn401() throws Exception {
		LoginRequest request = new LoginRequest(member.getUsername(), "incorrect password");

		mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(request)))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.token").isEmpty())
			.andDo(print());
	}

	@Test
	void testLoginWithValidCredentials_ShouldReturnTokenAnd200() throws Exception {
		LoginRequest request = new LoginRequest(member.getUsername(), member.getPassword());

		String token = "testtoken";
		given(jwtTokenProvider.createToken(anyString(), anyString())).willReturn(token);

		mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token").value(token))
			.andDo(print());
	}
}

