package com.sboard.controller.User;

import com.sboard.Service.UserService;
import com.sboard.email.EmailMessage;
import com.sboard.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class RegisterController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @ResponseBody
    @GetMapping(value = "/user/checkUser")
    public ResponseEntity checkUser(@RequestParam("type") String type, @RequestParam("value") String value , HttpSession session) {

        if(type.equals("uid")) {
            UserDTO userDTO = UserDTO.builder()
                    .uid(value)
                    .build();
            UserDTO user = userService.selectUser(type,userDTO);
            int result = 1;
            if (user != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        else if(type.equals("nick")) {
            UserDTO userDTO = UserDTO.builder()
                    .nick(value)
                    .build();
            UserDTO user = userService.selectUser(type,userDTO);
            log.info(user);
            int result = 1;
            if (user != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        else if(type.equals("email")) {

            UserDTO userDTO = UserDTO.builder()
                    .email(value)
                    .build();
            UserDTO user = userService.selectUser(type,userDTO);

            int result = 1;
            if (user != null) {
                log.info("eeeeeeeeeeeeewwweeeeeeeeeeeee" + user);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
            }
            else {
                EmailMessage emailMessage = EmailMessage.builder()
                        .to(userDTO.getEmail())
                        .subject("[sboard] 이메일 인증을 위한 인증 코드 발송")
                        .build();
                String code = userService.sendMail(emailMessage, "/user/email.html");

                session.setAttribute("code", code);

                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }
        else if(type.equals("hp")) {
            UserDTO userDTO = UserDTO.builder()
                    .hp(value)
                    .build();
            UserDTO user = userService.selectUser(type,userDTO);
            log.info(user);
            int result = 1;
            if (user != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return null;
    }

    @PostMapping(value = "/user/checkUser")
    public ResponseEntity CheckEmail(@RequestBody Map<String, String> requestBody, HttpSession session){

        String checkcode = (String) session.getAttribute("code");
        String code = requestBody.get("code");

        log.info("ccccccccccc" + checkcode);
        log.info("ooooooooooooo" + code);
        int result = 1;
        if(checkcode.equals(code)){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        else {
            result = 0;
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
    }

    @PostMapping(value = "/user/register")
    public String registerUser(@ModelAttribute UserDTO userDTO){
        log.info("aaaaaaaaaaaaaa");
        userService.insertUser(userDTO);
        return "redirect:/user/login";
    }
}
