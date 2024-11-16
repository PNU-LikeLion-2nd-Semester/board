package org.example.be.user;

import org.example.be.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<String> register(MemberRegisterRequest request) {
		if (memberRepository.existsByUsername(request.username())) {
			return ResponseEntity.badRequest().body("Username is already taken.");
		}
		Member member = new Member();
		member.setRole("USER");
		member.setUsername(request.username());
		member.setPassword(passwordEncoder.encode(request.password()));
		memberRepository.save(member);

		return ResponseEntity.ok("User registered successfully");
	}

	public ResponseEntity<LoginResponse> login(LoginRequest request) {
		try {
			Member member = memberRepository.findByUsername(request.username())
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
			if (!member.getPassword().equals(passwordEncoder.encode(request.password()))) {
				throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
			}
			UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
			String token = tokenProvider.createToken(userDetails.getUsername(),
				userDetails.getAuthorities().toString());
			return ResponseEntity.ok(new LoginResponse(token));
		} catch (Exception e) {
			return ResponseEntity.status(401).body(new LoginResponse(null));
		}
	}
}
