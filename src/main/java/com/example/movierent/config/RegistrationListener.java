package com.example.movierent.config;

import com.example.movierent.model.User;
import com.example.movierent.model.dto.OnVerificationUserEvent;
import com.example.movierent.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnVerificationUserEvent> {

    @Autowired
    @Qualifier("userService")
    private UserService service;

    @Autowired
    private JavaMailSender mailSender;


    /** Use the same method for 1- registration and 2-recovery password
     * @param event contain the type value
     **/
    @SneakyThrows
    @Override
    public void onApplicationEvent(@NonNull OnVerificationUserEvent event) {
        if (event.getType() == 1){
            this.sendConfirmRegistration(event);
        }else if (event.getType() == 2){
            this.sendRecoveryPassword(event);
        }

    }

    // Generate a random string token and save associated to user and type 1 and send email
    private void sendConfirmRegistration(OnVerificationUserEvent event) throws MessagingException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        String subject = "Please verify your registration";
        String confirmationUrl
                = event.getAppUrl() + "/confirm?token=" + token;
        String message = "<p> Dear "+user.getFirstName() +",</p>";
        message += "<p>Please click the link below to verify to your registration</p><br>";
        message += "<a href=\"" + confirmationUrl + "\" >VERIFY</a>";
        message += "<br><p>Thank you!</p>";

        sendMail(user.getEmail(), subject, message);

    }

    // Generate a random string token and save associated to user and type 2 and send email
    private void sendRecoveryPassword(OnVerificationUserEvent event) throws MessagingException {
        User userRecovery = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createRecoveryToken(userRecovery, token);
        String subject = "Password Recovery Request";
        String confirmationUrl
                = event.getAppUrl() + "/recovery?recovery_token=" + token;

        String message = "<p> Hello "+userRecovery.getFirstName() +",</p>";
        message += "<p>We received a request to reset your user's password.</p><br>";
        message += "<a href=\"" + confirmationUrl + "\" >Click here to reset your password.</a>";
        message += "<br><p>Thank you!</p>";

        sendMail(userRecovery.getEmail(), subject, message);
    }

    /** Method for send custom email
     * @param email email to send
     * @param subject the subject of the mail
     * @param message body of mail in html string
     **/
    void sendMail(String email, String subject, String message) throws MessagingException {
        String senderName = "MovieRental";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setFrom(senderName);
        helper.setText(message, true);
        mailSender.send(mimeMessage);
    }

}
