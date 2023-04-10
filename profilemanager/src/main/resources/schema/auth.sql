
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

PROMPT "Creating User Account Schema."
-------------------------------------
--  Connect and run the following  --
-------------------------------------

PROMPT "Creating Tables"

-- Create Tables
CREATE TABLE user_tbl
(
    userID          VARCHAR2(20 BYTE),
    uname           VARCHAR2(100 BYTE),
    pword           VARCHAR2(400 BYTE),
    email           VARCHAR2(50 BYTE),
    status          VARCHAR2(50 BYTE),
    createdBy       VARCHAR2(50 BYTE),
    createdDate     TIMESTAMP,
    modifiedBy      VARCHAR2(50 BYTE),
    modifiedDate    TIMESTAMP
);

CREATE TABLE images_tbl
( 
    userID          VARCHAR2(20 BYTE),
    photo           VARCHAR2(100 BYTE),
    avatar          VARCHAR2(100 BYTE),
    modifiedDate    TIMESTAMP
);

CREATE TABLE otp_tbl
(
    userID          VARCHAR2(20 BYTE),
    transID         VARCHAR2(100 BYTE),
    status          VARCHAR2(50 BYTE),
    modifiedDate    TIMESTAMP
);

CREATE TABLE audit_tbl
(
    userID          VARCHAR2(20 BYTE),
    inet            VARCHAR2(50 byte),
    login           TIMESTAMP,
    logout          TIMESTAMP
);

PROMPT "Commenting Tables"

-- Comment on tables
COMMENT ON COLUMN user_tbl.userID IS 'This is the personal primary identifier';
COMMENT ON COLUMN user_tbl.uname IS 'This is contact username this could be an alias';
COMMENT ON COLUMN user_tbl.pword IS 'This is contact set encrypted password';
COMMENT ON COLUMN user_tbl.email IS 'Electronic mail is a method of exchanging messages between people using electronic devices.';
COMMENT ON COLUMN user_tbl.status IS 'This is the state of the account either active or inactive.';
COMMENT ON COLUMN user_tbl.createdBy IS 'Audit column - indicates who creates the account.';
COMMENT ON COLUMN user_tbl.createdDate IS 'Audit column - date it was first created.';
COMMENT ON COLUMN user_tbl.modifiedBy IS 'Audit column - indicates who made last update.';
COMMENT ON COLUMN user_tbl.modifiedDate IS 'Audit column - date of last update.';
COMMENT ON TABLE user_tbl IS 'A user account is a location on a network server used to store a computer username, password, and other information. A user account allows or does not allow a user to connect to a network, another computer, or other shares.';
COMMENT ON COLUMN images_tbl.userID IS 'This is the personal primary identifier';
COMMENT ON COLUMN images_tbl.photo IS 'This is user photo image';
COMMENT ON COLUMN images_tbl.avatar IS 'This is user avatar image';
COMMENT ON COLUMN images_tbl.modifiedDate IS 'Audit column - date of last update.';
COMMENT ON TABLE images_tbl IS 'A user image is stored here.';
COMMENT ON COLUMN otp_tbl.userID IS 'This is the personal primary identifier';
COMMENT ON COLUMN otp_tbl.transID IS 'The unique transaction code';
COMMENT ON COLUMN otp_tbl.status IS 'This is the state of the account either valid or invalid';
COMMENT ON COLUMN otp_tbl.modifiedDate IS 'Audit column - date of last update.';
COMMENT ON TABLE otp_tbl IS 'A user image is stored here.';
COMMENT ON COLUMN audit_tbl.userID IS 'This is the personal primary identifier';
COMMENT ON COLUMN audit_tbl.inet IS 'This store user ip address';
COMMENT ON COLUMN audit_tbl.login IS 'This is the time a user login to the system';
COMMENT ON COLUMN audit_tbl.logout IS 'This is the time a user logout of the system';
COMMENT ON TABLE audit_tbl IS 'This is used to store user session.';

PROMPT "Setting Constraints"

-- Setting Primary Key
ALTER TABLE user_tbl ADD CONSTRAINT user_pk PRIMARY KEY (userID) USING INDEX;

-- Setting Check Constraint
ALTER TABLE user_tbl ADD CONSTRAINT userstatus_chk CHECK (status IN ('Active', 'Inactive')) ENABLE;
ALTER TABLE otp_tbl ADD CONSTRAINT otpstatus_chk CHECK (status IN ('Valid', 'Invalid')) ENABLE;

-- Setting Unique Key
ALTER TABLE user_tbl ADD CONSTRAINT user_unq UNIQUE (uname);
ALTER TABLE user_tbl ADD CONSTRAINT email_unq UNIQUE (email);

-- Setting Foreign Key
ALTER TABLE images_tbl ADD CONSTRAINT imagesuser_fk FOREIGN KEY (userID) REFERENCES user_tbl (userID) ON DELETE CASCADE;
ALTER TABLE otp_tbl ADD CONSTRAINT otpuser_fk FOREIGN KEY (userID) REFERENCES user_tbl (userID) ON DELETE CASCADE;
ALTER TABLE audit_tbl ADD CONSTRAINT audituser_fk FOREIGN KEY (userID) REFERENCES user_tbl (userID) ON DELETE CASCADE;

PROMPT "Creating Triggers"

-- Creating Triggers  
CREATE OR REPLACE TRIGGER user_trg BEFORE INSERT OR UPDATE ON user_tbl
    FOR EACH ROW
BEGIN
    IF INSERTING THEN
        SELECT 'UID' || LPAD((1 + ABS(MOD(dbms_random.random, 100000))), 6, '000') INTO :NEW.userID FROM DUAL;
        IF :NEW.uname IS NULL THEN RAISE_APPLICATION_ERROR(-20001, 'Username is mandatory and cannot be empty.'); END IF;
        IF :NEW.pword IS NULL THEN RAISE_APPLICATION_ERROR(-20001, 'Password is mandatory and cannot be empty.'); END IF;
        IF :NEW.email IS NULL THEN RAISE_APPLICATION_ERROR(-20001, 'Email is mandatory and cannot be empty.'); END IF;
        IF :NEW.status IS NULL THEN SELECT 'Active' INTO :NEW.status FROM DUAL; END IF;
        IF :NEW.createdBy IS NULL THEN SELECT USER INTO :NEW.createdBy FROM DUAL; END IF;
        IF :NEW.createdDate IS NULL THEN SELECT SYSTIMESTAMP INTO :NEW.createdDate FROM DUAL; END IF;
    ELSIF UPDATING THEN
        IF :NEW.uname IS NULL AND :OLD.uname IS NOT NULL THEN SELECT :OLD.uname INTO :NEW.uname FROM DUAL; END IF;
        IF :NEW.pword IS NULL AND :OLD.pword IS NOT NULL THEN SELECT :OLD.pword INTO :NEW.pword FROM DUAL; END IF;
        IF :NEW.email IS NULL AND :OLD.email IS NOT NULL THEN SELECT :OLD.email INTO :NEW.email FROM DUAL; END IF;
        IF :NEW.status IS NULL AND :OLD.status IS NOT NULL THEN SELECT :OLD.status INTO :NEW.status FROM DUAL; END IF;
        IF :NEW.modifiedBy IS NULL THEN SELECT USER INTO :NEW.modifiedBy FROM DUAL; END IF;
        IF :NEW.modifiedDate IS NULL THEN SELECT SYSTIMESTAMP INTO :NEW.modifiedDate FROM DUAL; END IF;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER images_trg BEFORE INSERT OR UPDATE ON images_tbl
    FOR EACH ROW
BEGIN
    IF INSERTING THEN
        IF :NEW.userID IS NULL THEN RAISE_APPLICATION_ERROR(-20001, 'User identity is mandatory and cannot be empty.'); END IF;
        IF :NEW.modifiedDate IS NULL THEN SELECT SYSTIMESTAMP INTO :NEW.modifiedDate FROM DUAL; END IF;
    ELSIF UPDATING THEN
        IF :NEW.userID IS NULL AND :OLD.userID IS NOT NULL THEN SELECT :OLD.userID INTO :NEW.userID FROM DUAL; END IF;
        IF :NEW.photo IS NULL THEN SELECT :OLD.photo INTO :NEW.photo FROM DUAL; END IF;
        IF :NEW.avatar IS NULL THEN SELECT :OLD.avatar INTO :NEW.avatar FROM DUAL; END IF;
        IF :NEW.modifiedDate IS NULL THEN SELECT SYSTIMESTAMP INTO :NEW.modifiedDate FROM DUAL; END IF;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER otp_trg BEFORE INSERT OR UPDATE ON otp_tbl
    FOR EACH ROW
BEGIN
    IF INSERTING THEN
        IF :NEW.userID IS NULL THEN RAISE_APPLICATION_ERROR(-20001, 'User identity is mandatory and cannot be empty.'); END IF;
        IF :NEW.transID IS NULL THEN SELECT dbms_random.String('X', 6)INTO :NEW.transID FROM DUAL; END IF;
        IF :NEW.status IS NULL THEN SELECT 'Valid' INTO :NEW.status FROM DUAL; END IF;
        IF :NEW.modifiedDate IS NULL THEN SELECT SYSTIMESTAMP INTO :NEW.modifiedDate FROM DUAL; END IF;
    ELSIF UPDATING THEN
        IF :NEW.userID IS NULL AND :OLD.userID IS NOT NULL THEN SELECT :OLD.userID INTO :NEW.userID FROM DUAL; END IF;
        IF :NEW.transID IS NULL THEN SELECT dbms_random.String('X', 6)INTO :NEW.transID FROM DUAL; END IF;
        IF :NEW.status IS NULL THEN SELECT 'Invalid' INTO :NEW.status FROM DUAL; END IF;
        IF :NEW.modifiedDate IS NULL THEN SELECT SYSTIMESTAMP INTO :NEW.modifiedDate FROM DUAL; END IF;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER audit_trg BEFORE INSERT OR UPDATE ON audit_tbl
    FOR EACH ROW
BEGIN
    IF INSERTING THEN
        IF :NEW.userID IS NULL THEN RAISE_APPLICATION_ERROR(-20001, 'User identity is mandatory and cannot be empty.'); END IF;
        IF :NEW.login IS NULL THEN SELECT SYSTIMESTAMP INTO :NEW.login FROM DUAL; END IF;
    ELSIF UPDATING THEN
        IF :NEW.userID IS NULL AND :OLD.userID IS NOT NULL THEN SELECT :OLD.userID INTO :NEW.userID FROM DUAL;
		ELSE RAISE_APPLICATION_ERROR(-20001, 'User identity is mandatory and cannot be empty.'); END IF;
        IF :NEW.logout IS NULL THEN SELECT SYSTIMESTAMP INTO :NEW.logout FROM DUAL; END IF;
    END IF;
END;
/

PROMPT "Enabling Triggers"

-- Enable Triggers
ALTER TRIGGER user_trg ENABLE;
ALTER TRIGGER images_trg ENABLE;
ALTER TRIGGER otp_trg ENABLE;
ALTER TRIGGER audit_trg ENABLE;

show errors
/

PROMPT "End of User Account Schema Creation."