package com.github.bjarneh.simple.handlers;

import junit.framework.TestCase;

/**
 * Make sure the String's hashCode does what's expected.
 *
 * @version 1.0
 * @author  bjarneh@ifi.uio.no
 */

public class DispatchHandlerTest extends TestCase {

    public void testHashCodeSanity(){
        assertTrue("HEAD".hashCode() == DispatchHandler.HEAD);
        assertTrue("GET".hashCode() == DispatchHandler.GET);
        assertTrue("POST".hashCode() == DispatchHandler.POST);
        assertTrue("PUT".hashCode() == DispatchHandler.PUT);
        assertTrue("DELETE".hashCode() == DispatchHandler.DELETE);
        assertTrue("TRACE".hashCode() == DispatchHandler.TRACE);
        assertTrue("OPTIONS".hashCode() == DispatchHandler.OPTIONS);
        assertTrue("CONNECT".hashCode() == DispatchHandler.CONNECT);
        assertTrue("PATCH".hashCode() == DispatchHandler.PATCH);
    }

}
