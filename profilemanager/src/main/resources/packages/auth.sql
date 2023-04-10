
/******************************************************************************
 * This piece of work is to enhance authentication project functionality.     *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      auth.sql                                                        *
 * Created:   03/02/2023, 14:20                                               *
 * Modified:  03/02/2023, 14:20                                               *
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

set serveroutput on;
set define off;

PROMPT "Creating User Authentication Header Package"

-- Create Packages
CREATE OR REPLACE PACKAGE authenticate_pkg
AS
    /* $Header: authenticate_pkg. 1.0.0 03-FEB-23 14:24 Package
    =================================================================================
      Copyright (c) 2023 Aerosimo

      Permission is hereby granted, free of charge, to any person obtaining a copy
      of this software and associated documentation files (the "Software"), to deal
      in the Software without restriction, including without limitation the rights
      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
      copies of the Software, and to permit persons to whom the Software is
      furnished to do so, subject to the following conditions:

      The above copyright notice and this permission notice shall be included in all
      copies or substantial portions of the Software.

      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
      FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE
      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
      SOFTWARE.
    ================================================================================
    Name: authenticate_pkg
    Program Type: Package Specification
    Purpose: ADD/FIND/UPDATE/DELETE entity
    =================================================================================
    HISTORY
    =================================================================================
    | DATE 		| Owner 	| Activity
    =================================================================================
    | 03-FEB-23	| eomisore 	| Created initial script.|
    =================================================================================
    */

    -- Create or Register Account
    PROCEDURE Signup(
        i_username  IN user_tbl.uname%TYPE,
        i_password  IN user_tbl.pword%TYPE,
        i_email     IN user_tbl.email%TYPE,
        i_createdBy IN user_tbl.createdBy%TYPE,
        o_response  OUT VARCHAR2);

    -- Login to User Account
    PROCEDURE Login(
        i_username  IN user_tbl.uname%TYPE,
        i_password  IN user_tbl.pword%TYPE,
        i_ipaddress IN audit_tbl.inet%TYPE,
        o_userid    OUT user_tbl.userID%TYPE,
        o_username  OUT user_tbl.uname%TYPE,
        o_email     OUT user_tbl.email%TYPE,
        o_response  OUT VARCHAR2);

    -- Logout of Account
    PROCEDURE Logout(
        i_userid     IN audit_tbl.userID%TYPE,
        i_ipaddress  IN audit_tbl.inet%TYPE);

    -- Forgot Account password
    PROCEDURE ForgotPassword(
        i_email     IN user_tbl.email%TYPE,
		i_transID   IN otp_tbl.transID%TYPE,
        o_transID   OUT otp_tbl.transID%TYPE,
        o_username  OUT user_tbl.uname%TYPE,
        o_response  OUT VARCHAR2);

    -- Reset Account password
    PROCEDURE ResetPassword(
        i_transID  IN otp_tbl.transID%TYPE,
        i_password IN user_tbl.pword%TYPE,
        o_username OUT user_tbl.uname%TYPE,
        o_email    OUT user_tbl.email%TYPE,
        o_response OUT VARCHAR2);

END authenticate_pkg;
/

PROMPT "Creating User Authentication Body Package"

-- Create Packages
CREATE OR REPLACE PACKAGE BODY authenticate_pkg
AS
    /* $Body: authenticate_pkg. 1.0.0 03-FEB-23 14:24 Package
    =================================================================================
      Copyright (c) 2023 Aerosimo

      Permission is hereby granted, free of charge, to any person obtaining a copy
      of this software and associated documentation files (the "Software"), to deal
      in the Software without restriction, including without limitation the rights
      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
      copies of the Software, and to permit persons to whom the Software is
      furnished to do so, subject to the following conditions:

      The above copyright notice and this permission notice shall be included in all
      copies or substantial portions of the Software.

      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
      FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE
      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
      SOFTWARE.
    ================================================================================
    Name: authenticate_pkg
    Program Type: Package Specification
    Purpose: ADD/FIND/UPDATE/DELETE entity
    =================================================================================
    HISTORY
    =================================================================================
    | DATE 		| Owner 	| Activity
    =================================================================================
    | 03-FEB-23	| eomisore 	| Created initial script.|
    =================================================================================
    */
-- Create or Register Account
    PROCEDURE Signup(
        i_username  IN user_tbl.uname%TYPE,
        i_password  IN user_tbl.pword%TYPE,
        i_email     IN user_tbl.email%TYPE,
        i_createdBy IN user_tbl.createdBy%TYPE,
        o_response  OUT VARCHAR2)
    AS
    BEGIN
        INSERT INTO user_tbl (uname,pword,email,status,createdBy) VALUES (i_username,utl_encode.base64_encode(utl_raw.cast_to_raw(enc_dec.encrypt(i_password))),i_email,'Active',i_createdBy);
        o_response := 'Success';
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
    o_response := SQLCODE || SUBSTR(SQLERRM, 1, 2000);
    END Signup;

-- Login to User Account
    PROCEDURE Login(
        i_username  IN user_tbl.uname%TYPE,
        i_password  IN user_tbl.pword%TYPE,
        i_ipaddress IN audit_tbl.inet%TYPE,
        o_userid    OUT user_tbl.userID%TYPE,
        o_username  OUT user_tbl.uname%TYPE,
        o_email     OUT user_tbl.email%TYPE,
        o_response  OUT VARCHAR2)
    AS
        match_count NUMBER;
    BEGIN
        SELECT COUNT(*),userID,uname,email INTO match_count,o_userid,o_username,o_email FROM user_tbl
        WHERE i_username IN (userID,uname,email)
          AND enc_dec.decrypt(utl_raw.cast_to_varchar2(utl_encode.base64_decode(pword))) = i_password
          AND status = 'Active' GROUP BY userID,uname,email;
        IF match_count = 1 THEN
            INSERT INTO audit_tbl (userID, inet, login) VALUES (o_userid, i_ipaddress, systimestamp);
            o_response := 'Success';
        END IF;
        IF SQL%NOTFOUND THEN
            RAISE NO_DATA_FOUND;
        END IF;
    EXCEPTION WHEN NO_DATA_FOUND THEN ROLLBACK;
    o_response := 'Authentication failed because of wrong username or password!';
    WHEN OTHERS THEN ROLLBACK;
    o_response := SQLCODE || SUBSTR(SQLERRM, 1, 2000);
    END Login;

    -- Logout of Account
    PROCEDURE Logout(
        i_userid     IN audit_tbl.userID%TYPE,
        i_ipaddress  IN audit_tbl.inet%TYPE)
    AS
    BEGIN
        UPDATE audit_tbl SET logout = systimestamp WHERE userID = i_userid AND inet = i_ipaddress;
        IF SQL%NOTFOUND THEN
            UPDATE audit_tbl SET logout = systimestamp WHERE userID = i_userid AND logout IS NULL;
        END IF;
    EXCEPTION WHEN NO_DATA_FOUND THEN ROLLBACK;
    WHEN OTHERS THEN ROLLBACK;
    END Logout;

    -- Forgot Account password
    PROCEDURE ForgotPassword(
        i_email     IN user_tbl.email%TYPE,
		i_transID   IN otp_tbl.transID%TYPE,
        o_transID   OUT otp_tbl.transID%TYPE,
        o_username  OUT user_tbl.uname%TYPE,
        o_response  OUT VARCHAR2)
    AS
        x_userid VARCHAR2(20 BYTE);
    BEGIN
        SELECT uname INTO o_username FROM user_tbl WHERE i_email = email;
        UPDATE user_tbl SET pword = 'TO BE CHANGE', status = 'Inactive' WHERE email = i_email RETURNING userID INTO x_userid;
        INSERT INTO otp_tbl (userID,transID,status) VALUES (x_userid,i_transID,'Valid') RETURNING transID INTO o_transID;
        o_response := 'Success';
        IF SQL%NOTFOUND THEN
            RAISE NO_DATA_FOUND;
        END IF;
    EXCEPTION WHEN NO_DATA_FOUND THEN ROLLBACK;
    o_response := 'Unfortunately, no results matching your criteria '|| i_email || ' were found.';
    WHEN OTHERS THEN ROLLBACK;
    o_response := SQLCODE || SUBSTR(SQLERRM, 1, 2000);
    END ForgotPassword;

    -- Reset Account password
    PROCEDURE ResetPassword(
        i_transID  IN otp_tbl.transID%TYPE,
        i_password IN user_tbl.pword%TYPE,
        o_username OUT user_tbl.uname%TYPE,
        o_email    OUT user_tbl.email%TYPE,
        o_response OUT VARCHAR2)
    AS
    BEGIN
        SELECT usr.uname, usr.email INTO o_username, o_email FROM user_tbl usr, otp_tbl otp WHERE otp.transID = i_transID;
        IF SQL%NOTFOUND THEN
            RAISE NO_DATA_FOUND;
        ELSE
            UPDATE user_tbl
            SET pword = utl_encode.base64_encode(utl_raw.cast_to_raw(enc_dec.encrypt(i_password))), status = 'Active'
            WHERE uname = o_username;
            o_response := 'Success';
        END IF;
    EXCEPTION WHEN NO_DATA_FOUND THEN ROLLBACK;
    o_response := 'Unfortunately, no results matching your criteria '|| i_transID || ' were found.';
    WHEN OTHERS THEN ROLLBACK;
    o_response := SUBSTR(SQLERRM, 1, 2000);
    END ResetPassword;

END authenticate_pkg;
/