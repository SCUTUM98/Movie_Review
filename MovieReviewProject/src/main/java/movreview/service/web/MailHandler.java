package movreview.service.web;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailHandler {
    private static final int port = 465;  // 대부분 465 포트를 사용
    private String host;
    private String user;
    private String tail;
    private String password; 

    // Getters and Setters
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getTail() { return tail; }
    public void setTail(String tail) { this.tail = tail; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    private Properties props = System.getProperties();

    private boolean setEnv() {
        props.put("mail.smtp.host", host);  
        props.put("mail.smtp.port", port);  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.ssl.enable", "true");  
        props.put("mail.smtp.ssl.trust", host);  
        return true;
    }

    // HTML 콘텐츠를 지원하는 메일 전송
    public boolean sendMail(String receiver, String title, String text, String mimeType) throws Exception {
        setEnv();
        Message msg = sendingHead();
        sendingBody(msg, receiver, title, text, mimeType);
        Transport.send(msg);    
        return true;
    }

    private Message sendingHead() {
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            String un = user;
            String pw = password;
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(un, pw);
            }
        });
        session.setDebug(true);  
        Message msg = new MimeMessage(session);
        return msg;
    }

    private void sendingBody(Message msg, String receiver, String title, String text, String mimeType) throws Exception {
        msg.setFrom(new InternetAddress(user + tail)); // 발신자 셋팅
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver)); // 수신자셋팅  
        msg.setSubject(title); // 제목셋팅  

        // MIME 타입에 따라 본문 설정
        if (mimeType.equals("text/html")) {
            // HTML 형식의 이메일
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(text, "text/html; charset=UTF-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            msg.setContent(multipart);
        } else {
            // 일반 텍스트 형식
            msg.setText(text);
        }
    }
}
