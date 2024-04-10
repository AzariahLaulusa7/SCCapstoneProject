# BreatheEasy

* **Description:** The Meditation app's main goal is to help users with their mental health by providing them with an easy-to-use resource in states of heightened emotion.
It provides several options that target different approaches for dealing with negative feelings like anxiety and panic.
* **For more details,** please visit our wiki pages:
    *  [**Project Description:**](https://github.com/SCCapstone/Garp/wiki/Project-Description) Provides a general description of our app and its features.
    *  [**User Stories:**](https://github.com/SCCapstone/Garp/wiki/User-Stories) The user stories for our 3 personas that covers common use cases for our app.
        * [**Persona 1:**](https://github.com/SCCapstone/Garp/wiki/Persona-1:-Adam-Quin) How our app can be a safe place for a college student.
        * [**Persona 2:**](https://github.com/SCCapstone/Garp/wiki/Persona-2:-Gustav-Schm%C3%B6rgenschnitzel) How our app can benefit a workaholic.
        * [**Persona 3:**](https://github.com/SCCapstone/Garp/wiki/Persona-3:-Sofia-Martin) How our app can help a single mother regain mental strength.
    * [**Design:**](https://github.com/SCCapstone/Garp/wiki/Design) This is the design of our app.
    * [**Requirements:**](https://github.com/SCCapstone/Garp/wiki/Requirements) These are the requirements of our app that includes required, desired, and aspirational features.
    * [**Architecture:**](https://github.com/SCCapstone/Garp/wiki/Architecture) This is the architecture of our app, including the app's code structure and the app's views.

## External Requirements
For running the app's code, you need to have [Android Studio](https://developer.android.com/studio) and a [Java JDK](https://www.oracle.com/java/technologies/downloads/#java8-windows) installed (the latest versions are recommended).

## Setup
To make the cloning, pulling, and pushing process easier, [GitHub Desktop](https://desktop.github.com/) is recommended but not required.
To clone the Garp repository in Android Studio follow these steps:
1. Open Android Studio
2. Open the **File** tab at the top left of the window. Press new and choose **Project from Version Control**.
3. Select **GitHub**, then login.
4. Copy the clone link from your repository, and paste it.
5. Select an appropriate folder location, and press **Clone**.
6. Open the Project. Finally, make sure to Gradle the project before building and running.
To clone a repository in a terminal, go to the folder you want to clone the repository to and follow these steps:
1. Type **Git clone URL**. Change the URL to the clone link from your repository.
2. Press enter to create a local clone. Make sure to Gradle the project before building and running.

## Running
After cloning the repository onto Android Studio, the project will take a while to load.
Once the project is finished loading, make sure to gradle the project first using the elephant icon on the top right corner.\
Then, there will be a side bar on the right of the screen. One of the tabs will say "**Device Manager**".\
On this screen, follow these steps:
1. Click on on the "**Device Manager**" tab and pick a device.
2. Choose a device or create a Device. (_Pixel API 34_ was used to test the game)
3. The device will pop-up, connected to the "**Running Devices**" tab. The "**Device Manager**" tab can be minimized.
4. Now, press the "**Run**" button, which is a green play button at the top of the screen, to run the code.

## Testing

### Test Directories
   * Behavioral Test directory:<br /> **Garp\app\src\androidTest\java\com\example\guarden\ReactionGameBehaviorTest.java**
   * Unit Test directory:<br /> **Garp\app\src\test\java\com\example\guarden\ReactionGameLogicTest.java**

### Testing Using Configurations
1. First, make sure the Garp Android Studio project is open. Then, Gradle the project.
3. On the left of the play button, you will see configurations and devices.
4. Press on the configuration button. Then, press edit configurations.
5. Add new configuration using the add button on the top left of the configurations window.
6. Press Android Instrumented Tests.
   * Name: "All Instrumented Tests"
   * Module: "Guarden.app.androidTest"
   * Test: "All in Module"
7. Next, add another new configuration. This time press Gradle.
   * Name: "All Unit Tests"
   * Run: Select all Unit tests
   * Gradle Project: "Garp"
8. Press ok. Now, you can select "All Unit Tests" or "All Instrumented Tests" in the configuration options. If you select the "All Instrumented Tests", you must also select a device to run the program on.
9. Press the green play button to build and run the tests.

### Testing Using Command line
1. Before running your tests, you need to Gradle, build, and run the Garp Android Studio project on a device via a USB or virtually.
2. You can open the terminal in the Garp Android Studio project or on your desktop terminal.

>[!NOTE]
>If you are using your desktop terminal, make sure you are in the correct directory. This should be "**<path_to_project>\Garp**".

3. Type the command "**_./gradlew test_**" and press enter. This will build and run all the unit tests.
4. After the unit tests are finished running, type the command "**_./gradlew cAT_**" and press enter. This will build and run all the instrumented/behavioral tests.

### Test Results
   * Once you are finished running all the tests, you can find your test results as an HTML or XML in your project file.
   * To find the Unit Test results, go to either of these paths:
      * HTML:<br /> "**<path_to_project>\Garp\app\build\reports\tests\testDebugUnitTest\index.html**"
      * XML:<br /> "**<path_to_project>\Garp\app\build\test-results\testDebugUnitTest\TEST-com.example.guarden.ReactionGameLogicTest.xml**"
   * To find the Behavioral Test results, go to either of these paths:
      * HTML:<br /> "**...\Garp\app\build\reports\androidTests\connected\debug\com.example.guarden.ReactionGameBehaviorTest.html**"
      * XML:<br /> "**<path_to_project>\Garp\app\build\outputs\androidTest-results\connected\debug\TEST-<device_value>-<project_value>-.xml**"
   * If you want to open the HTML files in a browser from Android Studio, then follow these steps:
      * Right click on the HTML file you wish to open.
      * Select "Open In".
      * Select "Open In Browser". Then, select the browser you wish to open the HTML file in.

>[!TIP]
> For more information on running tests in Android Studio, use this [link](https://developer.android.com/studio/test).

## Authors
**Adrian Luedicke:** <LUEDICKA@email.sc.edu>

**Joe Comiskey:** <COMISKEJ@email.sc.edu>

**Azariah Laulusa:** <LAULUSA@email.sc.edu>

**James MM Thurlow:** <JTHURLOW@email.sc.edu>

**Joseph Brancker:** <BRANCKER@email.sc.edu>
