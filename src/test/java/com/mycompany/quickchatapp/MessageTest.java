package com.mycompany.quickchatapp;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Message validation, ID/hash generation, send options,
 * and loop-based hash verification for Part 2 and Part 3.
 */
public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message();
        String result = msg.validateMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageLengthTooLongFailure() {
        Message msg = new Message();
        StringBuilder longMsg = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMsg.append("a");
        }
        String result = msg.validateMessageLength(longMsg.toString());
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
    }

    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message();
        String result = msg.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testRecipientCellFailure() {
        Message msg = new Message();
        String result = msg.checkRecipientCell("08575975889");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testMessageIDCreatedAndValidLength() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "1");
        assertNotNull(msg.getMessageID());
        assertTrue(msg.checkMessageID(msg.getMessageID()));
        assertEquals(10, msg.getMessageID().length());
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "1");
        msg.setMessageID("0012345678");
        String hash = msg.createMessageHash();
        assertEquals("00:0:HITONIGHT", hash);
    }

    @Test
    public void testMessageHashesInLoop() {
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?",
            "Hello team quick meeting today"
        };
        String[] expectedHashes = {
            "11:0:HITONIGHT",
            "22:1:HIPAYMENT",
            "33:2:HELLOTODAY"
        };

        for (int i = 0; i < messages.length; i++) {
            Message msg = new Message(i, "+27718693002", messages[i], "1");
            msg.setMessageID((i + 1) + "" + (i + 1) + "55667788");
            assertEquals(expectedHashes[i], msg.createMessageHash());
        }
    }

    @Test
    public void testSendMessageOptions() {
        Message sendMsg = new Message();
        assertEquals("Message successfully sent.", sendMsg.SentMessage("1"));
        assertEquals("Sent", sendMsg.getMessageStatus());

        Message disregardMsg = new Message();
        assertEquals("Press 0 to delete the message.", disregardMsg.SentMessage("2"));
        assertEquals("Disregarded", disregardMsg.getMessageStatus());

        Message storeMsg = new Message();
        assertEquals("Message successfully stored.", storeMsg.SentMessage("3"));
        assertEquals("Stored", storeMsg.getMessageStatus());
    }
}