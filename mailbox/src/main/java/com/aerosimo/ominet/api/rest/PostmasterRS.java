/******************************************************************************
 * This piece of work is to enhance mailbox project functionality.            *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      PostmasterRS.java                                               *
 * Created:   23/03/2023, 18:37                                               *
 * Modified:  23/03/2023, 18:37                                               *
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

package com.aerosimo.ominet.api.rest;

import com.aerosimo.ominet.com.Mailer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mailbox/v1")
public class PostmasterRS {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMail(@QueryParam("emailAddress") String emailAddress,
                             @QueryParam("emailSubject") String emailSubject,
                             @QueryParam("emailMessage") String emailMessage,
                             @QueryParam("emailFrom") String emailFrom,
                             @QueryParam("emailFiles") String emailFiles){
        String Output;
        if (emailAddress.isEmpty()) {
            Output = "Validation failed because emailAddress is a required field or not valid";
            return Response.status(Response.Status.BAD_REQUEST).entity(Output).build();
        }
        else if (emailSubject.isEmpty()) {
            Output = "Validation failed because emailSubject is a required field";
            return Response.status(Response.Status.BAD_REQUEST).entity(Output).build();
        }
        else if (emailMessage.isEmpty()) {
            Output = "Validation failed because emailMessage is a required field";
            return Response.status(Response.Status.BAD_REQUEST).entity(Output).build();
        }
        else if (emailFrom.isEmpty()) {
            Output = "Validation failed because emailFrom is a required field";
            return Response.status(Response.Status.BAD_REQUEST).entity(Output).build();
        }
        else {
            Output = Mailer.SendEmail(emailAddress,emailSubject,emailMessage,emailFiles,emailFrom);
            return Response.status(Response.Status.OK).entity(Output).build();
        }
    }
}