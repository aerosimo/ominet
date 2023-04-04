/******************************************************************************
 * This piece of work is to enhance constellation project functionality.      *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Aztro.java                                                      *
 * Created:   24/03/2023, 18:54                                               *
 * Modified:  24/03/2023, 18:54                                               *
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

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Aztro {

    private static final Logger log = LogManager.getLogger(Aztro.class.getName());
    static JSONObject Aztro;
    public static JSONObject getDailyHoroscope(String zodiacSign) {
        log.info("Preparing to make api calls to aztro to get daily horoscope update for " + zodiacSign);
        String resourceURI;
        resourceURI = "https://aztro.sameerkumar.website/?sign=" + zodiacSign.toLowerCase() + "&day=today";
        try{
            URL url = new URL(resourceURI);
            HttpURLConnection httpcon;
            httpcon = (HttpURLConnection) url.openConnection();
            httpcon.setConnectTimeout(5000);
            httpcon.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpcon.setRequestProperty("Accept", "application/json; charset=UTF-8");
            httpcon.setRequestProperty("User-Agent", "Java client");
            httpcon.setDoOutput(true);
            httpcon.setDoInput(true);
            httpcon.setRequestMethod("POST");
            httpcon.setUseCaches(false);
            httpcon.setInstanceFollowRedirects(false);
            OutputStream os;
            os = httpcon.getOutputStream();
            os.write(resourceURI.getBytes(StandardCharsets.UTF_8));
            os.close();
            InputStream in;
            in = new BufferedInputStream(httpcon.getInputStream());
            String result;
            result = IOUtils.toString(in, StandardCharsets.UTF_8);
            in.close();
            Aztro = new JSONObject(result);
            log.info(Aztro);
        } catch (Exception err){
            log.error("Aztro api calls failed when retrieving " + zodiacSign + " daily horoscope update with adaptor error " + err);
        }
        return Aztro;
    }
}