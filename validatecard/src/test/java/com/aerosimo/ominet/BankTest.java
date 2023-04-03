/**************************************************************************************************
 * This piece of work is to enhance validatecard project functionality.                           *
 *                                                                                                *
 * Author:    eomisore                                                                            *
 * File:      BankTest.java                                                                       *
 * Created:   03/02/2023, 21:25                                                                   *
 * Modified:  03/02/2023, 21:25                                                                   *
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

class BankTest {

    private static final Logger log = LogManager.getLogger(BankTest.class.getName());

    @BeforeEach
    void setUp() {
        log.info("Starting Card Validation Test");
    }

    @AfterEach
    void tearDown() {
        log.info("Card Validation Test complete");
    }

    @Test
    @DisplayName("Validating VISA Card")
    void testVISA() {
        String actual;
        String expected;
        expected = "Visa";
        actual = Bank.checkCard("4916801905619884");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating MasterCard Card")
    void testMasterCard() {
        String actual;
        String expected;
        expected = "MasterCard";
        actual = Bank.checkCard("5193347091529678");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating American Express Card")
    void testAMEX() {
        String actual;
        String expected;
        expected = "American Express";
        actual = Bank.checkCard("348235854897579");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating JCB Card")
    void testJCB() {
        String actual;
        String expected;
        expected = "JCB";
        actual = Bank.checkCard("3553512923196850");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating Discover Card")
    void testDiscover() {
        String actual;
        String expected;
        expected = "Discover";
        actual = Bank.checkCard("6011643317673615");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating Voyager Card")
    void testVoyager() {
        String actual;
        String expected;
        expected = "Voyager";
        actual = Bank.checkCard("8699005612629079");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating UnionPay Card")
    void testUnionPay() {
        String actual;
        String expected;
        expected = "China UnionPay";
        actual = Bank.checkCard("6222028807629548");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating DinersClub Card")
    void testDinersClub() {
        String actual;
        String expected;
        expected = "DinersClub";
        actual = Bank.checkCard("38999138953244");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating UATP Card")
    void testUATP() {
        String actual;
        String expected;
        expected = "UATP";
        actual = Bank.checkCard("112583326236387");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating Dankort Card")
    void testDankort() {
        String actual;
        String expected;
        expected = "Dankort";
        actual = Bank.checkCard("5019368793005259");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }

    @Test
    @DisplayName("Validating InterPayment Card")
    void testInterPayment() {
        String actual;
        String expected;
        expected = "InterPayment";
        actual = Bank.checkCard("6366801905619884");
        Assertions.assertNotNull(actual, "Checking that the card issuing network response is not null");
        Assertions.assertEquals(expected, actual, "This should match a the cardNetwork message from the CardValidator api");
    }
}