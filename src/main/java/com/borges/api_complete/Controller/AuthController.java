package com.borges.api_complete.Controller;


import com.borges.api_complete.Model.DTO.CredDto;
import com.borges.api_complete.Model.UserModel;
import com.borges.api_complete.Security.JWTFilter;
import com.borges.api_complete.Security.JWTUtil;
import com.borges.api_complete.Security.UserSS;
import com.borges.api_complete.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody CredDto authRequest) throws Exception {
        Object s = userService.loginUser(authRequest);
     //   System.out.println(s);
        if (s == null) {
            return "401";
        } else {

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
                );

            } catch (Exception ex) {
                new JWTAuthenticationFailureHandler();
            }

            return "Bearer "+ jwtUtil.generateToken(authRequest.getEmail());
        }
    }
    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response,@RequestBody String email) throws ExecutionException, InterruptedException {
        UserModel user= userService.findUserByEmail(email);
        String token = jwtUtil.generateToken(user.getEmail());
        response.addHeader("Authorization", "Bearer "+ token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }

    }
}
