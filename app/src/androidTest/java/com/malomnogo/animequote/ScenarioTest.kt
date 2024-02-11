package com.malomnogo.animequote

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.malomnogo.animequote.presentation.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * to run the test select
     * @see com.malomnogo.animequote.core.MockApp
     * in AndroidManifest.xml
     */

    @Test
    fun scenarioTest() {
        val initialPage = InitialPage()
        initialPage.checkVisible()
        activityScenarioRule.scenario.recreate()
        initialPage.checkVisible()
        initialPage.clickNext()

        val errorPage = ErrorPage(message = "No internet connection")
        errorPage.checkVisible()
        activityScenarioRule.scenario.recreate()
        errorPage.checkVisible()
        errorPage.clickNext()

        var quotePage = QuotePage(quote = "Fake quote text 1")
        quotePage.checkVisible()
        activityScenarioRule.scenario.recreate()
        quotePage.checkVisible()

        quotePage.clickNext()
        quotePage = QuotePage(quote = "Fake quote text 2")
        quotePage.checkVisible()
        activityScenarioRule.scenario.recreate()
        quotePage.checkVisible()

        quotePage.clickNext()
        errorPage.checkVisible()
        activityScenarioRule.scenario.recreate()
        errorPage.checkVisible()
    }
}