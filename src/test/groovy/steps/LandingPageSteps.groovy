package steps

import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import impl.ActionsImpl
import impl.RIHATestException
import impl.TestDataManager
import impl.form.Field
import io.cify.framework.core.Device
import io.cify.framework.core.DeviceCategory
import io.cify.framework.core.DeviceManager
import org.openqa.selenium.WebElement

/**
 * Created by FOB Solutions
 */

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

Given(~/^user opens RIHA website$/) { ->
    Device device = DeviceManager.getInstance().createDevice(DeviceCategory.BROWSER)
    device.openBrowser(TestDataManager.getTestDataFileContent().environment.URL as String)
}
Then(~/^user should be on the RIHA landing page$/) { ->
    if (!ActionsImpl.getLandingPageActions().isLandingPageVisible()) {
        throw new RIHATestException("User should be on the landing page!")
    }
}
When(~/^user clicks on a add new button$/) { ->
    ActionsImpl.getLandingPageActions().clickAddNewButton()
}

And(~/^newly added information system details should be visible$/) { ->
    String name = TestDataManager.getValue(Field.NAME as String)
    String shortName = TestDataManager.getValue(Field.SHORTNAME as String)
    String documentationURL = TestDataManager.getValue(Field.DOCUMENTATION as String)
    WebElement tableRow = ActionsImpl.getLandingPageActions().getTableRowByName(name)
    if (tableRow) {
        ActionsImpl.getLandingPageActions().isRowDataCorrect(tableRow, name, shortName, documentationURL)
    } else {
        throw new RIHATestException("There isn't row with name field $name")
    }
}

When(~/^user clicks on change button on newly added details$/) { ->
    String name = TestDataManager.getValue(Field.NAME as String)
    WebElement tableRow = ActionsImpl.getLandingPageActions().getTableRowByName(name)
    ActionsImpl.getLandingPageActions().clickOnChangeOnRow(tableRow)
}

When(~/^user clicks on delete button on newly added details$/) { ->
    String name = TestDataManager.getValue(Field.NAME as String)
    WebElement tableRow = ActionsImpl.getLandingPageActions().getTableRowByName(name)
    ActionsImpl.getLandingPageActions().clickOnDeleteOnRow(tableRow)
}

And(~/^information system description should be deleted$/) { ->
    String name = TestDataManager.getValue(Field.NAME as String)
    WebElement tableRow

    try {
        tableRow = ActionsImpl.getLandingPageActions().getTableRowByName(name)
    } catch (RIHATestException ignored) {
        // Didn't find row with specific title
    }

    if (tableRow != null) {
        throw new RIHATestException("Table row with name $name is not deleted correctly!")
    }
}

After("@clear_table") {

    if (!ActionsImpl.getLandingPageActions().isLandingPageVisible()) {
        ActionsImpl.getCoreActions().pressBack()
        ActionsImpl.getLandingPageActions().waitForLandingPageToBeVisible()
    }

    ActionsImpl.getLandingPageActions().getAllTableRows().each {
        ActionsImpl.getLandingPageActions().clickOnDeleteOnRow(it)
    }
}

After {
    DeviceManager.getInstance().quitAllDevices()
}