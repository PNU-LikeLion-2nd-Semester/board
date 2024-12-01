package org.example.be.user;

public record LoginRequest(
	String username,
	String password
) {
}
