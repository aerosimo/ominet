/******************************************************************************
 * This piece of work is to enhance constellation project functionality.      *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AstrologyWS.java                                                *
 * Created:   25/03/2023, 02:21                                               *
 * Modified:  25/03/2023, 02:21                                               *
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

import com.aerosimo.ominet.api.dao.Horoscope;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebService(name = "Constellation", serviceName = "Constellation",
        portName = "Astrology", targetNamespace = "https://aerosimo.com/api/ws/constellation/v1")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class AstrologyWS {

    private static final Logger log = LogManager.getLogger(AstrologyWS.class.getName());

    @WebMethod(operationName = "dailyHoroscope")
    @WebResult(name = "Status", partName = "dailyHoroscopeResponse")
    public String setHoroscope(@XmlElement(required = true)@WebParam(name = "presentDay",
            partName = "dailyHoroscopeRequest") String presentDay) {
        String Response;
        if (presentDay.isEmpty()){
            Response = "Schema Validation failed because presentDay is a required field";
        }
        else{
            Response = Horoscope.updateZodiac();
        }
        return Response;
    }
}