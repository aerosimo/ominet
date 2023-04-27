/******************************************************************************
 * This piece of work is to enhance geocoding project functionality.          *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Geocoding.java                                                  *
 * Created:   27/04/2023, 23:39                                               *
 * Modified:  27/04/2023, 23:39                                               *
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

package com.aerosimo.ominet.api;

import com.aerosimo.ominet.app.Geocode;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebService(name = "Geocoding", serviceName = "Geocoding",
        portName = "Geocode", targetNamespace = "https://aerosimo.com/api/ws/geocoding/v1")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class Geocoding {

    private static final Logger log = LogManager.getLogger(Geocoding.class.getName());

    @WebMethod(operationName = "getGeocode")
    @WebResult(name = "Status", partName = "geocodeResponse")
    public String getGeocode(@XmlElement(required = true)@WebParam(name = "userID", partName = "geocodeRequest") String userID,
                             @XmlElement(required = true)@WebParam(name = "encodedURL", partName = "geocodeRequest") String encodedURL,
                             @XmlElement(required = true)@WebParam(name = "addressType", partName = "geocodeRequest") String addressType) {
        String Response;
        if (userID.isEmpty()){
            Response = "Schema Validation failed because userID is a required field";
        } else if (encodedURL.isEmpty()) {
            Response = "Schema Validation failed because encodedURL is a required field";
        } else if (addressType.isEmpty()) {
            Response = "Schema Validation failed because addressType is a required field";
        }
        else{
            Response = Geocode.setCoordinate(userID,encodedURL,addressType);
        }
        return Response;
    }
}