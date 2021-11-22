/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.ui.robots

import android.net.Uri
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.uiautomator.UiSelector
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.mozilla.fenix.R
import org.mozilla.fenix.helpers.TestAssetHelper
import org.mozilla.fenix.helpers.TestHelper.packageName
import org.mozilla.fenix.helpers.click

/**
 * Implementation of Robot Pattern for the recently closed tabs menu.
 */

class RecentlyClosedTabsRobot {

    fun waitForListToExist() =
        mDevice.findObject(UiSelector().resourceId("$packageName:id/recently_closed_list"))
            .waitForExists(
                TestAssetHelper.waitingTime
            )

    fun verifyRecentlyClosedTabsMenuView() = assertRecentlyClosedTabsMenuView()

    fun verifyEmptyRecentlyClosedTabsList() = assertEmptyRecentlyClosedTabsList()

    fun verifyRecentlyClosedTabsPageTitle(title: String) = assertRecentlyClosedTabsPageTitle(title)

    fun verifyRecentlyClosedTabsUrl(expectedUrl: Uri) = assertPageUrl(expectedUrl)

    fun clickDeleteRecentlyClosedTabs() = recentlyClosedTabsDeleteButton().click()

    class Transition {
        fun clickOpenInNewTab(interact: BrowserRobot.() -> Unit): BrowserRobot.Transition {
            recentlyClosedTabsPageTitle().click()

            BrowserRobot().interact()
            return BrowserRobot.Transition()
        }
    }
}

private fun assertRecentlyClosedTabsMenuView() {
    onView(
        allOf(
            withText("Recently closed tabs"),
            withParent(withId(R.id.navigationToolbar))
        )
    )
        .check(
            matches(withEffectiveVisibility(Visibility.VISIBLE))
        )
}

private fun assertEmptyRecentlyClosedTabsList() =
    onView(
        allOf(
            withId(R.id.recently_closed_empty_view),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
        )
    )
        .check(
            matches(withText("No recently closed tabs here"))
        )

private fun assertPageUrl(expectedUrl: Uri) = onView(
    allOf(
        withId(R.id.url),
        withEffectiveVisibility(
            Visibility.VISIBLE
        )
    )
)
    .check(
        matches(withText(Matchers.containsString(expectedUrl.toString())))
    )

private fun recentlyClosedTabsPageTitle() = onView(
    allOf(
        withId(R.id.title),
        withText("Test_Page_1")
    )
)

private fun assertRecentlyClosedTabsPageTitle(title: String) {
    recentlyClosedTabsPageTitle()
        .check(
            matches(withEffectiveVisibility(Visibility.VISIBLE))
        )
        .check(
            matches(withText(title))
        )
}

private fun recentlyClosedTabsDeleteButton() =
    onView(
        allOf(
            withId(R.id.overflow_menu),
            withEffectiveVisibility(
                Visibility.VISIBLE
            )
        )
    )
