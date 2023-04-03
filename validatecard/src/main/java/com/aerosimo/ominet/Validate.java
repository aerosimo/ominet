/**************************************************************************************************
 * This piece of work is to enhance validatecard project functionality.                           *
 *                                                                                                *
 * Author:    eomisore                                                                            *
 * File:      Validate.java                                                                       *
 * Created:   03/02/2023, 21:09                                                                   *
 * Modified:  03/02/2023, 21:09                                                                   *
 *                                                                                                *
 * Copyright (c)  2023.  Aerosimo Ltd                                                             *
 *                                                                                                *
 * Permission is hereby granted, free of charge, to any person obtaining a                        *
 * copy of this software and associated documentation files (the "Software"),                     *
 * to deal in the Software without restriction, including without limitation                      *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,                       *
 * and/or sell copies of the Software, and to permit persons to whom the                          *
 * Software is furnished to do so, subject to the following conditions:                           *
 *                                                                                                *
 * The above copyright notice and this permission notice shall be included                        *
 * in all copies or substantial portions of the Software.                                         *
 *                                                                                                *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,                                *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES                                *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                                       *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                                     *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,                                   *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING                                   *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                                     *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                                             *
 *                                                                                                *
 **************************************************************************************************/

package com.aerosimo.ominet;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebService(name = "CardValidator", serviceName = "Validate",
        portName = "ValidateCard", targetNamespace = "https://aerosimo.com/api/ws/cardvalidator/v1")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class Validate {

    private static final Logger log = LogManager.getLogger(Validate.class.getName());

    @WebMethod(operationName = "validateCard")
    @WebResult(name = "cardNetwork", partName = "validateCardResponse")
    public String validateCard(@XmlElement(required = true) @WebParam(name = "CardLongNumber",
            partName = "validateCardRequest") String CardLongNumber) {
        String Response;
        if (CardLongNumber.isEmpty()) {
            Response = "Schema Validation failed because cardLongNumber is a required field";
            log.error("Schema Validation failed because cardLongNumber is a required field");
        } else if (CardLongNumber.length() <= 12) {
            Response = "Schema Validation failed because cardLongNumber length is too short";
            log.error("Schema Validation failed because cardLongNumber length is too short");
        } else {
            Response = Bank.checkCard(CardLongNumber.replaceAll("-",""));
        }
        return Response;
    }
}