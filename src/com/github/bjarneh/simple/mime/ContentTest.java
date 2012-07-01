package com.github.bjarneh.simple.mime;

import junit.framework.TestCase;
import java.io.IOException;

/**
 * Test whether mime-types are returned as expected.
 *
 * @version 1.0
 * @author  bjarneh@ifi.uio.no
 */

public class ContentTest extends TestCase {

    Content content;

    public void setUp(){
        try {
            content = new Content();
        }catch (IOException iox){
            throw new Error(iox);
        }
    }

    public void testMimeTypes(){

        String mime;

        // pdf
        mime = content.type("some/path/file.pdf");
        assertTrue(mime.equals("application/pdf"));

        // html
        // NOTE: ;charset=<DefaultCharSet> is added to mimetype
        mime = content.type("some/path/file.html");
        assertTrue(mime.startsWith("text/html"));
        mime = content.type("some/path/file.htm");
        assertTrue(mime.startsWith("text/html"));
        mime = content.type("some/path/file.xhtml");
        assertTrue(mime.equals("application/xhtml+xml"));

        // javascript
        mime = content.type("some/path/file.js");
        assertTrue(mime.startsWith("application/javascript"));

    }
}
