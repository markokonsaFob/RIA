package impl.form.pages

import io.cify.framework.core.Device
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

/**
 * Created by FOB Solutions
 */
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
