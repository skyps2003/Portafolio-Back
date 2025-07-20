package IHC.Portafolio.Service.Generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
         String subject = "Confirma tu cuenta";
        String confirmationUrl = "http://localhost:8081/usuario/confirmar?token=" + token;
        String body = "Hola,\n\nConfirma tu cuenta haciendo clic en este enlace:\n"
                + confirmationUrl + "\n\nGracias.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("terateniete@gmail.com");


        mailSender.send(message);
        System.out.println("Correo enviado a: " + toEmail + " con token: " + token);
    }

    public void sendTestEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Prueba de Email");
        message.setText("Este es un correo de prueba desde Spring Boot.");
        message.setFrom("211181@unamba.edu.pe");
        mailSender.send(message);
        System.out.println("Correo de prueba enviado a: " + toEmail);
    }
}
