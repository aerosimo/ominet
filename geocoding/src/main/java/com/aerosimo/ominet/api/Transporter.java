/******************************************************************************
 * This piece of work is to enhance geocoding project functionality.          *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Transporter.java                                                *
 * Created:   26/04/2023, 19:03                                               *
 * Modified:  26/04/2023, 20:11                                               *
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

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Transporter {

    private static final Logger log = LogManager.getLogger(Transporter.class.getName());
    static String response;
    public static String transport(String resourceURI){
        log.info("Preparing the HTTP connection" + resourceURI);
        try{
            URL url = new URL(resourceURI);
            HttpURLConnection http;
            http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(5000);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json; charset=UTF-8");
            http.setRequestProperty("User-Agent", "Mozilla");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("GET");
            http.setUseCaches(false);
            http.setInstanceFollowRedirects(false);
            OutputStream os;
            os = http.getOutputStream();
            os.write(resourceURI.getBytes(StandardCharsets.UTF_8));
            //os.close();
            os.flush();
            log.info("HTTP Status Code: " + http.getResponseCode());
            log.info("HTTP Status Message: " + http.getResponseMessage());
            InputStream in;
            in = new BufferedInputStream(http.getInputStream());
            response = IOUtils.toString(in, StandardCharsets.UTF_8);
            in.close();
            http.disconnect();
            log.info("Getting the Response: " + response);
        } catch (Exception err){
            response = "HTTP Connection Error : " + err;
            log.error("HTTP Connection Error : " + err);
        }
        return response;
    }

}