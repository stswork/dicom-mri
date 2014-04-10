package actors.mail;

import akka.actor.UntypedActor;
import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;
import models.actor.mailer.Mail;
import play.Play;

public class MailSenderActor extends UntypedActor {

    private final static String FEEDBACK_EMAIL_SUBJECT = "Telestroke : Please review MRI Scan";
    private final static String TELESTROKE_FEEDBACK_FROM = "Telestroke <info@telestroke.com>";


    @Override
    public void onReceive(Object message) throws Exception {

        try {
            Mail mail = (Mail) message;
            String body = views.html.email.email_template.render("Patient Notification", mail).body();
            MailerAPI mailerAPI = Play.application().plugin(MailerPlugin.class).email();
            mailerAPI.setSubject(FEEDBACK_EMAIL_SUBJECT);
            mailerAPI.setFrom(TELESTROKE_FEEDBACK_FROM);
            mailerAPI.setRecipient(mail.getdEmail());
            mailerAPI.send("",body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
