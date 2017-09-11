package impl.landing.pages

import io.cify.framework.core.Device
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

/**
 * Created by FOB Solutions
 */
class LandingPage {

    /**
     * Framework elements
     */
    Device device

    /**
     * Main elements
     */
    private static By addNewButton = By.className("btn-primary")
    private static By infosystemTable = By.id("infosystems-table")

    /**
     * Row elements
     */
    private static By tbody = By.tagName("tbody")
    private static By row = By.tagName("tr")
    public static By name = By.className("name")
    public static By shortName = By.className("short-name")
    public static By documentation = By.className("documentation")
    public static By edit = By.className("edit")
    public static By delete = By.className("delete")

    LandingPage(Device device) {
        this.device = device
    }

    WebElement getInformationTable() {
        device.getDriver().findElement(infosystemTable)
    }

    WebElement getAddNewButton() {
        device.getDriver().findElement(addNewButton)
    }

    List<WebElement> getTableRows() {
        getInformationTable().findElement(tbody).findElements(row)
    }
}
