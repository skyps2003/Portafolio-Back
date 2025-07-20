package IHC.Portafolio.Service.Generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) throws MessagingException {
        String subject = "Confirma tu cuenta";
        String confirmationUrl = "http://localhost:8081/usuario/confirmar?token=" + token;

        String htmlContent = "<html><body>" +
                "<h2 style='color:#2E86C1;'>¡Hola!</h2>" +
                "<p>Gracias por registrarte. Para confirmar tu cuenta, haz clic en el siguiente botón:</p>" +
                "<a href=\"" + confirmationUrl + "\" " +
                "style='display:inline-block;padding:10px 20px;background-color:#28a745;color:#ffffff;text-decoration:none;border-radius:5px;'>Confirmar cuenta</a>" +
                "<p style='margin-top:20px;'>Si no te registraste, puedes ignorar este correo.</p>" +
                "<br><p style='font-size:12px;color:#888;'>© TuEmpresa</p>" +
                "</body></html>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // <- true = HTML
        helper.setFrom("terateniete@gmail.com");

        mailSender.send(message);
        System.out.println("Correo HTML enviado a: " + toEmail + " con token: " + token);
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
