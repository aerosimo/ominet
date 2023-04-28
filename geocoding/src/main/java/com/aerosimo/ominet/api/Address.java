/******************************************************************************
 * This piece of work is to enhance geocoding project functionality.          *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Address.java                                                    *
 * Created:   26/04/2023, 19:03                                               *
 * Modified:  26/04/2023, 19:03                                               *
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

package com.aerosimo.ominet.api;

import com.aerosimo.ominet.core.Connect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class Address {

    private static final Logger log = LogManager.getLogger(Address.class.getName());

    static String response;

    public static String amendAddress(String userid, String addressType, String result){
        try{
            int i = result.indexOf("{");
            result = result.substring(i);
            JSONObject maps;
            maps = new JSONObject(result.trim());
            String sql;
            sql = "{call address_pkg.reviseaddress(?,?,?,?,?)}";
            Connection con;
            con = Connect.dbase();
            CallableStatement stmt;
            stmt = con.prepareCall(sql);
            stmt.setString(1, userid);
            stmt.setString(2, addressType);
            stmt.setString(3, maps.getString("lon"));
            stmt.setString(4, maps.getString("lat"));
            log.info("longitude : " + maps.getString("lon"));
            log.info("latitude : " + maps.getString("lat"));
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.execute();
            log.info("Successfully write address geocode details to database");
            response = "Success";
        }catch(Exception err){
            response = "Fail";
            log.error("Address service failed with adaptor error " + err);
        }
        return response;
    }
}