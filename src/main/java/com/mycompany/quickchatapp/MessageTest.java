package com.mycompany.quickchatapp;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Executes test cases specified in Task 2 & Task 3 rubric.
 */
public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message();
        String result = msg.validateMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message();
        String result = msg.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "1");
        msg.setMessageID("0012345678");
        String hash = msg.createMessageHash();
        assertEquals("00:0:HITONIGHT", hash);
    }
}