/******************************************************************************
 * This piece of work is to enhance profile-manager project functionality.    *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      WelcomeMail.java                                                *
 * Created:   24/02/2023, 09:45                                               *
 * Modified:  24/02/2023, 09:45                                               *
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
public class WelcomeMail {

    private static final Logger log = LogManager.getLogger(WelcomeMail.class.getName());

    static String response;

    public static String sendMail(String username, String email) {
        log.info("Preparing Email body content to " + username + " at the following email address: " + email);
        StringBuilder message;
        try {
            message = new StringBuilder("""
                    <!DOCTYPE html>
                    <html>
                       <head>
                          <meta charset="UTF-8">
                          <meta name="viewport" content="width=device-width, initial-scale=1.0">
                          <meta http-equiv="X-UA-Compatible" content="IE=edge">
                          <style>
                             * {
                             margin: 0;
                             padding: 0;
                             font-family: Poppins;
                             }
                             img {
                             display: inline-block;
                             }
                             .container {
                             max-width: 500px;
                             margin: 20px auto;
                             border-radius: 4px;
                             border: 1px solid rgba(0, 0, 0, .1);
                             // border-top: 3px solid #016FB9;
                             min-height: 100px;
                             position: relative;
                             &::before {
                             content: '';
                             position: absolute;
                             top: 0;
                             left: 0;
                             width: 100%;
                             height: 3px;
                             background: linear-gradient(to right, #0267C1, #D65108);
                             }
                             }
                             .illustration {
                             width: 100%;
                             text-align: center;
                             box-shadow: 0 10px 20px -5px rgba(0, 0, 0, .05);
                             border-radius: 0 0 50% 50% / 1%;
                             }
                             .illustration img {
                             width: 70%;
                             margin: 50px auto;
                             }
                             .separator {
                             display: block;
                             height: 3px;
                             width: 70%;
                             margin: 10px auto;
                             background-color: rgba(0, 0, 0, .05);
                             border-radius: 10px;
                             position: relative;
                             overflow: hidden;
                             &::before, &::after {
                             content: '';
                             position: absolute;
                             left: 0;
                             top: 0;
                             height: 100%;
                             width: 33%;
                             border-radius: inherit;
                             opacity: .05;
                             }
                             &::before {
                             left: 0;
                             background: #EFA00B;
                             }
                             &::after {
                             left: initial;
                             right: 0;
                             background: #D65108;
                             }
                             }
                             .hgroup {
                             text-align: center;
                             padding: 50px 30px 30px;
                             }
                             .name {
                             display: block;
                             text-transform: uppercase;
                             margin-bottom: 5px;
                             color: #0267C1;
                             font-weight: 600;
                             }
                             .hgroup h1 {
                             font-size: 20px;
                             font-weight: 600;
                             color: #333;
                             }
                             .hgroup h2 {
                             font-size: 19px;
                             }
                             .hgroup p {
                             font-size: 14px;
                             color: slategrey;
                             margin-top: 15px;
                             text-align: justify;
                             line-height: 25px;
                             }
                             .items {
                             padding: 30px;
                             display: grid;
                             grid-template-columns: repeat(2, 1fr);
                             }
                             .item {
                             margin-bottom: 10px;
                             text-align: center;
                             width: 100%;
                             margin: 0 auto 50px;
                             }
                             .item .icon {
                             margin-bottom: 10px;
                             }
                             .item .icon img {
                             width: 60px;
                             }
                             .item .title {
                             margin-bottom: 5px;
                             font-size: 16px;
                             font-weight: 600;
                             }
                             .item .subtitle {
                             font-size: 13px;
                             color: slategrey;
                             padding: 1rem;
                             }
                             .button-wrap {
                             text-align: center;
                             padding: 2rem;
                             }
                             button.explore {
                             padding: 15px 25px;
                             font: inherit;
                             background: linear-gradient(to right, #0267C1, #0280EF);
                             border-radius: 50px;
                             border: 0;
                             color: #fff;
                             margin: auto;
                             display: inline-block;
                             transition: all .2s ease-in-out;
                             cursor: pointer;
                             box-shadow: 0 5px 10px rgba(0, 0, 0, .1);
                             }
                             button.explore:hover {
                             transform: translateY(-5px);
                             box-shadow: 0 15px 10px -7px rgba(0, 0, 0, .1);
                             }
                             footer {
                             font-size: 12px;
                             color: slategrey;
                             text-align: center;
                             padding: 30px;
                             }
                          </style>
                       </head>
                       <body>
                          <div class="container">
                             <div class="illustration">
                                <a href="https://www.imagebam.com/view/MED2HDH" target="_blank"><img src="https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png" alt="Aerosimo logo"/></a>
                             </div>
                             <!--   <span class="separator"></span> -->
                             <div class="hgroup">
                                <span class="name">Hello,\s""");
            message.append(username);
            message.append("""
                    </span><br>
                                <h1>Welcome to Family Profile Manager by Aerosimo</h1>
                                <p>We're really excited that you've joined other members to make this Ominet application worth it.</p>
                                <p>The aim of this application is to incrementally build smart home ecosystem, which is a portal for members of this household who wish to discover new opportunities, connections and inspirations. Its main purpose is providing crucial media exposure for new interesting start-ups and innovative technologies to thrive and develop.
                                </p>
                             </div>
                             <div class="hgroup">
                                <h2>Thank you so much for compliance</h2>
                             </div>
                             <div class="button-wrap">
                                <button class="explore">
                                Aerosimo Support Team
                                </button> \s
                             </div>
                             <footer>
                                Aerosimo Ltd © <script>document.write(new Date().getFullYear());</script>
                                <br>&#9742; &nbsp;+44 782 7963 199\s
                                <br>&#9993;&nbsp;support@aerosimo.com
                             </footer>
                             <span style="font-size:10px;">This is a confidential email and may also be privileged. If you are not the intended recipient, please inform us immediately.</span>
                          </div>
                       </body>
                    </html>""");
            response = Postmaster.SendEmail(email, " Family Profile Manager Welcome Notification", message.toString(), "", "Aerosimo Support <support@aerosimo.com>");
            log.info("Email " + response);
        } catch (Exception err) {
            response = "Welcome Email failed";
            log.error(WelcomeMail.class.getName() + " failed with email error - " + err);
        }
        return response;
    }
}