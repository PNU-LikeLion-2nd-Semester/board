package org.example.be.user;

import org.example.be.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;
	private final CustomUserDetailsService userDetailsService;
	private final JwtTokenProvider tokenProvider;

	public ResponseEntity<String> register(MemberRegisterRequest request) {
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
			return ResponseEntity.badRequest().body("Username is already taken.");
		} catch (Exception e) {
			Member member = new Member();
			member.setRole("USER");
			member.setUsername(request.username());
			member.setPassword(request.password());
			memberRepository.save(member);

			return ResponseEntity.ok("User registered successfully");
		}
	}

	public ResponseEntity<String> login(LoginRequest request) {
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
			String token = tokenProvider.createToken(userDetails.getUsername(),
				userDetails.getAuthorities().toString());
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.status(401).body("Invalid username or password");
		}

	}
}
