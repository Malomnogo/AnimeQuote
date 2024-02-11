package com.malomnogo.animequote

import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*

abstract class AbstractPage {

    open fun checkVisible() {
        onView(
            allOf(
                withId(R.id.progressBar),
                isAssignableFrom(ProgressBar::class.java)
            )
        ).check(matches(not(isDisplayed())))

        onView(
            allOf(
                withId(R.id.nextButton),
                isAssignableFrom(Button::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(FrameLayout::class.java))
            )
        ).check(matches(isDisplayed()))
    }

    open fun clickNext() {
        onView(
            allOf(
                withId(R.id.nextButton),
                withText("New Quote"),
                isAssignableFrom(Button::class.java)
            )
        ).perform(click())
    }
}