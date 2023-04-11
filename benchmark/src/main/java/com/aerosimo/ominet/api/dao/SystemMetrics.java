/******************************************************************************
 * This piece of work is to enhance benchmark project functionality.          *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      SystemMetrics.java                                              *
 * Created:   10/04/2023, 19:36                                               *
 * Modified:  10/04/2023, 19:36                                               *
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

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;

public class SystemMetrics {

    private static final Logger log = LogManager.getLogger(SystemMetrics.class.getName());
    static String[] diskusage = new String[3];
    static String[] memoryusage = new String[4];
    static ArrayList<String> cpuusage = new ArrayList<String>();

    public static String[] getDisk() {
        log.info("Preparing to get disk information");
        File root = new File("/");
        diskusage[0] = String.format("%.2fGB", (double) root.getTotalSpace() / 1073741824);
        diskusage[1] = String.format("%.2fGB", (double) root.getFreeSpace() / 1073741824);
        diskusage[2] = String.format("%.2fGB", (double) root.getUsableSpace() / 1073741824);
        return diskusage;
    }
    public static String[] getMemory() {
        log.info("Preparing to get memory information");
        var memoryMXBean = ManagementFactory.getMemoryMXBean();
        memoryusage[0] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getInit() / 1073741824);
        memoryusage[1] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getUsed() / 1073741824);
        memoryusage[2] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getMax() / 1073741824);
        memoryusage[3] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getCommitted() / 1073741824);
        return memoryusage;
    }

    public static ArrayList<String> getCPU() {
        log.info("Preparing to get cpu information");
        var threadMXBean = ManagementFactory.getThreadMXBean();
        for (Long threadID : threadMXBean.getAllThreadIds()) {
            ThreadInfo info = threadMXBean.getThreadInfo(threadID);
            cpuusage.add(info.getThreadName());
            cpuusage.add(String.valueOf(info.getThreadState()));
            cpuusage.add(String.format("%s", threadMXBean.getThreadCpuTime(threadID)));
        }
        return cpuusage;
    }

}