package impl.landing.actions

import impl.ActionsImpl
import impl.Constants
import impl.RIHATestException
import impl.landing.pages.LandingPage
import io.cify.framework.actions.ActionsDesktopWeb
import io.cify.framework.annotations.Title
import io.cify.framework.core.Device
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition

/**
 * Created by FOB Solutions
 */
class LandingPageActions implements ActionsDesktopWeb {

    private Device device
    private LandingPage landingPage

    LandingPageActions(Device device) {
        this.device = device
        this.landingPage = new LandingPage(device)
    }

    @Title("Is RIHA landing page visible")
    boolean isLandingPageVisible() {
        try {
            isDisplayed(landingPage.getInformationTable())
        } catch (NoSuchElementException ignored) {
            false
        }
    }

    @Title("Wait for landing page to be visible")
    void waitForLandingPageToBeVisible() {
        waitForCondition(device, new ExpectedCondition<Boolean>() {
            Boolean apply(WebDriver d) {
                try {
                    isLandingPageVisible()
                } catch (ignore) {
                    false
                }
            }
        }, Constants.DEFAULT_TIMEOUT)
    }

    @Title("Click on a add new button")
    void clickAddNewButton() {
        click(landingPage.getAddNewButton())
    }

    @Title("Is newly added row data correct")
    boolean isRowDataCorrect(WebElement row, String name, String shortName, String documentationURL) {
        row.findElement(landingPage.name).getText() == name &&
                row.findElement(landingPage.shortName).getText() == shortName &&
                row.findElement(landingPage.documentation).getText() == documentationURL
    }

    @Title("Click on change on specific row")
    void clickOnChangeOnRow(WebElement row) {
        click(row.findElement(landingPage.edit))
    }

    @Title("Click on delete on specific row")
    void clickOnDeleteOnRow(WebElement row) {
        int currentCount = landingPage.getTableRows().size()
        click(row.findElement(landingPage.delete))
        ActionsImpl.getCoreActions().acceptAlert()

        waitForCondition(device, new ExpectedCondition<Boolean>() {
            Boolean apply(WebDriver d) {
                try {
                    landingPage.getTableRows().size() < currentCount
                } catch (ignore) {
                    false
                }
            }
        }, Constants.DEFAULT_TIMEOUT)

    }

    @Title("Get all table rows")
    List<WebElement> getAllTableRows() {
        landingPage.getTableRows()
    }

    @Title("Get table row for specific name")
    WebElement getTableRowByName(String name) {
        List<WebElement> rows = landingPage.getTableRows()
        if (!rows.isEmpty()) {
            landingPage.getTableRows().find { WebElement row ->
                try {
                    row.findElement(landingPage.name).getText().equalsIgnoreCase(name)
                } catch (NoSuchElementException ignored) {
                    // Row do not have row with specified name
                }
            }
        } else {
            throw new RIHATestException("Information system table is empty!")
        }
    }
}
