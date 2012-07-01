package com.github.bjarneh.ant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.bjarneh.utilz.Handy;

/**
 *
 * Adds a Bash stub to an executable jar file.
 *
 * This is used in an Ant task to create a Bash script
 * which launches the executable jar file that the
 * task 'jar' produces.
 *
 * @author  bjarneh@ifi.uio.no
 * @version 1.0
 */

public class JScript{

    private String stub;

    public JScript(){ makeStub(); }

    private void makeStub(){

        StringBuilder sb = new StringBuilder();
        sb.append("#!/bin/bash\n\n");
        sb.append("# [ auto generated code ]\n\n");
        sb.append("# This file consists of a Bash stub with an\n");
        sb.append("# excetutable jar file attached to it, for more\n");
        sb.append("# info: http://github.com/bjarneh/bearhttp\n\n");
        sb.append("function die() {\n");
        sb.append("  echo \"$1\"\n");
        sb.append("  exit 1\n");
        sb.append("}\n\n");
        sb.append("# Taken from Debian Developers Reference Chapter 6\n");
        sb.append("function pathfind() {\n");
        sb.append("  OLDIFS=\"$IFS\"\n");
        sb.append("  IFS=:\n");
        sb.append("  for p in $PATH; do\n");
        sb.append("    if [ -x \"$p/$*\" ]; then\n");
        sb.append("      IFS=\"$OLDIFS\"\n");
        sb.append("      return 0\n");
        sb.append("    fi\n");
        sb.append("  done\n");
        sb.append("  IFS=\"$OLDIFS\"\n");
        sb.append("  return 1\n");
        sb.append("}\n\n");
        sb.append("pathfind \"java\"");
        sb.append("|| die \"[ERROR] could not find: java in \\$PATH\"\n\n");
        sb.append("exec java -jar $0 \"$@\"\n\n\n");

        this.stub = sb.toString();
    }


    /**
     * Adds a shell script stub to an executable jar.
     *
     *
     * @param jar path to executable jar file.
     * @param script desired path to executable bash scipt
     */
    public void create(String jar, String script) throws IOException {

        File jarFile = new File(jar);

        if(! jarFile.isFile()){
            throw new IOException("File not found: "+ jar);
        }
        if(! jarFile.canRead()){
            throw new IOException("Cannot read: " + jar);
        }

        File scriptFile = new File(script);
        FileOutputStream ostream = new FileOutputStream(scriptFile);
        // write shell script stub to script file
        ostream.write(stub.getBytes());

        FileInputStream istream = new FileInputStream(jarFile);

        // pipe content of jarFile file to scriptFile
        Handy.pipe(istream, ostream);

        istream.close();
        ostream.close();

        // make script executable for all
        scriptFile.setExecutable(true);
    }

    public static void main(String[] args) throws IOException {

        if( args.length != 2 ){
            System.out.println(" JScript requires 2 arguments ");
            System.out.println(" 1: path to executable jar");
            System.out.println(" 2: path to name of desired bash script");
        }else{

            JScript jscript = new JScript();

            if( Handy.onWindows() ){
                System.out.println(" JScript generates bash scripts, which");
                System.out.println(" only works on *nix i.e. Linux/Mac etc.");
                System.out.println(" You seem to be running Windows");
                System.exit(1);
                return; // unreachable bla-bla..
            }

            jscript.create(args[0], args[1]);
        }
    }
}
