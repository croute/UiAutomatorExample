UiAutomatorExample
==================

This repository is example for `UiAutomator` testing.

* Related post - [Android UiAutomator Test](http://novafactory.net/archives/3161)

## Creating test cases ##

### Creating a java project ###

Creating a `JAVA` project. Not Android project.

### Copying libraries and dependecy settings ###

Copying android.jar and uiautomator.jar to Project directory.

![Copying jars](http://novafactory.net/wp-content/uploads/2014/08/copy_jar.png)

### Creating test codes ###

```java
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

```

## Build, Deploy and Run ##

### Creating a build.xml(build configuration) ###


```
<android-sdk>/tools/android create uitest-project -n <name> -t <target-id> -p <path>
```

* `name` is project name.
* `target-id` is id of target api for test.
* `path` is project directory path.



Check Target id
``` 
$ android list targets
```

### Build ###

Setting your `ANDROID_HOME` env variable.

```
# Windows
> set ANDROID_HOME=<path_to_your_sdk>
 
# UNIX(Mac..)
$ export ANDROID_HOME=<path_to_your_sdk>
```

Build

```java
$ ant build
```

### Deploy ###

Using Adb push command.

```
$ adb push <path_to_output_jar> /data/local/tmp/
```
example

```
$ adb push ./bin/UiAutomatorExample.jar /data/local/tmp/
439 KB/s (997 bytes in 0.002s)
```

### Run ###

```
$ adb shell uiautomator runtest <jar> -c <class>
```

* `jar`: Created jar for test.
* `class`: Test class.

example

```
$ adb shell uiautomator runtest UiAutomatorExample.jar -c net.novafactory.uiautomator.LaunchSettings
```

## Sample Video ##

http://www.youtube.com/watch?v=tehwOUCzSpM



