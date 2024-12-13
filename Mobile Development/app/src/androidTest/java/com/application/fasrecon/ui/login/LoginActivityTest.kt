package com.application.fasrecon.ui.login

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.application.fasrecon.R
import com.application.fasrecon.ui.MainActivity
import com.application.fasrecon.util.IdlingResourceUtil
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(LoginActivity::class.java)
        IdlingRegistry.getInstance().register(IdlingResourceUtil.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(IdlingResourceUtil.countingIdlingResource)
    }

    @Test
    fun emptyFieldInput() {
        onView(withId(R.id.email_login_input))
            .perform(typeText(""), closeSoftKeyboard())

        onView(withId(R.id.btn_login))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.email_login_input))
            .check(matches(hasErrorText("Field Email cannot be blank")))
        onView(withId(R.id.email_login_input))
            .perform(typeText("fasrecon@gmail.com"), closeSoftKeyboard())

        onView(withId(R.id.password_login_input))
            .perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.btn_login))
            .check(matches(isDisplayed()))
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.password_login_input))
            .check(matches(hasErrorText("Field password cannot be blank")))
    }

    @Test
    fun wrongEmailFormatInput() {
        onView(withId(R.id.email_login_input))
            .perform(typeText("testlogin123"), closeSoftKeyboard())

        onView(withId(R.id.password_login_input))
            .perform(typeText("pw4"), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.email_login_input))
            .check(matches(hasErrorText("The email format is incorrect")))
    }

    @Test
    fun wrongPasswordFormatInput() {
        onView(withId(R.id.email_login_input))
            .perform(typeText("fasrecon@gmail.com"), closeSoftKeyboard())

        onView(withId(R.id.password_login_input))
            .perform(typeText("pw4"), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.password_login_input))
            .check(matches(hasErrorText("The password can\'t be less than 8 characters")))
    }

    @Test
    fun invalidEmail() {
        onView(withId(R.id.email_login_input))
            .perform(typeText("testdummy@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.password_login_input))
            .perform(typeText("fasrecon123"), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        onView(withText("Login Failed"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun invalidPassword() {
        onView(withId(R.id.email_login_input))
            .perform(typeText("fasrecon@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.password_login_input))
            .perform(typeText("testdummy123"), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        onView(withText("Login Failed"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loginSuccess() {
        Intents.init()

        onView(withId(R.id.email_login_input))
            .perform(typeText("fasrecon@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.password_login_input))
            .perform(typeText("fasrecon123"), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        onView(withText("Ok"))
            .perform(click())

        intended(hasComponent(MainActivity::class.java.name))

        Intents.release()
    }
}