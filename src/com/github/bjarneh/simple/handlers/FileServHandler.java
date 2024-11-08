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

import java.util.Map;
import java.util.HashMap;
import java.net.URI;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import com.github.bjarneh.utilz.Handy;
import com.github.bjarneh.simple.mime.Content;

/**
 * Handles incoming HEAD and GET requests, it's a simple web server.
 *
 * NOTE: Any other http request (PUT,POST...) returns an message 
 * in the form of a plain text file. This class just implements
 * a handler, waiting for incoming requests etc is done by the
 * HttpServer found in the standard library. Typical usage:
 *
 * <pre>
 
   // port:    Listen for incoming request on specified port.
   // backlog: Number of incoming requests that can be queued, before
   //          we start dropping requests.

   HttpServer server;
   server = HttpServer.create(new InetSocketAddress(port), backlog);


   // root: Directory you would like to serve, note that the context
   //       argument is always "/". If you set root to "/tmp",
   //       "GET /file.txt"  would give you the file "/tmp/file.txt".

   server.createContext("/", new FileServHandler(root));

   // Choose what type of executor you would like to use, note
   // that 'null' gives you the default executor. Incoming requests
   // start up new threads, i.e. the executors are Java's built-in
   // schemes to start up asynchronous tasks in separate threads.

   server.setExecutor(Executors.newCachedThreadPool());
   server.start();


 * </pre>
 *
 * @author bjarneh@ifi.uio.no
 * @version 1.0
 */

public class FileServHandler extends DispatchHandler {

    public Content mime;
    public String  root;

    String htmlHeader = "<!DOCTYPE html>\n<html>\n <body>\n";
    String htmlFooter = " </body>\n</html>\n";


    public FileServHandler(String rootDir) throws IOException {
        mime = new Content();
        root = new File(rootDir).getPath();
    }

    public void handleGET(HttpExchange exchange) throws IOException {
        handleHeadGet(exchange, true);
    }

    public void handleHEAD(HttpExchange exchange) throws IOException {
        handleHeadGet(exchange, false);
    }

    void handleHeadGet(HttpExchange x, boolean body) throws IOException {

        String path;
        Headers responseHeaders;
        OutputStream responseBody;

        responseHeaders = x.getResponseHeaders();
        responseHeaders.set("Server", "Bearhttp 1.0");
        responseHeaders.set("Allow", "HEAD, GET");

        path = x.getRequestURI().getPath();
        File file = new File(root, path);


        if( file.isDirectory() && body ){

            handleDir( x, file );

        }else if( file.isFile() ){

            handleFile( x, file, body );

        }else{

            handle404( x, file, body );

        }

        x.close();

    }

    public void handle404(HttpExchange x, File file, boolean body)
        throws IOException
    {
        if( body ){

            OutputStream responseBody;
            Headers responseHeaders;

            StringBuilder sb = new StringBuilder();
            sb.append(htmlHeader);
            sb.append(" <pre>\n");
            sb.append("  404 file not found: ");
            sb.append(file.getName());
            sb.append("\n </pre>\n");
            sb.append(htmlFooter);

            byte[] bytes = sb.toString().getBytes();

            responseHeaders = x.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/html;charset=utf-8");
            responseHeaders.set("Content-Length", bytes.length + "");

            x.sendResponseHeaders(
                    HttpURLConnection.HTTP_NOT_FOUND, bytes.length);

            responseBody = x.getResponseBody();
            responseBody.write(bytes);
            responseBody.close();
        }

    }


    public void handleDir(HttpExchange x, File file) throws IOException {

        OutputStream responseBody;
        Headers responseHeaders;

        StringBuilder sb = new StringBuilder();
        sb.append(htmlHeader);
        sb.append("  <ul>\n"); // unordered list of files

        String[] files = file.list();

        String tmp;
        String fmt ="  <li><a href=\"%s\">%s</a></li>\n";
        
        for(String f: files){
            tmp = f;
            if(! file.getName().equals("/")){
                tmp = file.getName() + "/" + f;
            }
            sb.append( String.format(fmt, tmp, f) );
        }

        sb.append("  </ul>\n");
        sb.append(htmlFooter);

        byte[] bytes = sb.toString().getBytes();

        responseHeaders = x.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/html;charset=utf-8");
        responseHeaders.set("Content-Length", bytes.length + "");

        x.sendResponseHeaders(HttpURLConnection.HTTP_OK, bytes.length);

        responseBody = x.getResponseBody();
        responseBody.write(bytes);
        responseBody.close();

    }


    public void handleFile(HttpExchange x, File file, boolean body)
        throws IOException
    {
        OutputStream responseBody;
        Headers responseHeaders = x.getResponseHeaders();

        if(file.canRead()){

            responseHeaders.set("Content-Type", mime.type(file));

            if( body ){

                long size = file.length();
                responseHeaders.set("Content-Length", size + "");
                x.sendResponseHeaders(HttpURLConnection.HTTP_OK, size);

                responseBody = x.getResponseBody();
                FileInputStream reqestBody = new FileInputStream(file);
                Handy.pipe(reqestBody, responseBody);
                reqestBody.close();

            }else{
                x.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
            }

        }else{
            x.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, -1);
        }

    }



    // only GET and HEAD is implemented (not even fully) so any other
    // reqests will call this method, which will give some info at least.

    public void notImplemented(HttpExchange exchange) throws IOException {

        String  err = "Only HEAD and GET reqests are handled";

        Headers responseHeaders = exchange.getResponseHeaders();
        OutputStream responseBody = exchange.getResponseBody();  

        responseHeaders.set("Content-Type", "text/plain");
        responseHeaders.set("Content-Length", err.length() + "");
        responseHeaders.set("Server", "Bearhttp 1.0");
        responseHeaders.set("Allow", "HEAD, GET");

        exchange.sendResponseHeaders(
                HttpURLConnection.HTTP_NOT_IMPLEMENTED, err.length());

        responseBody = exchange.getResponseBody();
        responseBody.write(err.getBytes());
        responseBody.close();

        exchange.close();
    }

}
