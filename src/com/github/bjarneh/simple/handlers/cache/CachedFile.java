package com.github.bjarneh.simple.handlers.cache;

import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.github.bjarneh.utilz.Handy;

/**
 * Hold contents of file in byte array.
 *
 * @author bjarneh@ifi.uio.no
 * @version 1.0
 */

public class CachedFile{

    ByteArrayInputStream bytes;

    public CachedFile(File file) throws IOException {
        slurpFile(file);
    }

    private void slurpFile(File file) throws IOException {
        FileInputStream fi = new FileInputStream(file);
        ByteArrayOutputStream fo = new ByteArrayOutputStream();
        Handy.pipe(fi, fo);
        bytes = new ByteArrayInputStream(fo.toByteArray());
    }

    public void resetInputStream(){
        bytes.reset();
    }

    public InputStream getInputStream(){
        return bytes;
    }
}
