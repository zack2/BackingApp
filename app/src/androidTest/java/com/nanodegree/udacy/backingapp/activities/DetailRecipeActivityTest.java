package com.nanodegree.udacy.backingapp.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nanodegree.udacy.backingapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetailRecipeActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void detailRecipeActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.myRecyclerView),
                        withParent(allOf(withId(R.id.fragment_container),
                                withParent(allOf(withId(R.id.swipeRefresh),
                                        withParent(allOf(withId(R.id.coordinatorLayout),
                                                withParent(allOf(withId(android.R.id.content),
                                                        withParent(withId(R.id.action_bar_root)))))))))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.ingredientsRL), isDisplayed()));
        relativeLayout.perform(click());

        pressBack();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.stepsRecyclerView), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.linear_clic),
                        withParent(allOf(withId(R.id.linearLayoutCardContent),
                                withParent(allOf(withId(R.id.card_view),
                                        withParent(withId(R.id.scrollView))))))));
        linearLayout.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.linear_clic),
                        withParent(allOf(withId(R.id.linearLayoutCardContent),
                                withParent(allOf(withId(R.id.card_view),
                                        withParent(withId(R.id.scrollView))))))));
        linearLayout2.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.linear_clic),
                        withParent(allOf(withId(R.id.linearLayoutCardContent),
                                withParent(allOf(withId(R.id.card_view),
                                        withParent(withId(R.id.scrollView))))))));
        linearLayout3.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(965);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(allOf(withId(R.id.app_bar),
                                        withParent(allOf(withId(R.id.coordinatorLayout),
                                                withParent(allOf(withId(android.R.id.content),
                                                        withParent(withId(R.id.action_bar_root)))))))))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.stepsRecyclerView), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(9, click()));

        ViewInteraction linearLayout4 = onView(
                allOf(withId(R.id.linear_clic),
                        withParent(allOf(withId(R.id.linearLayoutCardContent),
                                withParent(allOf(withId(R.id.card_view),
                                        withParent(withId(R.id.scrollView))))))));
        linearLayout4.perform(scrollTo(), click());

        ViewInteraction linearLayout5 = onView(
                allOf(withId(R.id.linear_clic),
                        withParent(allOf(withId(R.id.linearLayoutCardContent),
                                withParent(allOf(withId(R.id.card_view),
                                        withParent(withId(R.id.scrollView))))))));
        linearLayout5.perform(scrollTo(), click());

        pressBack();

    }

}
