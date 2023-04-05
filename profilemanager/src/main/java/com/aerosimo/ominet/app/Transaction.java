/******************************************************************************
 * This piece of work is to enhance profile-manager project functionality.    *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Transaction.java                                                *
 * Created:   24/02/2023, 12:59                                               *
 * Modified:  24/02/2023, 12:59                                               *
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

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Transaction {

    private static final Logger log = LogManager.getLogger(Transaction.class.getName());

    static String response;
    static int RANDOM_STRING_LENGTH;
    static final String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static String otp(String app) {
        RANDOM_STRING_LENGTH = 6;
        response = RandomStringUtils.random(RANDOM_STRING_LENGTH, CHAR_LIST);
        log.info("OTP: " + response + " generated for " + app);
        return response;
    }

    public static String ref(String app) {
        RANDOM_STRING_LENGTH = 4;
        String first = RandomStringUtils.random(RANDOM_STRING_LENGTH, CHAR_LIST);
        String second = RandomStringUtils.random(RANDOM_STRING_LENGTH, CHAR_LIST);
        String third = RandomStringUtils.random(RANDOM_STRING_LENGTH, CHAR_LIST);
        response = first + "-" + second + "-" + third;
        log.info("REF: " + response + " generated for " + app);
        return response;
    }
}