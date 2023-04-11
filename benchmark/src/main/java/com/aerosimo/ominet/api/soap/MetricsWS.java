/******************************************************************************
 * This piece of work is to enhance benchmark project functionality.          *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      MetricsWS.java                                                  *
 * Created:   11/04/2023, 00:43                                               *
 * Modified:  11/04/2023, 00:43                                               *
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

import com.aerosimo.ominet.api.dao.SystemMetrics;
import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

import java.util.ArrayList;

@WebService(name = "SystemMetrics", serviceName = "SystemMetrics",
        portName = "HarvestMetrics", targetNamespace = "https://aerosimo.com/api/ws/systemmetrics/v1")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class MetricsWS {

    @WebMethod(operationName = "diskUsage")
    @WebResult(name = "diskUsage", partName = "diskUsageResponse")
    public String[] getDisk() {
        return SystemMetrics.getDisk();
    }

    @WebMethod(operationName = "memmoryUsage")
    @WebResult(name = "memmoryUsage", partName = "memmoryUsageResponse")
    public String[] getMemory(){
        return SystemMetrics.getMemory();

    }

    @WebMethod(operationName = "cpuUsage")
    @WebResult(name = "cpuUsage", partName = "cpuUsageResponse")
    public ArrayList<String> getCPU(){
        return SystemMetrics.getCPU();
    }
}