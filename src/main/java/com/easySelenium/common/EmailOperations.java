package com.easySelenium.common;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.easySelenium.generic.Log;
import com.easySelenium.generic.Utility;
import com.marketshare.core.constants.CommonConstants;
import com.easySelenium.generic.FileOperations;
import com.easySelenium.common.CommonConstant;

/**
 * TODO Put here a description of what this class does.
 *
 * @author ssamaji.
 *         Created Jun 23, 2018.
 */
public class EmailOperations {

	private static boolean m_isMailEnabled = false;
	private static String m_bcc;
	private static String m_cc;
	private static String m_content;
	private static String m_from;
	private static Properties m_mailDetails;
	private static String m_password;
	private static Properties m_sessionDetails;
	private static String m_subject;
	private static String m_to;
	private static String m_userName;
	
	public static boolean send(String body, String reportPath) {
		try {
			String clientName = FileOperations.getPropertyValue("app.properties", "clientname");
			String version = FileOperations.getPropertyValue("app.properties", "version");
			String product = FileOperations.getPropertyValue("app.properties", "product");
			m_subject = FileOperations.getPropertyValue("mail.properties", CommonConstant.MAIL_SUBJECT);
			try {
				if (version == null) {
					m_subject = String.format(m_subject, clientName);
				} else {
					String subject[] = m_subject.split(" ");
					m_subject = String.format("%s - %s - %s %s Automation Report", product, version, clientName,
							subject[3]);
				}
			} catch (Exception e) {
				m_subject = String.format(m_subject, clientName);
			}
			return send(m_subject, body, reportPath);

		} catch (Exception e) {
			Log.info(e.toString());
		}
		Log.info("Mail option is not enabled.");
		return false;
	}
	//
	private static void setMailDetails() {
		try {
			m_mailDetails = FileOperations.getProperties("mail.properties");

			m_isMailEnabled = Boolean.parseBoolean(m_mailDetails.getProperty(CommonConstant.MAIL_ENABLE));
			setMailDetails(m_mailDetails.getProperty(CommonConstant.MAIL_FROM),
					m_mailDetails.getProperty(CommonConstant.MAIL_TO),
					m_mailDetails.getProperty(CommonConstant.MAIL_CC),
					m_mailDetails.getProperty(CommonConstant.MAIL_SUBJECT),
					m_mailDetails.getProperty(CommonConstant.MAIL_CONTENT));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getDecryptValue(String valueToDecrypt) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(CommonConstants.ENCRYPTION_KEY);
		return encryptor.decrypt(valueToDecrypt);
	}
	//
	private static void setMailDetails(String from, String to, String cc, String subject, String content) {
		m_userName = CommonConstant.MAIL_USER_NAME;
		m_password = getDecryptValue(CommonConstant.MAIL_PASSWORD);
		m_from = from;
		m_to = to;
		m_cc = cc;
		m_subject = subject;
		m_content = content;
		m_bcc = "";
	}
	//
	private static void setSessionDetails() {
		Properties props = new Properties();
		props.put("mail.smtp.host", CommonConstant.MAIL_HOST);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", CommonConstant.MAIL_PORT);
		props.put("mail.smtp.starttls.enable", "true");
		m_sessionDetails = props;
	}
	//
	private static Session createMailSession() {
		Session session = null;
		try {
			session = Session.getDefaultInstance(m_sessionDetails, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(m_userName, m_password);
				}
			});
		} catch (Exception e) {
			Log.info(e.toString());
		}
		return session;
	}
	//
	public static boolean send(String subject, String body, String reportPath) {
		setMailDetails();

		if (m_isMailEnabled) {
			setSessionDetails();
			Session session = createMailSession();
			try {
				if (session == null) {
					return false;
				}
				String clientName = FileOperations.getPropertyValue("app.properties", "clientname");
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(m_from));

				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(m_to));

				if (!m_cc.toLowerCase().trim().equals("none")) {
					Log.info("Add cc list");
					Log.info(m_cc);
					message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(m_cc));
				}
				message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(m_bcc));
				m_subject = subject;
				message.setSubject(m_subject);
				String systemName = System.getenv("COMPUTERNAME") == null
						?System.getenv("HOSTNAME") : System.getenv("COMPUTERNAME");

				String mainContent = null;
				if (body.startsWith("ChromeDriver exception")) {
					mainContent = body;
				} else {
					mainContent = body.replace("%%%SUMMARYNOTE%%%",	String.format(m_content, clientName, systemName,
											Utility.getFormatedDate("EEEEE dd MMMMM yyyy 'at' hh:mm a zzz")))
												.replace("%%%SUBJECTHEADER%%%", m_subject);
				}

				MimeBodyPart bodyPart = new MimeBodyPart();
				bodyPart.setText(mainContent, "utf-8", "html");
				String fileName = reportPath;
				String reportName = String.format("%s_%s", clientName,fileName.substring(fileName.lastIndexOf("/") + 1));
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(fileName);
				mimeBodyPart.setDataHandler(new DataHandler(source));
				mimeBodyPart.setFileName(reportName);

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(bodyPart);
				multipart.addBodyPart(mimeBodyPart);

				message.setContent(multipart);
				Transport.send(message);

				Log.info("Mail has sent successfully with report attachment.");
				return true;
			} catch (Exception e) {
				Log.info(e.toString());
				throw new RuntimeException(e);
			}
		}
		Log.info("Mail option is not enabled.");
		return false;
	}
	//	
	public static boolean send(String toList, String ccList, String emailSubject, String emailContent) {
		return send(toList, ccList, emailSubject, emailContent, null);
	}
	//
	public static boolean send(String toList, String ccList, String emailSubject, String emailContent,
				String fileAttachmentPath) {
		try {
			setSessionDetails();
			setMailDetails(CommonConstant.MAIL_USER_NAME, toList, ccList, emailSubject, emailContent);

			Session session = createMailSession();
			if (session == null) {
				Log.info("Faile while creating mail session.");
				return false;
			}

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(m_from));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(m_to));
			if (!m_cc.toLowerCase().trim().equals("none")) {
				Log.info("Add cc list");
				Log.info(m_cc);
				message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(m_cc));
			}
			message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(m_bcc));
			message.setSubject(m_subject);
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(m_content, "utf-8", "html");

			MimeBodyPart mimeBodyPart = null;
			if (fileAttachmentPath != null && fileAttachmentPath.length() > 0) {
				fileAttachmentPath = fileAttachmentPath.replace("\\", "/");
				mimeBodyPart = new MimeBodyPart();
				String fileName = fileAttachmentPath;
				String reportName = fileName.substring(fileName.lastIndexOf("/") + 1);
				DataSource source = new FileDataSource(fileName);
				mimeBodyPart.setDataHandler(new DataHandler(source));
				mimeBodyPart.setFileName(reportName);
			}
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			if (mimeBodyPart != null) {
				multipart.addBodyPart(mimeBodyPart);
			}
			message.setContent(multipart);
			Transport.send(message);

			Log.info("Mail has sent successfully.");
			return true;
			} catch (Exception e) {
				Log.info(e.toString());
			}
			Log.info("Mail has not sent. Failed.");
			return false;
		}
}
