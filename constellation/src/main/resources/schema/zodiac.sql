
/******************************************************************************
 * This piece of work is to enhance constellation project functionality.      *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      zodiac.sql                                                      *
 * Created:   13/02/2023, 21:30                                               *
 * Modified:  13/02/2023, 21:40                                               *
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

PROMPT "Creating Constellation Schema."
-------------------------------------
--  Connect and run the following  --
-------------------------------------

PROMPT "Creating Tables"

-- Create Tables
CREATE TABLE zodiac_tbl
(
    zodiac        VARCHAR2(50 BYTE),
    dateRange     VARCHAR2(50 BYTE),
    currentDay    VARCHAR2(50 BYTE),
    narrative     VARCHAR2(4000 BYTE),
    affinity      VARCHAR2(50 BYTE),
    mood          VARCHAR2(50 BYTE),
    colour        VARCHAR2(50 BYTE),
    luckyNumber   VARCHAR2(50 BYTE),
    luckyTime     VARCHAR2(50 BYTE),
    modifiedBy    VARCHAR2(50 BYTE),
    modifiedDate  TIMESTAMP
);

PROMPT "Commenting Tables"

-- Comment on tables
COMMENT ON TABLE zodiac_tbl IS 'Profile information for list of daily horoscope based on signs.';
COMMENT ON COLUMN zodiac_tbl.zodiac IS 'The zodiac is an area of the sky that extends approximately 8° north or south (as measured in celestial latitude) of the ecliptic, the apparent path of the Sun across the celestial sphere over the course of the year.';
COMMENT ON COLUMN zodiac_tbl.dateRange IS 'A number of dates that includes a particular start and finish date and all dates in between.';
COMMENT ON COLUMN zodiac_tbl.currentDay IS 'The current date means the date today or the date when something will happen.';
COMMENT ON COLUMN zodiac_tbl.narrative IS 'Your Zodiac sign, or star sign, reflects the position of the sun when you were born. With its strong influence on your personality, character, and emotions, your sign is a powerful tool for understanding yourself and your relationships and of course, your sign can show you the way to an incredible life.';
COMMENT ON COLUMN zodiac_tbl.affinity IS 'Your Zodiac sign, or star sign, reflects the position of the sun when you were born. With its strong influence on your personality, character, and emotions, your sign is a powerful tool for understanding yourself and your relationships and of course, your sign can show you the way to an incredible life.';
COMMENT ON COLUMN zodiac_tbl.mood IS 'This is likely the mood based on the daily horoscope.';
COMMENT ON COLUMN zodiac_tbl.colour IS 'This is the favourite colour based on the daily horoscope.';
COMMENT ON COLUMN zodiac_tbl.luckyNumber IS 'This is the day lucky number based on daily horoscope.';
COMMENT ON COLUMN zodiac_tbl.luckyTime IS 'This is the incarnated lucky time of the day based on daily horoscope.';
COMMENT ON COLUMN zodiac_tbl.modifiedBy IS 'Audit column - indicates who made last update.';
COMMENT ON COLUMN zodiac_tbl.modifiedDate IS 'Audit column - date of last update.';

PROMPT "Setting Constraints"

-- Setting Unique Key
ALTER TABLE zodiac_tbl ADD CONSTRAINT zodiac_unq UNIQUE (zodiac);

-- Setting Check Constraint
ALTER TABLE zodiac_tbl ADD CONSTRAINT zodiac_chk CHECK (zodiac IN('Aries', 'Taurus', 'Gemini', 'Cancer', 'Leo', 'Virgo', 'Libra', 'Scorpio','Sagittarius', 'Capricorn', 'Aquarius', 'Pisces')) ENABLE;

PROMPT "Creating Triggers"

-- Creating Triggers  
CREATE OR REPLACE TRIGGER zodiac_trg BEFORE INSERT OR UPDATE ON zodiac_tbl
    FOR EACH ROW
BEGIN
    IF :NEW.modifiedBy IS NULL THEN SELECT USER INTO :NEW.modifiedBy FROM DUAL; END IF;
    IF :NEW.modifiedDate IS NULL THEN SELECT SYSTIMESTAMP INTO :NEW.modifiedDate FROM DUAL; END IF;
END;
/

PROMPT "Enabling Triggers"

-- Enable Triggers
ALTER TRIGGER zodiac_trg ENABLE;

PROMPT "Insert Records"
-- Insert country records
INSERT INTO zodiac_tbl (zodiac) VALUES ('Aries');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Taurus');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Gemini');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Cancer');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Leo');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Virgo');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Libra');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Scorpio');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Sagittarius');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Capricorn');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Aquarius');
INSERT INTO zodiac_tbl (zodiac) VALUES ('Pisces');

COMMIT;

show errors
/

PROMPT "End of Constellation Creation."