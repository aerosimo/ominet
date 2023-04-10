
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

PROMPT "Creating Constellation Header Package"

-- Create Packages
CREATE OR REPLACE PACKAGE constellation_pkg
AS
    /* $Header: constellation_pkg. 1.0.0 13-FEB-23 21:40 Package
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
    Name: constellation_pkg
    Program Type: Package Specification
    Purpose: ADD/FIND/UPDATE/DELETE entity
    =================================================================================
    HISTORY
    =================================================================================
    | DATE 		| Owner 	| Activity
    =================================================================================
    | 13-FEB-23	| eomisore 	| Created initial script.|
    =================================================================================
    */

    -- Update constellation table
    PROCEDURE setHoroscope(
        i_zodiac IN zodiac_tbl.zodiac%TYPE,
        i_dateRange IN zodiac_tbl.dateRange%TYPE,
        i_currentDay IN zodiac_tbl.currentDay%TYPE,
        i_narrative IN zodiac_tbl.narrative%TYPE,
        i_affinity IN zodiac_tbl.affinity%TYPE,
        i_mood IN zodiac_tbl.mood%TYPE,
        i_colour IN zodiac_tbl.colour%TYPE,
        i_luckyNumber IN zodiac_tbl.luckyNumber%TYPE,
        i_luckyTime IN zodiac_tbl.luckyTime%TYPE,
        i_modifiedBy IN zodiac_tbl.modifiedBy%TYPE,
		o_response  OUT VARCHAR2);
		
    -- Find details from the aztro table
    PROCEDURE getHoroscope(
        i_zodiac IN zodiac_tbl.zodiac%TYPE,
        o_aztroList OUT SYS_REFCURSOR);

END constellation_pkg;
/

PROMPT "Creating Constellation Body Package"

-- Create Packages
CREATE OR REPLACE PACKAGE BODY constellation_pkg
AS
    /* $Body: constellation_pkg. 1.0.0 13-FEB-23 21:40 Package
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
    Name: constellation_pkg
    Program Type: Package Specification
    Purpose: ADD/FIND/UPDATE/DELETE entity
    =================================================================================
    HISTORY
    =================================================================================
    | DATE 		| Owner 	| Activity
    =================================================================================
    | 13-FEB-23	| eomisore 	| Created initial script.|
    =================================================================================
    */

    -- Update constellation table
    PROCEDURE setHoroscope(
        i_zodiac IN zodiac_tbl.zodiac%TYPE,
        i_dateRange IN zodiac_tbl.dateRange%TYPE,
        i_currentDay IN zodiac_tbl.currentDay%TYPE,
        i_narrative IN zodiac_tbl.narrative%TYPE,
        i_affinity IN zodiac_tbl.affinity%TYPE,
        i_mood IN zodiac_tbl.mood%TYPE,
        i_colour IN zodiac_tbl.colour%TYPE,
        i_luckyNumber IN zodiac_tbl.luckyNumber%TYPE,
        i_luckyTime IN zodiac_tbl.luckyTime%TYPE,
        i_modifiedBy IN zodiac_tbl.modifiedBy%TYPE,
		o_response  OUT VARCHAR2)
    AS
    BEGIN
        UPDATE zodiac_tbl
        SET dateRange = i_dateRange,
            currentDay = i_currentDay,
            narrative = i_narrative,
            affinity = i_affinity,
            mood = i_mood,
            colour = i_colour,
            luckyNumber = i_luckyNumber,
            luckyTime = i_luckyTime,
            modifiedBy = i_modifiedBy,
            modifiedDate = systimestamp
        WHERE zodiac = i_zodiac;
        o_response := 'Success';
        IF SQL%NOTFOUND THEN
            RAISE NO_DATA_FOUND;
        END IF;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN ROLLBACK;
        o_response := 'Unfortunately, no results matching your criteria ' || i_zodiac || ' were found.';
        WHEN OTHERS THEN ROLLBACK;
        o_response := SQLCODE || SUBSTR(SQLERRM, 1, 2000);
    END setHoroscope;

    -- Find details from the aztro table
    PROCEDURE getHoroscope(
        i_zodiac IN zodiac_tbl.zodiac%TYPE,
        o_aztroList OUT SYS_REFCURSOR)
    AS
    BEGIN
        IF i_zodiac IS NOT NULL OR LENGTH(TRIM(i_zodiac)) > 0 THEN
            OPEN o_aztroList FOR
                SELECT *
                FROM zodiac_tbl
                WHERE zodiac = i_zodiac;
        ELSE
            OPEN o_aztroList FOR
                SELECT * FROM zodiac_tbl order by dateRange;
        END IF;
    END getHoroscope;

END constellation_pkg;
/

PROMPT "Compiling Constellation Package"

ALTER PACKAGE constellation_pkg COMPILE PACKAGE;
ALTER PACKAGE constellation_pkg COMPILE BODY;
/