package com.malomnogo.animequote

import android.widget.TextView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.*

class QuotePage(private val quote: String) : AbstractPage() {

    override fun checkVisible() {
        super.checkVisible()
        onView(
            allOf(
                withId(R.id.mainTextView),
                withText(quote),
                isAssignableFrom(TextView::class.java)
            )
        )
            .check(matches(isDisplayed()))
            .check(matches(ColorMatcher("#000000")))
    }
}