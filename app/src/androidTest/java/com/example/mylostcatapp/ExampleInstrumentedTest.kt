package com.example.mylostcatapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mylostcatapp", appContext.packageName)

        @RunWith(AndroidJUnit4::class)
        class ExampleInstrumentedTest {
            @get:Rule
            var activityRule: ActivityScenarioRule<MainActivity> =
                ActivityScenarioRule(MainActivity::class.java)

            @Test
            fun useAppContext() {
                // Context of the app under test.
                val appContext = InstrumentationRegistry.getInstrumentation().targetContext
                assertEquals("com.example.lostcatsapp", appContext.packageName)

                Espresso.onView(ViewMatchers.withText("Authentication"))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


                Espresso.onView(withId(R.id.emailInputField))
                    .perform(ViewActions.typeText("lailalaila@mail.dk"))

                Espresso.onView(withId(R.id.passwordInputField)).perform(ViewActions.typeText("laila1"))

                Espresso.onView(withId(R.id.button_create_user)).perform(ViewActions.click())

                pause(2000)

                Espresso.onView(ViewMatchers.withText("The email address is already in use by another account."))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            }
            private fun pause(millis: Long) {
                try {
                    Thread.sleep(millis)
                    // https://www.repeato.app/android-espresso-why-to-avoid-thread-sleep/
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

    }
}