package net.novafactory.uiautomator;

import android.view.KeyEvent;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/**
 * <pre>
 *     UiAutomator test LaunchSetting
 * </pre>
 *
 * @author nova
 * @since 2014. 8. 6.
 */
public class LaunchSettings extends UiAutomatorTestCase {

    /**
     * Play Store launch test.
     *
     * @throws UiObjectNotFoundException
     */
    public void testLaunch() throws UiObjectNotFoundException {

        getUiDevice().pressHome();
        UiObject allAppsButton = new UiObject(new UiSelector().description("Apps"));
        allAppsButton.clickAndWaitForNewWindow();

        UiObject appsTab = new UiObject(new UiSelector().text("Apps"));
        appsTab.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.setAsHorizontalList();

        UiObject settingsApp = appViews.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()),
                "Play Store");
        settingsApp.clickAndWaitForNewWindow();

        UiObject settingsValidation = new UiObject(new UiSelector().packageName("com.android.vending"));
        assertTrue("Unable to detect Play Store", settingsValidation.exists());
    }

    /**
     * TaskStalker app search test.
     *
     * @throws UiObjectNotFoundException
     */
    public void testSearchApp() throws UiObjectNotFoundException {
        if (!getUiDevice().getCurrentPackageName().contains("com.android.vending")) {
            testLaunch();
        }

        UiObject search = new UiObject(new UiSelector().clickable(true).descriptionContains("Search"));

        String inputText = search.getText();
        search.click();
        for (int i = 0, n = inputText.length(); i < n; i++) {
            getUiDevice().pressKeyCode(KeyEvent.KEYCODE_BACKSLASH);
        }
        search.setText("TaskStalker");
        getUiDevice().pressEnter();
    }

}
