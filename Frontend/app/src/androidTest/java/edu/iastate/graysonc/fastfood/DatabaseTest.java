package edu.iastate.graysonc.fastfood;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.iastate.graysonc.fastfood.activities.MainActivity;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(JUnit4.class)
@LargeTest
public class DatabaseTest {

    private String stringToBetyped;

    @Rule
    public Activity<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso";
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        Espresso.onView(withId(R.id.editTextUserInput))
                .perform(ViewActions.typeText(stringToBetyped), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.changeTextBt)).perform(ViewActions.click());

        // Check that the text was changed.
        Espresso.onView(withId(R.id.textToBeChanged))
                .check(matches(withText(stringToBetyped)));
    }
}
