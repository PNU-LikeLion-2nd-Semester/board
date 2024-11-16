package org.example.be.user;

public record MemberRegisterRequest(
	String username,
	String password
) {
}
