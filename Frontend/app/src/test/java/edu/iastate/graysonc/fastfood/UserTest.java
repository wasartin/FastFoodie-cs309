package edu.iastate.graysonc.fastfood;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Date;

import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.Ticket;
import edu.iastate.graysonc.fastfood.database.entities.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class UserTest {
    @Mock
    User user;

    @Test
    public void testConstructor() {
        user = mock(User.class);
        doReturn(new Date()).when(user).getLastRefresh();
        user = new User("a", "b", null);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testAccessors() {
        GoogleSignInAccount sign = mock(GoogleSignInAccount.class);
        doReturn("Demo@test.com").when(sign).getEmail();
        User user = new User(sign.getEmail(), "admin", null);
        assertEquals(user.getEmail(), sign.getEmail());
        assertEquals("admin", user.getType());
        assertNull(user.getLastRefresh());
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testMutators() {
        GoogleSignInAccount sign = mock(GoogleSignInAccount.class);
        doReturn("Demo@test.com").when(sign).getEmail();
        User user = new User(null, null, null);
        user.setEmail(sign.getEmail());
        user.setType("admin");
        user.setLastRefresh(new Date());
        assertNotNull(user.getEmail());
        assertNotNull(user.getType());
        assertNotNull(user.getLastRefresh());
        Mockito.validateMockitoUsage();
    }
}
