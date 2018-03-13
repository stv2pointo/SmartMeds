package com.stvjuliengmail.smartmeds;

/**
 * Created by Steven on 2/26/2018.
 */
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.stvjuliengmail.smartmeds.activity.SearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PillSearchTest {
    // creates activity before and destroys after
    @Rule
    public ActivityTestRule<SearchActivity> mSearchActivityRule =
            new ActivityTestRule<>(SearchActivity.class);

    // TODO: Use mock data ??
    // currently for these inputs the actual values are hard coded in
    // how do I make these right all the time
    // without recycling the same bad result?
    // I'm guessing by creating mock data, but how?

    @Test
    public void searchOnExactNameTest() {
        onView(withId(R.id.etName)).perform(typeText("Levoxyl"), closeSoftKeyboard());
        onView(withId(R.id.btnLoadList)).perform(click());
        onView(withId(R.id.recVwResultList)).check(new RecyclerViewItemCountAssertion(5));
    }

//    @Test
//    public void searchOnShortNameTest() {
//        onView(withId(R.id.etName)).perform(typeText("aa"), closeSoftKeyboard());
//        onView(withId(R.id.btnLoadList)).perform(click());
//        onView(withId(R.id.recVwResultList)).check(new RecyclerViewItemCountAssertion(0));
//    }

//    @Test
//    public void searchOnImprintTest() {
//        onView(withId(R.id.etImprint)).perform(typeText("dp"), closeSoftKeyboard());
//        onView(withId(R.id.btnLoadList)).perform(click());
//        onView(withId(R.id.recVwResultList)).check(new RecyclerViewItemCountAssertion(20));
//    }
//
//    @Test
//    public void searchOnBadImprintTest() {
//        onView(withId(R.id.etImprint)).perform(typeText("badImprint"), closeSoftKeyboard());
//        onView(withId(R.id.btnLoadList)).perform(click());
//        onView(withId(R.id.recVwResultList)).check(new RecyclerViewItemCountAssertion(0));
//    }
//
//    @Test
//    public void searchOnColorTest() {
//        onView(withId(R.id.colorSpinner)).perform(click());
//        onData(equalTo("Blue")).perform(click());
//        onView(withId(R.id.recVwResultList)).check(new RecyclerViewItemCountAssertion(20));
//    }
//
//    @Test
//    public void searchOnDefaultColorTest() {
//        onView(withId(R.id.colorSpinner)).perform(click());
//        onData(equalTo("Pill Color")).perform(click());
//        onView(withId(R.id.recVwResultList)).check(new RecyclerViewItemCountAssertion(0));
//    }
//
//    @Test
//    public void searchOnShapeTest() {
//        onView(withId(R.id.shapeSpinner)).perform(click());
//        onData(equalTo("Diamond")).perform(click());
//        onView(withId(R.id.recVwResultList)).check(new RecyclerViewItemCountAssertion(16));
//    }
//
//    @Test
//    public void searchOnDefaultShapeTest() {
//        onView(withId(R.id.shapeSpinner)).perform(click());
//        onData(equalTo("Pill Shape")).perform(click());
//        onView(withId(R.id.recVwResultList)).check(new RecyclerViewItemCountAssertion(0));
//    }
//
//    @Test
//    public void searchOnAllInputsTest() {
//        onView(withId(R.id.etName)).perform(typeText("Levoxyl"), closeSoftKeyboard());
//
//        onView(withId(R.id.etImprint)).perform(typeText("dp"), closeSoftKeyboard());
//
//        onView(withId(R.id.shapeSpinner)).perform(click());
//        onData(equalTo("Oval")).perform(click());
//
//        onView(withId(R.id.colorSpinner)).perform(click());
//        onData(equalTo("Turquoise")).perform(click());
//
//        onView(withId(R.id.recVwResultList)).check(new RecyclerViewItemCountAssertion(1));
//    }

}
