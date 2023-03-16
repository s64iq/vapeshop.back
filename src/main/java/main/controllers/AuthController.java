package main.controllers;

import main.configs.jwt.JwtUtils;
import main.exception.TokenRefreshException;
import main.model.ERole;

import main.model.RefreshToken;
import main.model.Role;
import main.model.User;
import main.pojo.*;
import main.repository.RoleRepository;
import main.repository.UserRepository;
import main.service.AuthService;
import main.service.RefreshTokenService;
import main.service.UserDetailsImpl;
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
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
       if(userRepository.existsByUsername(loginRequest.getUsername())) {
          if(passwordEncoder.matches(loginRequest.getPassword(),userRepository.findByUsernameIgnoreCase(loginRequest.getUsername()).getPassword())) {
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
          } else {
              return ResponseEntity
                      .badRequest()
                      .body(new MessageResponse("Error: Password is incorrect"));
          }
       } else {
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("Error: User not found"));
       }
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
		if(authService.validateUsernameWithRegex(signupRequest.getUsername())) {
			if(authService.validateEmailWithRegex(signupRequest.getEmail())) {
				if(authService.validatePasswordWithRegex(signupRequest.getPassword())) {
					if (userRepository.existsByUsername(signupRequest.getUsername())) {
						return ResponseEntity
								.badRequest()
								.body(new MessageResponse("Error: Username is exist"));
					}

					if (userRepository.existsByEmail(signupRequest.getEmail())) {
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
					userRepository.save(user);
					return ResponseEntity.ok(new MessageResponse("User CREATED"));
				} else {
					return ResponseEntity
							.badRequest()
							.body(new MessageResponse(
									"Error: Invalid password!;" +
											"Password must contain:;" +
											"only ENG characters,;" +
											"at least 8 and most 24 characters,;" +
											"at least one number,;" +
											"at least one upper case character,;" +
											"at least one lower case character,;" +
											"at least one special character(!@#$%&*()-+=^),;" +
											"not one white space."));
				}
			} else {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Invalid email!"));
			}
		} else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse(
							"Error: Invalid username!;" +
									"Username must contain:;" +
									"only ENG characters,;" +
									"at least 3 and most 16 characters or number,;" +
									"not one upper case character,;" +
									"not one special character(!@#$%&*()-+=^)."));
		}
	}

	@GetMapping("/user")
	public ResponseEntity<?> verifyUser(@RequestHeader("Authorization") String token) {
		try {
			String tokenWithoutType = token.split(" ")[1];
			User user = userRepository.findByUsernameIgnoreCase(jwtUtils.getUserNameFromJwtToken(tokenWithoutType));

			return ResponseEntity.ok(new UserPropertiesResponse(
					user.getId(),
					user.getUsername(),
					user.getEmail(),
					user.getRoles()));
		} catch (Exception e) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: " + e.getMessage()));
		}
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
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

	@PutMapping("/password")
	public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody String password) {
		try {
			String tokenWithoutType = token.split(" ")[1];

			if(authService.validatePasswordWithRegex(password)) {
				if(passwordEncoder.matches(password, userRepository.findByUsernameIgnoreCase(jwtUtils.getUserNameFromJwtToken(tokenWithoutType)).getPassword())) {
					return ResponseEntity
							.badRequest()
							.body(new MessageResponse("This password already use!"));
				} else {
					userRepository.findByUsernameIgnoreCase(jwtUtils.getUserNameFromJwtToken(tokenWithoutType)).setPassword(passwordEncoder.encode(password));
					userRepository.save(userRepository.findByUsernameIgnoreCase(jwtUtils.getUserNameFromJwtToken(tokenWithoutType)));
					return ResponseEntity.ok(new MessageResponse("Password updated!"));
				}
			} else {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse(
								"Error: Invalid password!;" +
										"Password must contain:;" +
										"only ENG characters,;" +
										"at least 8 and most 24 characters,;" +
										"at least one number,;" +
										"at least one upper case character,;" +
										"at least one lower case character,;" +
										"at least one special character(!@#$%&*()-+=^),;" +
										"not one white space."));
			}
		} catch (Exception e) {
			return ResponseEntity
					.internalServerError()
					.body(new MessageResponse("Error: " + e.getMessage()));
		}
	}
}
