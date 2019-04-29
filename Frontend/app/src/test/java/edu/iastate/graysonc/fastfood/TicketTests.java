package edu.iastate.graysonc.fastfood;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import edu.iastate.graysonc.fastfood.database.entities.Ticket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TicketTests {
    @Test
    public void testConstructor() {
        GoogleSignInAccount sign = mock(GoogleSignInAccount.class);
        doReturn("Demo@test.com").when(sign).getEmail();
        Ticket ticket = new Ticket(sign.getEmail(), "What's a junit??", "Food");
        Mockito.validateMockitoUsage();
        assertNotNull(ticket);
    }

    @Test
    public void testMutatorAndGetters() {

        GoogleSignInAccount sign = mock(GoogleSignInAccount.class);
        doReturn("Demo@test.com").when(sign).getEmail();
        Ticket ticket = new Ticket(sign.getEmail(), "What's a junit??", "Food");
        assertNull(ticket.getDate());
        assertEquals("Food", ticket.getCategory());
        assertEquals("What's a junit??", ticket.getIssue());
        assertNull(ticket.getDate());
        assertNull(ticket.getAdminId());
        assertEquals(ticket.getTicketId(),0);
        Mockito.validateMockitoUsage();

    }

}
