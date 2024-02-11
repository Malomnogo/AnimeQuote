package com.malomnogo.animequote

import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.*

class InitialPage : AbstractPage() {

    override fun checkVisible() {
        onView(
            allOf(
                withId(R.id.mainTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(FrameLayout::class.java))
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
}