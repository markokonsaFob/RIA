1. What Is RIHA Test Automation project?
1. How the project structure looks like?
1. What are the preconditions?
1. Step-By-Step guide for beginners
1. How to run tests with Cify Framework?
1. How to configure tests with Cify Framework?
1. How to add new tests?
1. How to setup Jenkins Job for Test Automation?
1. Learn more about Cify Framework

## What Is RIHA Test Automation project?
RIHA Test automation project is part of a government tender (Hanketeade 187228 Baaskomponentide kvaliteedijuhtimise ja testimise raamleping 2017-2020).

Goal is to automate specific scenarios specified by client.
 
## How the project structure looks like?

### Project structure overview
```
    /
    |-- build
    |   |-- cify
    |       |-- log
    |-- gradle
    |   |-- wrapper
    |-- src
    |   |-- test
    |       |-- groovy
    |           |-- impl
    |           |-- steps
    |       |-- resources
    |           |-- features
    |           |-- introduction
    |-- build.gradle
    |-- local.properties
    |-- capabilities.json
    |-- configuration.json
    |-- data.json
    |-- README.md
```

**build/cify/log/** - Project logs  
**gradle/wrapper/** - Gradle wrapper  
**src/test/groovy/impl** - Implementation layer, client specific implementation  
**src/test/groovy/steps** - Cucumber step definitions  
**src/test/resources/features** - Cucumber Feature files  
**src/test/resources/introduction** - Sample images for README    
**local.properties** - Project parameters for local execution  
**capabilities.json** - Device list for full execution  
**configuration.json** - Device information for local right-click-and-play execution  
**data.json** - Client specific test data for automation scripts.

## What are the preconditions?

* Java 8
* Chrome is installed
* IntelliJ IDEA (or similar IDE)

## Step-By-Step guide for beginners

### Run clean build to make sure that everything is okay

Just to make sure that everything is in order run gradle clean build task  

**On Mac OS X**  
``./gradlew build`` 
 
**On Windows**  
``gradlew build``  

### Download plugins for IDEA

* Gherkin
* Cucumber for Java
* Cucumber for Groovy

### Add run configuration to IDE

* Open Run/Debug configurations on IntelliJ or Android Studio
* Select Cucumber for Java from defaults
* Set **src/test/groovy/steps** as a Glue
* Press OK

![Run/Debug configuration](src/test/resources/introduction/rundebugconf.png)

## How to run tests with Cify Framework?

Options for running tests with Cify Framework:
* Right click and play (plug-and-play)
* Run from command line (cify-runner)

Default device configuration is (configuration.json): 

```
{
  "videoRecord": true,
  "videoDir": "build/cify/videos/",
  "capabilities": {
    "browser": {
      "capability": "chrome"
    }
  }
}

```

### Plug-And-Play

#### Precondition for plug-and-play

Plug-and-play will use **configuration.json** for configuration file.

#### Steps

1. Navigate to Cucumber Feature file that you would like to run
1. Find Scenario you would like to run
1. Right click on Scenario/Right click on Feature
1. Click Run

### With Cify Runner

Cify funner will use **capabilities.json** and **.properties** files for configuration.

Runner gives multiple options to trigger tests and test suites.

Gradle wrapper is used in the project. We can trigger gradle tasks like following:

* ./gradlew <task> (on Unix-like platforms such as Linux and Mac OS X)
* gradlew <task> (on Windows using the gradlew.bat batch file)

#### Run specific cucumber tag

Running the command below will trigger all scenarios with add tag and exclude all TODO tags.

``
  ./gradlew cucumber -PfeatureDirs=src/test/resources/features -PgluePackages=src/test/groovy/steps -Ptags=@add,~@TODO
``

#### Run with properties file

Users can specify all the parameters required for Cify Runner from properties file.  
Running with properties file:

``
 ./gradlew cucumber -Penv=fileName
``

NB! If file name is "local.properties" then user should enter only "local".

#### Run specific capabilities JSON

Sometimes there are needed multiple capabilities files, one for each suite. Then user can specify with command line parameter which capabilities file to use.

``
    ./gradlew cucumber -PcapabilitiesFilePath=src/test/resources/capabilities/someCapabilities.json
``

## How to configure tests with Cify Framework?

### Default configuration

#### Default browser

Default browser is Chrome

### How to configure?

#### Local run (Right click and play on scenario)
User can change **configuration.json** to any capability which is needed.  
Example of configuration.json file  
```
{
   "videoRecord": false,
   "videoDir": "build/cify/videos/",
   "capabilities": {
     "android": {
       "capability": "android",
       "deviceName": "Android",
       "version": "ANY"
     },
     "browser": {
       "capability": "firefox"
     },
     "ios": {
       "capability": "iphone",
       "deviceName": "iPhone",
       "version": "ANY"
     }
   }
 }
```

**videoRecord** - Enable web driver video recording.  
**videoDir** - Directory where videos are saved. With right click and run. Runs triggered with gradle have different spot.  
**capabilities** - Are used when user right clicks on scenarios or feature and press run. Capability with given category is taken and triggered.  

#### Suites and CI Systems

User can specify test output location by changing path values from **local.properties** or create completely different properties file.
```
cucumberPlugins=pretty,json:build/cify/reports/json/,junit:build/cify/reports/junit/

Values after ":" sign are paths.
```

#### Configuring application URL

Application or environment URL can be changed from **data.json** file. This file can be created from Jenkins job as well, to give more flexibility.

### Capabilities (Runner)

Capabilities file is in JSON format and defines capabilities for suite. Users can pass parameters to devices with capabilities json file.

File contains two objects:
* defaults
* set

#### Defaults 

Defaults is a optional parameter in capabilities json. User can define capabilities for 3 device categories (browser, android, iOS). If default is defined for one category then it will be added to every capability variation (if not defined in the set). 

#### Set

Set is a list of capabilities to test against. User can define as much capabilities for each device category as needed. Runner will create variations that every capability is tested with every other capabilities from other category.  

Valid capability file structure:
```
 {
   "set": {
     "browser": [
       {
         "capability": "chrome",
         "name": "Chrome OS X"
       },
       {
         "capability": "firefox",
         "name": "Firefox OS X"
       },
       {
         "capability": "opera",
         "name": "Opera OS X"
       },
       {
         "capability": "safari",
         "name": "Safari OS X"
       }
     ]
   }
 }
```
In this case there would be 4 different suites. One for each device in the list.

## How to add new tests?

### Cucumber
Cucumber features are located in src/test/resources/features folder.  
Users can add features like default cucumber requires.  
Add **@TODO** tag to feature or scenario when it's not implemented.  

```
@TODO
Scenario: Adding information system description without name field
    When user clicks on a add new button
    Then user should be on form view
    When user enters "Cify" into SHORTNAME field
    And user enters "https://github.com/fobsolutions/cify-framework" into DOCUMENTATION field
    And user clicks save button
    Then user should be on form view
    And NAME field should be INCORRECT
    And SHORTNAME field should be CORRECT
    And DOCUMENTATION field should be CORRECT
```

### Step definitions

Step definitions are located in **src/test/groovy/steps** folder.  
Users can add step definitions like default cucumber requires.

```
When(~/^user enters "([^"]*)" into ([^"]*) field$/) { String input, Field field ->
    ActionsImpl.getFormActions().enterInto(input, field)
}
```

### Implementation

Create a action class for page.

```
class FormActions implements ActionsDesktopWeb {

    Device device
    FormPage formPage

    FormActions(Device device) {
        this.device = device
        this.formPage = new FormPage(device)
    }

    @Title("Enter string into specified field")
    void enterInto(String input, Field field) {
        getInput(field).clear()
        sendKeys(getInput(field), input)
        TestDataManager.setTestData(field as String, input)
    }

    @Title("Click save button")
    void clickOnSaveButton() {
        click(formPage.getSaveButton())
    }

    @Title("Is input correct")
    boolean isInputCorrect(Field field) {
        !getFormGroup(field).getAttribute("class").contains(formPage.HAS_ERROR)
    }

    @Title("Is form view visible")
    boolean isFormViewVisible() {
        isDisplayed(device, formPage.nameInputLocator) &&
                isDisplayed(device, formPage.shortNameInputLocator) &&
                isDisplayed(device, formPage.documentationInputLocator)
    }
}
```

Add new class to ActionsImpl class.

```
    /**
     * Gets Form actions for current device
     * @return FormActions class instance
     */
    static FormActions getFormActions() {
        Device device = DeviceManager.getInstance().getActiveDevice()
        new FormActions(device)
    }

```


### Page Objects

Page objects hold all the elements that tests need.

```
class FormPage {

    /**
     * Framework elements
     */
    private Device device

    /**
     * Form elements
     */
    static By nameInputLocator = By.id("name")
    static By shortNameInputLocator = By.id("short-name")
    static By documentationInputLocator = By.id("documentation")
    static By saveButtonLocator = By.className("btn-default")
    static By formGroupLocator = By.className("form-group")

    static final String HAS_ERROR = "has-error"

    FormPage(Device device) {
        this.device = device
    }

    WebElement getNameInput() {
        device.getDriver().findElement(nameInputLocator)
    }

    WebElement getShortNameInput() {
        device.getDriver().findElement(shortNameInputLocator)
    }

    WebElement getDocumentationInput() {
        device.getDriver().findElement(documentationInputLocator)
    }

    WebElement getSaveButton() {
        device.getDriver().findElement(saveButtonLocator)
    }

    List<WebElement> getFormGroups() {
        device.getDriver().findElements(formGroupLocator)
    }
}

```

## How to setup Jenkins Job for Test Automation?

Cify Runner can be used in Jenkins as a default Gradle project.  

### Preconditions

1. Jenkins with version 2.23+
1. Gradle plugin installed on Jenkins

### Step-By-Step guide

1. Create freestyle project
1. Check "Delete workspace before build starts" in Build Environment
1. Add build step "Invoke Gradle script"
1. Select use gradle wrapper
1. Insert "cucumber" with all the parameters you need into Tasks input

``
Example: cucumber -Penv=demo -PfarmUrl=http://localhost:4444/wd/hub
``

### Cucumber HTML reports with Cify Runner

Users can use default Cucumber HTML report.

Cucumber reports plugin can be found in: [Cucumber reports plugin](https://github.com/jenkinsci/cucumber-reports-plugin)

Cucumber reports are saved by default to **build/cify/reports**


### Demonstrative pictures about Jenkins setup

![String inputs](src/test/resources/introduction/string_input.png)

![Build Environment](src/test/resources/introduction/build_environment.png)

![Build Capabilities](src/test/resources/introduction/build_capabilities.png)

![Build properties](src/test/resources/introduction/build_properties.png)

![Post Build](src/test/resources/introduction/post_build.png)


## Learn more about Cify Framework

**Cify Framework**  
Framework is responsible for managing communication with devices, and handling device actions (click, touch, tap, fillIn, sendKeys etc.) independently from device platform.  
Learn more from [GitHub](https://github.com/fobsolutions/cify-framework)  
**Cify Runner**  
Runner is responsible for parameters management, test configuration and test execution.  
Learn more from [GitHub](https://github.com/fobsolutions/cify-runner)    
**Cify Device Farm**  
Device Farm provides cross platform Selenium grid nodes.  
Learn more from [GitHub](https://github.com/fobsolutions/cify-device-farm)    