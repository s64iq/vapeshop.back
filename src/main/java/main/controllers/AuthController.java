package main.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import main.configs.jwt.JwtUtils;
import main.exception.TokenRefreshException;
import main.model.ERole;

import main.model.RefreshToken;
import main.model.Role;
import main.model.User;
import main.pojo.*;
import main.repository.RoleRepository;
import main.repository.UserRepository;
import main.service.RefreshTokenService;
import main.service.UserDetailsImpl;
import main.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRespository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	/*@PostMapping("/signin")
	public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles));
	}*/


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		return ResponseEntity.ok(new SignupResponse(jwt,
				refreshToken.getToken(),
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
		
		if (userRespository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is exist"));
		}
		
		if (userRespository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Email is exist"));
		}
		
		User user = new User(
			signupRequest.getUsername(),
			signupRequest.getEmail(),
			passwordEncoder.encode(signupRequest.getPassword())
		);
		
		Set<String> reqRoles = signupRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		
		if (reqRoles == null) {
			Role userRole = roleRepository
				.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
			roles.add(userRole);
		} else {
			reqRoles.forEach(r -> {
				switch (r) {
				case "admin":
					Role adminRole = roleRepository
						.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
					roles.add(adminRole);
					
					break;
				case "mod":
					Role modRole = roleRepository
						.findByName(ERole.ROLE_MODERATOR)
						.orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
					roles.add(modRole);
					
					break;

				default:
					Role userRole = roleRepository
						.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRespository.save(user);
		return ResponseEntity.ok(new MessageResponse("User CREATED"));
	}

	/*@DeleteMapping
	public void deleteCookie(Cookie cookie) {
		return cookie();
	}*/

	@GetMapping("/user")
	public String verifyUser(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.split(" ")[1];
		return jwtUtils.getUserNameFromJwtToken(token);
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@RequestBody RefreshTokenRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				}).orElseThrow(() ->
						new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}

	/*@GetMapping("/validatetoken")
	public void validateToken(@RequestHeader("Authorization") String test) {
		String token = test.split(" ")[1];
		*//*((Claims) Jwts.parser().parse(token).getBody()).getExpiration();*//*
		System.out.println(((Claims) Jwts.parser().parse(token).getBody()).getExpiration());
		*//*String username = jwtUtils.getUserNameFromJwtToken(token);
		userRespository.findByUsername(username);*//*
	}*/
}
