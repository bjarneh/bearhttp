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

package com.github.bjarneh.simple.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import java.util.concurrent.Executors;

import com.github.bjarneh.parse.options.Getopt;
import com.github.bjarneh.simple.handlers.FileServHandler;

/**
 *
 * Entry point of Bearhttp server.
 *
 * Parses command line options, initializes a new 
 * HttpServer with the FileServHandler.
 *
 * @author  bjarneh@ifi.uio.no
 * @version 1.0
 */

public class Bearhttp {

    // defaults
    protected int port = 9000;
    protected String root = "/";
    protected HttpServer server;


    public void parseArgs(String[] args){

        Getopt getopt = new Getopt();

        getopt.addBoolOption("-h -help --help");
        getopt.addFancyStrOption("-p --port");
        getopt.addFancyStrOption("-r --root");

        String[] rest = getopt.parse(args);

        if(getopt.isSet("-help")){ printHelpAndExit(); }
        if(getopt.isSet("-port")){ port = getopt.getInt("-port"); }

        if(getopt.isSet("-root")){
            root = getopt.get("-root");
        }else if(rest.length > 0){
            root = rest[0];
        }

    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 10);
        server.createContext("/", new FileServHandler(root));
        // server.setExecutor(null); gives default executors
        server.setExecutor(Executors.newCachedThreadPool());
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        server.start();
        System.out.printf("bearhttp: serving '%s' on port %d\n", root, port);
    }

    private final Thread shutdownHook = new Thread() {
        @Override public void run() {
            server.stop(0);
            System.out.println("\nbearhttp: stopped");
        }
    };

    private static void printHelpAndExit(){
        System.out.printf("\n bearhttp - server with no features\n\n");
        System.out.printf(" usage: bearhttp [OPTIONS] [ROOT]\n\n");
        System.out.printf(" options:\n\n");
        System.out.printf("  -h --help  :  print this menu and exit\n");
        System.out.printf("  -p --port  :  listen to port (default:9000)\n");
        System.out.printf("  -r --root  :  root directory (default: '/')\n\n");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        Bearhttp bearhttp = new Bearhttp();
        bearhttp.parseArgs( args );
        bearhttp.start();
    }
}
