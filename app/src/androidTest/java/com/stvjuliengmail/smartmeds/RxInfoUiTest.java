package com.stvjuliengmail.smartmeds;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Steven on 2/26/2018.
 */

public class RxInfoUiTest {
    // creates activity  with intent extras before and destroys after
    @Rule
    public ActivityTestRule<RxInfoActivity> rxInfoActivityRule =
            new ActivityTestRule<RxInfoActivity>(RxInfoActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, RxInfoActivity.class);
                    result.putExtra("rxcui", 866083);
                    result.putExtra("name", "buspirone hydrochloride 10 MG Oral Tablet");
                    result.putExtra("imageUrl", "https:\\/\\/rximage.nlm.nih.gov\\/image\\/images\\/gallery\\/original\\/00093-0054-01_RXNAVIMAGE10_7226B965.jpg");
                    return result;
                }
            };

    @Test
    public void clickOnMyMedsSaveButtonTest() {
        onView(withId(R.id.fabSaveMyMeds)).perform(click());
    }

    @Test
    public void clickOnInteractionsButtonTest() {
        onView(withId(R.id.btnInteractions)).perform(click());
    }
}
