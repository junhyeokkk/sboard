package com.sboard.Service;

import com.sboard.email.EmailMessage;
import com.sboard.dto.UserDTO;
import com.sboard.entity.User;
import com.sboard.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Optional;
import java.util.Random;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final SpringTemplateEngine templateEngine;
    private final PasswordEncoder passwordEncoder;


    //로그인 검증 메서드
    public UserDTO selectUser(String uid) {
        Optional<User> optUser = userRepository.findById(uid);

        if(optUser.isPresent()) {
            User user = optUser.get();
            return user.toDTO();
        }
        return null;
    }

    //회원가입 메서드
    public void insertUser(UserDTO userDTO) {
        String encoded= passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        //회원가입
        userRepository.save(userDTO.toEntity());
    }

    // 인증번호 및 임시 비밀번호 생성 메서드
    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    // thymeleaf를 통한 html 적용
    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }

    // 메일보내기
    public String sendMail(EmailMessage emailMessage, String type) {
        String authNum = createCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            log.info("suc");
            return authNum;

        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }

    // 중복 검사 메서드
    public UserDTO selectUser(String type, UserDTO userDTO) {
        if(type.equals("uid")) {
            Optional<User> opt = userRepository.findById(userDTO.getUid());

            if (opt.isPresent()) {
                User user = opt.get();
                return user.toDTO();
            }
        }
        else if(type.equals("nick")) {
            Optional<User> opt = userRepository.findByNick(userDTO.getNick());

            if(opt.isPresent()) {
                User user = opt.get();
                return user.toDTO();
            }
        }
        else if(type.equals("email")) {
            Optional<User> opt = userRepository.findByEmail(userDTO.getEmail());

            if(opt.isPresent()) {
                User user = opt.get();
                return user.toDTO();
            }
        }
        else if(type.equals("hp")) {
            Optional<User> opt = userRepository.findByHp(userDTO.getHp());

            if(opt.isPresent()) {
                User user = opt.get();
                return user.toDTO();
            }
        }
        return null;
    }
}
