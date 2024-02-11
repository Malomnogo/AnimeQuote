package com.malomnogo.animequote

import android.widget.Button
import android.widget.TextView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.*

class ErrorPage(private val message: String) : AbstractPage() {

    override fun checkVisible() {
        onView(
            allOf(
                withId(R.id.mainTextView),
                withText(message),
                isAssignableFrom(TextView::class.java)
            )
        )
            .check(matches(isDisplayed()))
            .check(matches(ColorMatcher("#FF0000")))
    }

    override fun clickNext() {
        onView(
            allOf(
                withId(R.id.nextButton),
                withText("Retry"),
                isAssignableFrom(Button::class.java)
            )
        ).perform(click())
    }
}