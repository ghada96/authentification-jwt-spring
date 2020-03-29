package com.ghada.authentificationjwt.controller;

import com.ghada.authentificationjwt.config.JwtTokenUtil;
import com.ghada.authentificationjwt.dto.UrlDTO;
import com.ghada.authentificationjwt.model.Auth;
import com.ghada.authentificationjwt.model.AuthToken;
import com.ghada.authentificationjwt.model.LoginUser;
import com.ghada.authentificationjwt.model.UserDTO;
import com.ghada.authentificationjwt.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.ghada.authentificationjwt.config.JwtTokenUtil.JWT_TOKEN_VALIDITY;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/token")
public class AuthentificationController {
    private static final String LOCKED_ACCOUNT_CONTACT_SUPPORT_MESSAGE = "Votre compte a été désactivé. Veuillez contacter l'équipe support.";


    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("userService")
    private AuthentificationService userService;



  /*  @Value("${byblos.api:http://localhost:8088/byblos}")
    private String apiByblos;*/

    @PostMapping(value = "/generate-token")
    public ResponseEntity<Object> register(@RequestBody LoginUser loginUser) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        }catch(BadCredentialsException e){
            String message = "";
            UserDTO userDTO =  userService.getUserByLogin(loginUser.getUsername());
            if(userDTO.getIsActive() != 0l){
                this.userService.incrementUserFailedLoginAttempts(userDTO.getId());
                Long failedAttemptsCount = this.userService.getUserFailedLoginAttempts(userDTO.getId());
                if(failedAttemptsCount == 5){
                    this.userService.resetUserFailedLoginAttempts(userDTO.getId());
                    this.userService.setUserInactif(userDTO.getId());
                    message = LOCKED_ACCOUNT_CONTACT_SUPPORT_MESSAGE;
                }else{
                    message = "Il vous reste " + (5 - failedAttemptsCount) + " tentative avant la désactivation de votre compte.";
                }
            }else{
                message = LOCKED_ACCOUNT_CONTACT_SUPPORT_MESSAGE;
            }
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }


        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDTO user = userService.getUserByLoginWithoutPwd(loginUser.getUsername());

        if (user.getIsActive() == 1) {
            user.setSpacesList(userService.getAuthorisedList(user.getId()));
            user.setSpaceId(loginUser.getSpace());
            this.userService.resetUserFailedLoginAttempts(user.getId());
            final String token = jwtTokenUtil.generateToken(user);
            final String expiresIn = String.valueOf(System.currentTimeMillis() + (JWT_TOKEN_VALIDITY * 1000));

            return ResponseEntity.ok(new Auth(new AuthToken(token), user, expiresIn));
        } else {
            return new ResponseEntity<>(LOCKED_ACCOUNT_CONTACT_SUPPORT_MESSAGE, HttpStatus.UNAUTHORIZED);
        }

    }
    @PostMapping(value = "/generate-token2")
    public ResponseEntity<Auth> reGenerateToken(@RequestBody LoginUser loginUser) {

        final UserDTO user = userService.getUserByLoginWithoutPwd(loginUser.getUsername());
        user.setSpaceId(loginUser.getSpace());
        if (user.getIsActive() == 1) {
            user.setAuthorities(userService.getAuthority(user.getId(), loginUser.getSpace()));
            user.setSpacesList(userService.getAuthorisedList(user.getId()));
            final String token = jwtTokenUtil.generateToken(user);
            final String expiresIn = String
                    .valueOf(System.currentTimeMillis() + (JWT_TOKEN_VALIDITY * 1000));
            return ResponseEntity.ok(new Auth(new AuthToken(token), user, expiresIn));
        } else {
            return null;
        }
    }

    @PostMapping(value = "/getCredentialsByToken")
    public ResponseEntity<Auth> getCredentialsByToken(@RequestParam String token)  {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        final UserDTO user = userService.getUserByLoginWithoutPwd(username);
        user.setSpaceId(jwtTokenUtil.getSpaceIdFromToken(token));
        user.setSpacesList(jwtTokenUtil.getSpaceListFromToken(token));
        user.setAuthorities(userService.getAuthority(user.getId(), user.getSpaceId()));
        final String expiresIn = String.valueOf(jwtTokenUtil.getExpirationDateFromToken(token).getTime());
        return ResponseEntity.ok(new Auth(new AuthToken(token), user, expiresIn));

    }

    @PostMapping(value = "/validateToken")
    public boolean isExpired(@RequestParam String token, @RequestParam String username) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        return jwtTokenUtil.validateToken(token, userDetails);
    }



    @GetMapping(value = "getNbre/{id}")
    @ResponseBody
    public Long getNbre(@PathVariable("id") Long id)  {
        return userService.getUserFailedLoginAttempts(id);
    }


  /*  @PostMapping(value = "/logoutURI")
    @ResponseBody
    public UrlDTO logout() {
        return new UrlDTO( apiByblos+"/j_spring_security_logout");
    }*/
}
