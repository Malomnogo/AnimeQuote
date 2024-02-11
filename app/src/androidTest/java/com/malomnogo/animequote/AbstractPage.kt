package com.malomnogo.animequote

import android.widget.Button
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf

abstract class AbstractPage {

    abstract fun checkVisible()

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