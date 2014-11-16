package services.implementation;

import builders.PathBuilder;
import domain.useraccounts.UserAccount;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import services.interfaces.IMailSenderService;
import tools.ConfigurationTools;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Krzysiu on 2014-06-13.
 */
public class MailSenderSpringService implements IMailSenderService {

    public enum MailType {
        ACCOUNT_ACTIVATION("Account Activation", "AccountCreationTemplate.vm");

        private final String subject;
        private final String templateName;

        MailType(String subject, String template) {
            this.subject = subject;
            this.templateName = template;
        }

        public String getSubject() {
            return subject;
        }

        public String getTemplateName() {
            return templateName;
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
                        mimeMessageHelper.setTo(to); //to
                        mimeMessageHelper.setSubject(subject);
                        mimeMessageHelper.setText(text, true); //text
                        mimeMessageHelper.addInline("logo", new ClassPathResource("images/logo.png"));
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
    private VelocityEngine velocityEngine;

    public MailSenderSpringService(String from, JavaMailSender javaMailSender, VelocityEngine velocityEngine) {
        this.from = from;
        this.javaMailSender = javaMailSender;
        this.velocityEngine = velocityEngine;
        this.mailSenderThread = new MailSenderThread();
        mailSenderThread.start();
    }

    @Override
    public void sendAccountActivationMessage(UserAccount userAccount) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("mailTitle", MailType.ACCOUNT_ACTIVATION.getSubject());
        parameters.put("mainAddress", ConfigurationTools.MAIN_PAGE);
        parameters.put("activationLink", PathBuilder.getUserAccountActivationPath(userAccount.getId()));
        parameters.put("emailSign", ConfigurationTools.PROJECT_NAME);
        parameters.put("userAccount", userAccount);
        String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, MailType.ACCOUNT_ACTIVATION.getTemplateName(), "UTF-8", parameters);
        mailSenderThread.addNewMailToSend("kkrzysiu@poczta.onet.pl", MailType.ACCOUNT_ACTIVATION.getSubject(), body);
    }

}
