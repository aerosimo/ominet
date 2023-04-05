<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true"%>
<%--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance profile-manager project functionality.  ~
  ~                                                                          ~
  ~ Author:    eomisore                                                      ~
  ~ File:      index.jsp                                                     ~
  ~ Created:   22/02/2023, 21:42                                             ~
  ~ Modified:  22/02/2023, 22:14                                             ~
  ~                                                                          ~
  ~ Copyright (c)  2023.  Aerosimo Ltd                                       ~
  ~                                                                          ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a
  ~ copy of this software and associated documentation files (the "Software"),
  ~ to deal in the Software without restriction, including without limitation
  ~ the rights to use, copy, modify, merge, publish, distribute, sublicense,
  ~ and/or sell copies of the Software, and to permit persons to whom the
  ~ Software is furnished to do so, subject to the following conditions:     ~
  ~                                                                          ~
  ~ The above copyright notice and this permission notice shall be included
  ~ in all copies or substantial portions of the Software.
  ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  ~ EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
  ~ OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
  ~ NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
  ~ HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
  ~ WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
  ~ FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
  ~ OR OTHER DEALINGS IN THE SOFTWARE.
  ~                                                                          ~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~--%>

<html>
<head>
    <meta charset="UTF-8">
    <meta content="Elijah Omisore" name="author">
    <meta content="Aerosimo 1.0.0" name="generator">
    <meta content="Aerosimo" name="application-name">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="Aerosimo IT Consultancy" name="description">
    <meta content="Aerosimo" name="apple-mobile-web-app-title">
    <meta content="Oracle, Java, Tomcat, Maven, Jenkins, Bitbucket, Github" name="keywords">
    <meta content="width=device-width, initial-scale=1, user-scalable=no" minimum-scale=1.0, maximum-scale=1.0"
          name="viewport"/>
    <!-- Title -->
    <title>Dashboard | Family Profile Manager | Aerosimo Ltd</title>
    <!-- Favicon-->
    <link href="assets/img/icons/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/icons/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/icons/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/icons/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/icons/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/icons/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
</head>

<body>
    <h1>Hello ${uname} and welcome to Omisore Family Profile Manager</h1>
    <p>The current timestamp is: <%= new java.util.Date() %></p>
</body>

</html>
