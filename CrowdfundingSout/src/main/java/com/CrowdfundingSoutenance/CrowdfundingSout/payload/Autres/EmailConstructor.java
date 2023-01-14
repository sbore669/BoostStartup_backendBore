package com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Utilisateurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Component
public class EmailConstructor {

	@Autowired
	private Environment env;

	@Autowired
	private TemplateEngine templateEngine;

	public MimeMessagePreparator constructNewUserEmail(Utilisateurs utilisateurs) {
		Context context = new Context();
		context.setVariable("utilisateurs", utilisateurs);
		String text = templateEngine.process("newUserEmailTemplate", context);
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setPriority(1);
				email.setTo(utilisateurs.getEmail());
				email.setSubject("Bienvenu sur crowdfunding");
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};
		return messagePreparator;
	}
	public MimeMessagePreparator constructValidationStartups(Utilisateurs utilisateurs) {
		Context context = new Context();
		context.setVariable("utilisateurs", utilisateurs);
		String text = templateEngine.process("ValidationStartups", context);
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setPriority(1);
				email.setTo(utilisateurs.getEmail());
				email.setSubject("Bienvenu sur MaliStartups-Invest");
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};
		return messagePreparator;
	}

	public MimeMessagePreparator constructResetPasswordEmail(Utilisateurs utilisateurs, String password) {
		Context context = new Context();
		context.setVariable("utilisateurs", utilisateurs);
		context.setVariable("password", password);
		String text = templateEngine.process("resetPasswordEmailTemplate", context);
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setPriority(1);
				email.setTo(utilisateurs.getEmail());
				email.setSubject("Nouveau mots de passe - Malitourist");
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};
		return messagePreparator;
	}

	public MimeMessagePreparator constructUpdateUserProfileEmail(Utilisateurs utilisateurs) {
		Context context = new Context();
		context.setVariable("utilisateurs", utilisateurs);
		String text = templateEngine.process("updateUserProfileEmailTemplate", context);
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setPriority(1);
				email.setTo(utilisateurs.getEmail());
				email.setSubject("Profile Modifié - Malitourist");
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};
		return messagePreparator;
	}
}
