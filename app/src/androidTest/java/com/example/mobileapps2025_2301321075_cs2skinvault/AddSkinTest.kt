package com.example.mobileapps2025_2301321075_cs2skinvault

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.adapter.SkinAdapter
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class AddSkinTest {
    @Test
    fun addSkin() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            onView(withId(R.id.btnAddSkin)).perform(click())

            onView(withId(R.id.etSkinName)).perform(typeText("EspressoSkin"), closeSoftKeyboard())
            onView(withId(R.id.etWeapon)).perform(typeText("M4A1"), closeSoftKeyboard())
            onView(withId(R.id.etRarity)).perform(typeText("Rare"), closeSoftKeyboard())
            onView(withId(R.id.etPrice)).perform(typeText("99.99"), closeSoftKeyboard())

            onView(withId(R.id.btnAddSkin)).perform(click())

            onView(withId(R.id.rvSkins))
                .perform(
                    RecyclerViewActions.scrollTo<SkinAdapter.VH>(
                        hasDescendant(withText(containsString("EspressoSkin")))
                    )
                )
        }
    }
}