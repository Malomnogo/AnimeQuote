package com.malomnogo.animequote

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun scenarioTest() {
        val initialPage = InitialPage()
        initialPage.checkVisible()
        activityScenarioRule.scenario.recreate()
        initialPage.checkVisible()
        initialPage.clickGetNewQuote()

        val errorPage = ErrorPage(message = "No internet connection")
        errorPage.checkVisible()
        activityScenarioRule.scenario.recreate()
        errorPage.checkVisible()
        errorPage.clickRetry()

        val quotePage = QuotePage(quote = "Fake quote text")
        quotePage.checkVisible()
        activityScenarioRule.scenario.recreate()
        quotePage.checkVisible()
    }
}