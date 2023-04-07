/******************************************************************************
 * This piece of work is to enhance mailbox project functionality.            *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Mailer.java                                                     *
 * Created:   22/03/2023, 13:39                                               *
 * Modified:  22/03/2023, 13:39                                               *
 *                                                                            *
 * Copyright (c)  2023.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

package com.aerosimo.ominet.com;

import org.apache.logging.log4j.*;
import org.apache.commons.lang3.RandomStringUtils;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import com.aerosimo.ominet.core.Connect;
import java.util.Date;
import java.io.IOException;
public class Mailer {

    private static final Logger log = LogManager.getLogger(Mailer.class.getName());
    static String response;
    static int RANDOM_STRING_LENGTH = 40;
    static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyz1234567890";

    public static String SendEmail(String emailAddress, String emailSubject, String emailMessage, String emailFiles, String emailFrom) {
        response = RandomStringUtils.random(RANDOM_STRING_LENGTH, CHAR_LIST);
        String[] attachFiles;
        attachFiles = emailFiles.split(",");
        Message msg;
        msg = new MimeMessage(Connect.email());
        if (emailFiles.isEmpty()) {
            log.info("Sending email without attachment");
            try {
                msg.setFrom(new InternetAddress(emailFrom));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
                msg.setSubject(emailSubject);
                msg.setSentDate(new Date());
                msg.setContent(emailMessage, "text/html");
                Transport.send(msg);
            } catch (MessagingException err) {
                response = "Fail";
                log.error("Email Service " + Mailer.class.getName() + " failed with email adaptor error - " + err);
            }
        } else {
            log.info("Sending email with attachment");
            try {
                msg.setFrom(new InternetAddress(emailFrom));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
                msg.setSubject(emailSubject);
                msg.setSentDate(new Date());
                MimeBodyPart messageBodyPart;
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(emailMessage, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                for (String filePath : attachFiles) {
                    MimeBodyPart attachPart;
                    attachPart = new MimeBodyPart();
                    try {
                        attachPart.attachFile(filePath);
                    } catch (IOException err) {
                        response = "Fail";
                        log.error("Email Service " + Mailer.class.getName() + " failed with email adaptor error - " + err);
                    }
                    multipart.addBodyPart(attachPart);
                }
                msg.setContent(multipart);
                Transport.send(msg);
            } catch (MessagingException err) {
                response = "Fail";
                log.error("Email Service " + Mailer.class.getName() + " failed with email adaptor error - " + err);
            }
        }
        return response;
    }
}