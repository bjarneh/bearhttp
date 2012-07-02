//  Copyright © 2012 bjarneh
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.github.bjarneh.simple.handlers;

import java.io.IOException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.github.bjarneh.simple.code.Status;

/**
 *
 * A base class HttpHandler, dispatches http requests based on type.
 *
 * I.e. a GET request calls handleGET, and a HEAD request calls
 * handleHEAD and so on. None of these function have an implementation,
 * other than returning a 'Not implemented' status, so it's made abstract.
 *
 * @version 1.0
 * @author  bjarneh@ifi.uio.no
 */

public abstract class DispatchHandler implements HttpHandler {

    // "REQEST".hashCode() i.e. "HEAD".hashCode() etc..
    static final int HEAD    = 2213344;
    static final int GET     = 70454;
    static final int POST    = 2461856;
    static final int PUT     = 79599;
    static final int DELETE  = 2012838315;
    static final int TRACE   = 80083237;
    static final int OPTIONS = -531492226;
    static final int CONNECT = 1669334218;
    static final int PATCH   = 75900968;

    // These values are hard-coded, but the hashCode function
    // of java.lang.String will hopefully never change.
    //
    // The Scala REPL makes hardcoding fun!
    //
    // scala> var h = List("HEAD","GET",...,"PATCH")
    // scala> h.foreach(x => printf("%s => %d\n", x, x.hashCode ))


    public DispatchHandler(){}

    public void handle(HttpExchange exchange) throws IOException {

        int req = exchange.getRequestMethod().toUpperCase().hashCode();
        
        switch(req){
            case HEAD:    handleHEAD(exchange); break;
            case GET:     handleGET(exchange); break;
            case POST:    handlePOST(exchange); break;
            case PUT:     handlePUT(exchange); break;
            case DELETE:  handleDELETE(exchange); break;
            case TRACE:   handleTRACE(exchange); break;
            case OPTIONS: handleOPTIONS(exchange); break;
            case CONNECT: handleCONNECT(exchange); break;
            case PATCH:   handlePATCH(exchange); break;
        }

    }

    public void handleHEAD(HttpExchange exchange) throws IOException {
        notImplemented(exchange);
    }
    public void handleGET(HttpExchange exchange) throws IOException {
        notImplemented(exchange);
    }
    public void handlePOST(HttpExchange exchange) throws IOException {
        notImplemented(exchange);
    }
    public void handlePUT(HttpExchange exchange) throws IOException {
        notImplemented(exchange);
    }
    public void handleDELETE(HttpExchange exchange) throws IOException {
        notImplemented(exchange);
    }
    public void handleTRACE(HttpExchange exchange) throws IOException {
        notImplemented(exchange);
    }
    public void handleOPTIONS(HttpExchange exchange) throws IOException {
        notImplemented(exchange);
    }
    public void handleCONNECT(HttpExchange exchange) throws IOException {
        notImplemented(exchange);
    }
    public void handlePATCH(HttpExchange exchange) throws IOException {
        notImplemented(exchange);
    }

    public void notImplemented(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(Status.NOT_IMPLEMENTED, -1);
        exchange.close();
    }
}
