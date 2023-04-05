/******************************************************************************
 * This piece of work is to enhance profile-manager project functionality.    *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Postmaster.java                                                 *
 * Created:   24/02/2023, 09:49                                               *
 * Modified:  24/02/2023, 09:49                                               *
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

package com.aerosimo.ominet.app;

import com.aerosimo.ominet.core.Connect;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Date;

public class Postmaster {

    private static final Logger log = LogManager.getLogger(Postmaster.class.getName());

    static String response;

    public static String SendEmail(String emailAddress, String emailSubject, String emailMessage, String emailFiles, String emailFrom) {
        response = "Sent Successfully";
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
                response = "Sent Unsuccessfully";
                log.error("Email Notification Service " + Postmaster.class.getName() + " failed with email adaptor error - " + err);
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
                        response = "Sent Unsuccessfully";
                        log.error("Email Notification Service " + Postmaster.class.getName() + " failed with email adaptor error - " + err);
                    }
                    multipart.addBodyPart(attachPart);
                }
                msg.setContent(multipart);
                Transport.send(msg);
            } catch (MessagingException err) {
                response = "Sent Unsuccessfully";
                log.error("Email Notification Service " + Postmaster.class.getName() + " failed with email adaptor error - " + err);
            }
        }
        return response;
    }
}