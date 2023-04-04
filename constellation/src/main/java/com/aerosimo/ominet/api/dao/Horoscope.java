/******************************************************************************
 * This piece of work is to enhance constellation project functionality.      *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Horoscope.java                                                  *
 * Created:   25/03/2023, 02:08                                               *
 * Modified:  25/03/2023, 02:11                                               *
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

import com.aerosimo.ominet.app.Aztro;
import com.aerosimo.ominet.core.Connect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class Horoscope {

    private static final Logger log = LogManager.getLogger(Horoscope.class.getName());
    static String response;
    static String sql;
    static Connection con = Connect.dbase();
    static CallableStatement stmt;
    static JSONObject result;
    public static String updateZodiac() {
        String[] signs;
        signs = new String[]{"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
                "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
        sql = "{call constellation_pkg.setHoroscope(?,?,?,?,?,?,?,?,?,?,?)}";
        for (String sign : signs) {
            result = Aztro.getDailyHoroscope(sign.toLowerCase());
            try {
                stmt = con.prepareCall(sql);
                stmt.setString(1, sign);
                stmt.setString(2, result.getString("date_range"));
                stmt.setString(3, result.getString("current_date"));
                stmt.setString(4, result.getString("description"));
                stmt.setString(5, result.getString("compatibility"));
                stmt.setString(6, result.getString("mood"));
                stmt.setString(7, result.getString("color"));
                stmt.setString(8, result.getString("lucky_number"));
                stmt.setString(9, result.getString("lucky_time"));
                stmt.setString(10, "Aztro");
                stmt.registerOutParameter(11, Types.VARCHAR);
                stmt.execute();
                log.info("Successfully write " + sign + " daily horoscope update from Aztro to database");
                response = "Success";
            }catch(Exception err){
                response = "Fail";
                log.error("Horoscope service failed with adaptor error " + err);
            }
        }
        return response;
    }
}