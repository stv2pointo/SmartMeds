package com.stvjuliengmail.smartmeds;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.stvjuliengmail.smartmeds.activity.AddOrEditMyMedActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by shaht_000 on 4/19/2018.
 */

@RunWith(AndroidJUnit4.class)
public class AddOrEditMyMedTest {


    @Rule
    public ActivityTestRule<AddOrEditMyMedActivity> mAddEditRule =
            new ActivityTestRule<AddOrEditMyMedActivity>(AddOrEditMyMedActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    int _rxcui = 198316;
                    String _name = "Hydrochlorothiazide 25 MG / Triamterene 37.5 MG Oral Capsule";
                    String _imageUrl = "https://rximage.nlm.nih.gov/image/images/gallery/600/00781-2074-01_RXNAVIMAGE10_9D34CEF6.jpg";
                    Intent intent = new Intent(targetContext, AddOrEditMyMedActivity.class);
                    intent.putExtra("rxcui", _rxcui);
                    intent.putExtra("name", _name);
                    intent.putExtra("imageUrl", _imageUrl);
                    return intent;
                }
            };

    @Test
    public void searchAddAllDateTest() {
        onView(withId(R.id.etDosage)).perform(typeText("150mg"));
        onView(withId(R.id.etDirections)).perform(typeText("Take 2"));
        onView(withId(R.id.etDoctor)).perform(typeText("Dr. Smarthead"));
        onView(withId(R.id.etPharmacy)).perform(typeText("Wallgreens\n1243 New York Drive\nOrem, Utah"), closeSoftKeyboard());
        onView(withId(R.id.fabSave)).perform(click());
    }
}
