/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/29/2023
 * Time: 7:54 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.controller;

import com.example.ordermanagementapi.dto.JWTAuthResponse;
import com.example.ordermanagementapi.dto.LoginDto;
import com.example.ordermanagementapi.dto.SignUpDto;
import com.example.ordermanagementapi.entity.Customer;
import com.example.ordermanagementapi.entity.Role;
import com.example.ordermanagementapi.entity.User;
import com.example.ordermanagementapi.repository.CustomerRepository;
import com.example.ordermanagementapi.repository.RoleRepository;
import com.example.ordermanagementapi.repository.UserRepository;
import com.example.ordermanagementapi.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@Tag(name = "Auth controller exposes siginin and signup REST APIs")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    @Operation(summary = "Authenticate user", description = "REST API to Register or Signup user to Blog app")
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        if (StringUtils.isEmpty(loginDto.getUsernameOrEmail())) {
            System.out.println("Username or email cannot be empty");
            throw new IllegalArgumentException("Username or email cannot be empty");
        }
        System.out.println("get authentication");
        User user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or email"));

        System.out.println("invalid username or email");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), loginDto.getPassword()));
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        System.out.println("get authentication complete");

        System.out.println("SecurityContextHolder.getContext().setAuthentication(authentication);");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("ends:SecurityContextHolder.getContext().setAuthentication(authentication);");
        System.out.println("\nget token form tokenProvider");
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        token = "Bearer "+token;
        System.out.println("token:"+token);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @Operation(summary = "Register user", description = "REST API to Signin or Login user to Blog app")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser
            (@RequestBody SignUpDto signUpDto){
        System.out.println("check for username exists in a DB");
        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        System.out.println("check for email exists in DB");

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        System.out.println("create customer object");

        // create customer object
        Customer customer = new Customer();
        customer.setFirstName(signUpDto.getFirstName());
        customer.setLastName(signUpDto.getLastName());
        customer.setBornAt(signUpDto.getBornAt());

        Customer cc=customerRepository.save(customer);
        System.out.println("create customer object completed");

        System.out.println("create user object");
        // create user object
        User user = new User();
        user.setName(signUpDto.getFirstName()+" "+signUpDto.getLastName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setCustomerId(cc.getId());
        Role roles = roleRepository.findByName("ROLE_CUSTOMER").get();
        user.setRoles(Collections.singleton(roles));
        System.out.println("create user object completed");

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully",
                HttpStatus.OK);
    }
}