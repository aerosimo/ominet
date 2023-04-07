/******************************************************************************
 * This piece of work is to enhance mailbox project functionality.            *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      PostmasterWS.java                                               *
 * Created:   22/03/2023, 14:50                                               *
 * Modified:  22/03/2023, 14:50                                               *
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

package com.aerosimo.ominet.api.soap;

import com.aerosimo.ominet.com.Mailer;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebService(name = "mailbox", serviceName = "postmaster",
        portName = "sendmail", targetNamespace = "https://aerosimo.com/api/ws/mailbox/v1")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class PostmasterWS {

    private static final Logger log = LogManager.getLogger(PostmasterWS.class.getName());

    @WebMethod(operationName = "SendEmail")
    @WebResult(name = "Status", partName = "SendEmailResponse")
    public String sendMail(@XmlElement(required = true)@WebParam(name = "emailAddress", partName = "SendEmailRequest") String emailAddress,
                           @XmlElement(required = true)@WebParam(name = "emailSubject", partName = "SendEmailRequest") String emailSubject,
                           @XmlElement(required = true)@WebParam(name = "emailMessage", partName = "SendEmailRequest") String emailMessage,
                           @XmlElement(required = true)@WebParam(name = "emailFrom", partName = "SendEmailRequest") String emailFrom,
                           @WebParam(name = "emailFiles", partName = "SendEmailRequest") String emailFiles) {

        String Response;
        if (emailAddress.isEmpty()) {
            Response = "Schema Validation failed because emailAddress is a required field or not valid";
        }
        else if (emailSubject.isEmpty()) {
            Response = "Schema Validation failed because emailSubject is a required field";
        }
        else if (emailMessage.isEmpty()) {
            Response = "Schema Validation failed because emailMessage is a required field";
        }
        else if (emailFrom.isEmpty()) {
            Response = "Schema Validation failed because emailFrom is a required field";
        }
        else {
            Response = Mailer.SendEmail(emailAddress,emailSubject,emailMessage,emailFiles,emailFrom);
        }
        return Response;
    }
}