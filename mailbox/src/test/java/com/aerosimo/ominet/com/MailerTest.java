/******************************************************************************
 * This piece of work is to enhance mailbox project functionality.            *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      MailerTest.java                                                 *
 * Created:   22/03/2023, 15:32                                               *
 * Modified:  22/03/2023, 15:32                                               *
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailerTest {

    private static final Logger log = LogManager.getLogger(MailerTest.class.getName());

    @BeforeEach
    void setUp() {log.info("Starting Card Validation Test");}

    @AfterEach
    void tearDown() {log.info("Card Validation Test complete");}

    @Test
    @DisplayName("Validating email api")
    void sendEmail() {
        String actual;
        String expected;
        String message;
        expected = "Fail";
        message = "<html>\n" +
                "<head>\n" +
                "<style>@property --rotate {\n" +
                "  syntax: \"<angle>\";\n" +
                "  initial-value: 132deg;\n" +
                "  inherits: false;\n" +
                "}\n" +
                "\n" +
                ":root {\n" +
                "  --card-height: 65vh;\n" +
                "  --card-width: calc(var(--card-height) / 1.5);\n" +
                "}\n" +
                "\n" +
                "\n" +
                "body {\n" +
                "  min-height: 100vh;\n" +
                "  background: #212534;\n" +
                "  display: flex;\n" +
                "  align-items: center;\n" +
                "  flex-direction: column;\n" +
                "  padding-top: 2rem;\n" +
                "  padding-bottom: 2rem;\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "\n" +
                ".card {\n" +
                "  background: #191c29;\n" +
                "  width: var(--card-width);\n" +
                "  height: var(--card-height);\n" +
                "  padding: 3px;\n" +
                "  position: relative;\n" +
                "  border-radius: 6px;\n" +
                "  justify-content: center;\n" +
                "  align-items: center;\n" +
                "  text-align: center;\n" +
                "  display: flex;\n" +
                "  font-size: 1.5em;\n" +
                "  color: rgb(88 199 250 / 0%);\n" +
                "  cursor: pointer;\n" +
                "  font-family: cursive;\n" +
                "}\n" +
                "\n" +
                ".card:hover {\n" +
                "  color: rgb(88 199 250 / 100%);\n" +
                "  transition: color 1s;\n" +
                "}\n" +
                ".card:hover:before, .card:hover:after {\n" +
                "  animation: none;\n" +
                "  opacity: 0;\n" +
                "}\n" +
                "\n" +
                "\n" +
                ".card::before {\n" +
                "  content: \"\";\n" +
                "  width: 104%;\n" +
                "  height: 102%;\n" +
                "  border-radius: 8px;\n" +
                "  background-image: linear-gradient(\n" +
                "    var(--rotate)\n" +
                "    , #5ddcff, #3c67e3 43%, #4e00c2);\n" +
                "    position: absolute;\n" +
                "    z-index: -1;\n" +
                "    top: -1%;\n" +
                "    left: -2%;\n" +
                "    animation: spin 2.5s linear infinite;\n" +
                "}\n" +
                "\n" +
                ".card::after {\n" +
                "  position: absolute;\n" +
                "  content: \"\";\n" +
                "  top: calc(var(--card-height) / 6);\n" +
                "  left: 0;\n" +
                "  right: 0;\n" +
                "  z-index: -1;\n" +
                "  height: 100%;\n" +
                "  width: 100%;\n" +
                "  margin: 0 auto;\n" +
                "  transform: scale(0.8);\n" +
                "  filter: blur(calc(var(--card-height) / 6));\n" +
                "  background-image: linear-gradient(\n" +
                "    var(--rotate)\n" +
                "    , #5ddcff, #3c67e3 43%, #4e00c2);\n" +
                "    opacity: 1;\n" +
                "  transition: opacity .5s;\n" +
                "  animation: spin 2.5s linear infinite;\n" +
                "}\n" +
                "\n" +
                "@keyframes spin {\n" +
                "  0% {\n" +
                "    --rotate: 0deg;\n" +
                "  }\n" +
                "  100% {\n" +
                "    --rotate: 360deg;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "a {\n" +
                "  color: #212534;\n" +
                "  text-decoration: none;\n" +
                "  font-family: sans-serif;\n" +
                "  font-weight: bold;\n" +
                "  margin-top: 2rem;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"card\">\n" +
                "  Aerosimo\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        actual = Mailer.SendEmail("babyboi@omisore.co.uk","Unit Test Email Service",message,"","Aerosimo Support <support@aerosimo.com>");
        assertNotNull(actual,"Checking that the email response is not null");
    }
}