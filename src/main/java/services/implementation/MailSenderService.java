package services.implementation;

import domain.useraccounts.UserAccount;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import services.interfaces.IMailSenderService;

import javax.mail.internet.MimeMessage;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Krzysiu on 2014-06-13.
 */
public class MailSenderService implements IMailSenderService {

    public enum MailType {
        ACCOUNT_ACTIVATION("Account Activation", "AccountActivationTemplate.vm");

        private final String subject;
        private final String template;

        MailType(String subject, String template) {
            this.subject = subject;
            this.template = template;
        }

        public String getSubject() {
            return subject;
        }

        public String getTemplate() {
            return template;
        }
    }

    public class MailSenderThread extends Thread {

        private Queue<MimeMessagePreparator> mailsToSend;

        public MailSenderThread() {
            this.mailsToSend = new ConcurrentLinkedQueue<>();
        }

        @Override
        public void run() {
            while(true) {
                if(!mailsToSend.isEmpty()) {
                    javaMailSender.send(mailsToSend.poll());
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void addNewMailToSend(String to, String subject, String text) {
            mailsToSend.add(new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    try {
                        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                        mimeMessageHelper.setFrom(from);
                        mimeMessageHelper.setTo("kkrzysiu@poczta.onet.pl"); //to
                        mimeMessageHelper.setSubject(subject);
                        mimeMessageHelper.setText("<img src=\\\"cid:myLogo\\\"/>", true); //text
                        mimeMessageHelper.addInline("myLogo", new ClassPathResource("images/logo.png"));
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    private String from;
    private JavaMailSender javaMailSender;
    private MailSenderThread mailSenderThread;

    public MailSenderService(String from, JavaMailSender javaMailSender) {
        this.from = from;
        this.javaMailSender = javaMailSender;
        this.mailSenderThread = new MailSenderThread();
        mailSenderThread.start();
    }

    @Override
    public void sendAccountActivationMessage(UserAccount userAccount) {
        mailSenderThread.addNewMailToSend("kkrzysiu@poczta.onet.pl", "New Message", "Jestem tutaj ;)");
    }
}
