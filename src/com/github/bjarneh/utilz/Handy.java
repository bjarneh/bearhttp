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

package com.github.bjarneh.utilz;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

import java.net.URI;
import java.util.Map;
import java.util.HashMap;

/**
 * Utility functions.
 *
 * Functions that could be useful all over the place.
 *
 * @author  bjarneh@ifi.uio.no
 * @version 1.0
 */

public class Handy{

    /**
     * Return true if it looks like we are running on Windows.
     *
     * @return true if we are running on Windows
     */
    public static boolean onWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("windows");
    }

    /**
     * Write bytes from one stream to anoter.
     *
     * @param fi  where bytes are read
     * @param fo  where bytes are written
     * @param max size of the buffer used
     */
    public static void pipe(InputStream fi, OutputStream fo, int max)
        throws IOException
    {
        int got = -1;
        byte[] b = new byte[max];

        for( got = fi.read(b); got > 0; got = fi.read(b) ){
            fo.write(b, 0, got);
        }
    }

    /**
     * Write bytes from one stream to anoter.
     *
     * #Handy.pipe with a default value buffer of 4096 bytes
     *
     * @param fi where bytes are read
     * @param fo where bytes are written
     */
    public static void pipe(InputStream fi, OutputStream fo)
        throws IOException
    {
        pipe(fi, fo, 4096);
    }

    /**
     *
     * Convert a path-name to a valid system path-name.
     *
     * @param path a path-name separated by '/'
     * @return a path-name separated by systems path-separator
     */
    public static String fromSlash(String path){
        return path.replace("/", System.getProperty("file.separator"));
    }

    /**
     * Return the question from an URI as a map of key value pairs.
     *
     * @param  uri the URI in question, get it?
     * @return a map containing key value pairs from URI#getQuery
     */
    public static Map<String, String> uriQuery(URI uri){

        String question = uri.getQuery();

        if(question == null){ return null; }

        Map<String, String> map = new HashMap<String, String>();

        String[] keyValuePairs = question.split("&");
        for(String kv: keyValuePairs){
            String[] keyValue = kv.split("=");
            map.put(keyValue[0], keyValue[1]);
        }

        return map;
    }


    /**
     * Returns file endings of strings.
     *
     * @param path path name to a file
     * @return suffix of path name (.txt .pdf)
     */
    public static String suffix(String path){
        if( path == null ){ return null; }
        int lastDot = path.lastIndexOf('.');
        if( lastDot < 0 || lastDot == (path.length() -1)){
            return null;
        }
        return path.substring(lastDot+1, path.length());
    }
}
