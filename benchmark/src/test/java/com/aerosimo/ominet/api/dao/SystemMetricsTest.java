/******************************************************************************
 * This piece of work is to enhance benchmark project functionality.          *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      SystemMetricsTest.java                                          *
 * Created:   11/04/2023, 01:00                                               *
 * Modified:  11/04/2023, 01:00                                               *
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

package com.aerosimo.ominet.api.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemMetricsTest {

    private static final Logger log = LogManager.getLogger(SystemMetricsTest.class.getName());

    @BeforeEach
    void setUp() {log.info("Starting test call to retrieve system metrics");}

    @AfterEach
    void tearDown() {log.info("System metrics test complete");}

    @Test
    @DisplayName("Unit Testing System Disk Usage")
    void getDisk() {
        assertNotNull(SystemMetrics.getDisk(),"Checking the Total disk space");
    }

    @Test
    @DisplayName("Unit Testing System Memory Usage")
    void getMemory() {
        assertNotNull(SystemMetrics.getMemory(),"Checking the memory utilization");
    }

    @Test
    @DisplayName("Unit Testing System CPU Usage")
    void getCPU() {
        assertNotNull(SystemMetrics.getMemory(),"Checking the CPU Usage");
    }
}