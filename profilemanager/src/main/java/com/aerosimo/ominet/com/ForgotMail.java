/******************************************************************************
 * This piece of work is to enhance profile-manager project functionality.    *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      ForgotMail.java                                                 *
 * Created:   24/02/2023, 11:42                                               *
 * Modified:  24/02/2023, 11:42                                               *
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

import com.aerosimo.ominet.app.Postmaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ForgotMail {

    private static final Logger log = LogManager.getLogger(ForgotMail.class.getName());
    static String response;

    public static String sendMail(String userName, String authCode, String emailAddress) {
        StringBuilder message;
        try {
            log.info("Preparing Email body content");
            message = new StringBuilder("""
                <body style="margin:0;padding:0">
                   <div style="width:100%;min-width:320px;font-family:Helvetica,Arial;font-weight:bold;font-size:24px;background-color:#efefeb">
                      <div style="margin:0 auto;width:100%;max-width:598px;text-align:center;background-color:#242e30">
                         <div style="font-size:0;background-color:#242e30">
                            <div style="width:15%;display:inline-block;vertical-align:top">
                               <img style="width:100%;min-height:auto" src="http://www.uberconference.com/static/img2/emails/announcement_l.gif">
                            </div>
                            <div style="display:inline-block;width:70%;vertical-align:top;color:#f7f7f7;text-align:center">
                               <div style="font-size:42px;font-weight:bold;padding-bottom:12px;padding-top:25px">""");
            message.append(userName);
            message.append("""
                </div>
                               <hr style="width:80%;border:1px solid #2f6577">
                               <div style="font-size:24px;font-weight:normal;padding-top:12px;padding-bottom:6px">One-Time Passcode (OTP)</div>
                               <table style="margin:auto">
                                  <tbody>
                                     <tr>
                                        <td style="color:#afafaf;font-size:11px;padding:6px">Your OTP is available below, please use this to validate your login</td>
                                     </tr>
                                  </tbody>
                               </table>
                               <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <tbody>
                                     <tr>
                                        <td width="100%" style="padding-top:32px;text-align:center" align="center">
                                           <table width="220px" cellspacing="0" cellpadding="0" style="display:inline-block">
                                              <tbody>
                                                 <tr>
                                                    <td align="center" width="220px" height="70" bgcolor="#28b777" style="background:#28b777;border-radius:28px;color:#ffffff;display:block">
                                                       <h1 style="color:#ffffff;font-size:20px;font-family:Arial,sans-serif;text-decoration:none;line-height:42px;width:100%;display:inline-block">""");
            message.append(authCode);
            message.append("""
                </h1>
                                                    </td>
                                                 </tr>
                                              </tbody>
                                           </table>
                                        </td>
                                     </tr>
                                  </tbody>
                               </table>
                            </div>
                            <div style="width:15%;display:inline-block;vertical-align:top">
                               <img style="width:100%;min-height:auto" src="http://www.uberconference.com/static/img2/emails/announcement_r.gif">
                            </div>
                         </div>
                         <div style="text-align:center">
                            <img style="width:100%;min-height:auto;vertical-align:bottom" src="http://www.uberconference.com/static/img2/emails/announcement_b.gif">
                         </div>
                      </div>
                   </div>""");
            response = Postmaster.SendEmail(emailAddress,"Authentication Login Code", message.toString(),"","Aerosimo Support <support@aerosimo.com>");
            log.info("Email " + response);
        } catch (Exception err) {
            response = "Forgot password email failed";
            log.error("Forgot password email service " + ForgotMail.class.getName() + " failed with email error - " + err);
        }
        return response;
    }
}