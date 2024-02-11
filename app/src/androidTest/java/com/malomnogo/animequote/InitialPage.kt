package com.malomnogo.animequote

import android.widget.FrameLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

class InitialPage : AbstractPage() {

    override fun checkVisible() {
        super.checkVisible()
        onView(
            allOf(
                withId(R.id.mainTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(FrameLayout::class.java))
            )
        ).check(matches(not(isDisplayed())))
    }
}