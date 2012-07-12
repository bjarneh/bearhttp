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

package com.github.bjarneh.simple.handlers.cache;

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
import com.github.bjarneh.simple.handlers.FileServHandler;

/**
 * Extends {@link FileServHandler} with some caching.
 *
 * @author bjarneh@ifi.uio.no
 * @version 1.0
 */

public class CachedFileServHandler extends FileServHandler {

    protected final long MAX_SIZE = 5 *1000 * 1000; // 5 Million bytes
    HashMap<String, CachedFile> map;

    public CachedFileServHandler(String rootDir) throws IOException {
        super(rootDir);
        map = new HashMap<String, CachedFile>();
    }

    @Override
    public void handleFile(HttpExchange x, File file, boolean body)
        throws IOException
    {

        InputStream reqestBody = new FileInputStream(file);
        OutputStream responseBody;
        Headers responseHeaders = x.getResponseHeaders();

        if(file.canRead()){

            responseHeaders.set("Content-Type", mime.type(file));

            if( body ){

                long size = file.length();
                responseHeaders.set("Content-Length", size + "");
                x.sendResponseHeaders(HttpURLConnection.HTTP_OK, size);

                responseBody = x.getResponseBody();

                if( isCached( file ) ){
                    reqestBody = getInputStream( file );
                    System.out.printf(" from cache: %s\n", file.getName());
                }else{
                    if( size < MAX_SIZE ){
                        map.put(file.getCanonicalPath(), new CachedFile(file));
                        reqestBody = getInputStream( file );
                    }else{
                        reqestBody = new FileInputStream(file);
                    }
                }

                Handy.pipe(reqestBody, responseBody);
                reqestBody.close();

            }else{
                x.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
            }

        }else{
            x.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, -1);
        }

    }

    boolean isCached(File file) throws IOException {
        return map.containsKey(file.getCanonicalPath());
    }

    boolean smallEnough(File file){
        return file.length() < MAX_SIZE;
    }

    InputStream getInputStream(File file) throws IOException {
        CachedFile cachedFile = map.get(file.getCanonicalPath());
        cachedFile.resetInputStream();
        return cachedFile.getInputStream();
    }

}
