package Modal;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
    
    // Email: Địa chỉ email của bạn (người gửi)
    // Password: Mật khẩu ứng dụng (App Password) 16 ký tự của Gmail
    static final String from = "nhitruong043@gmail.com"; 
    static final String password = "dtfj mfwy pjxw rnks"; 

    public static boolean sendEmail(String to, String tieuDe, String noiDung) {
        // Khai báo các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Tạo Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        // Phiên làm việc
        Session session = Session.getInstance(props, auth);

        // Gửi email
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(new InternetAddress(from, "Công ty Sách (Hỗ trợ)"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(tieuDe, "UTF-8");
            msg.setSentDate(new Date());
            msg.setContent(noiDung, "text/html; charset=UTF-8");

            Transport.send(msg);
            System.out.println("Gửi email thành công");
            return true;
        } catch (Exception e) {
            System.out.println("Gửi email thất bại");
            e.printStackTrace();
            return false;
        }
    }
}