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


package com.github.bjarneh.simple.code;

/**
 *
 * HTTP status codes.
 *
 * Not the full list of HTTP status codes, they will be added as needed
 * by handlers. Status code explanation are taken from wikipedia, link to
 * URL is included on this page.
 *
 * @author bjarneh@ifi.uio.no
 * @version 1.0
 * @see <a href="http://en.wikipedia.org/wiki/List_of_HTTP_status_codes">List of HTTP status codes</a>
 */

public class Status{

    // Standard response for successful HTTP requests. The actual response
    // will depend on the request method used. In a GET request, the
    // response will contain an entity corresponding to the requested
    // resource. In a POST request the response will contain an entity
    // describing or containing the result of the action.
    public static final int OK = 200;

    // This and all future requests should be directed to the given URI.
    //
    // GET /index.php HTTP/1.1
    // Host: www.example.org
    //
    // HTTP/1.1 301 Moved Permanently
    // Location: http://www.example.org/index.asp
    public static final int MOVED_PERMANENTLY = 301;

    // The request cannot be fulfilled due to bad syntax.
    public static final int BAD_REQUEST = 400;

    // Similar to 403 Forbidden, but specifically for use when
    // authentication is possible but has failed or not yet been
    // provided.The response must include a WWW-Authenticate header
    // field containing a challenge applicable to the requested resource.
    public static final int UNAUTHORIZED = 401;

    // The request was a legal request, but the server is refusing to
    // respond to it. Unlike a 401 Unauthorized response,
    // authenticating will make no difference.
    public static final int FORBIDDEN = 403;


    // The requested resource could not be found but may be available
    // again in the future. Subsequent requests by the client are
    // permissible.
    public static final int NOT_FOUND = 404;


    // A generic error message, given when no more specific message is
    // suitable.
    public static final int INTERNAL_SERVER_ERROR = 500;

    // The server either does not recognise the request method, or it
    // lacks the ability to fulfill the request.
    public static final int NOT_IMPLEMENTED = 501;

}
