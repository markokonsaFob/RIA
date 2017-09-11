package impl.form.actions

import impl.RIHATestException
import impl.TestDataManager
import impl.form.Field
import impl.form.pages.FormPage
import io.cify.framework.actions.ActionsDesktopWeb
import io.cify.framework.annotations.Title
import io.cify.framework.core.Device
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebElement

/**
 * Created by FOB Solutions
 */
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

    /**
     * Gets form group div for specified field
     * @param field
     * @return form-group element
     */
    private WebElement getFormGroup(Field field) {
        List<WebElement> formGroups = formPage.getFormGroups()
        if (!formGroups.isEmpty()) {
            return formGroups.find { WebElement group ->
                try {
                    switch (field) {
                        case Field.NAME:
                            group.findElement(formPage.nameInputLocator)
                            return group
                        case Field.SHORTNAME:
                            group.findElement(formPage.shortNameInputLocator)
                            return group
                        case Field.DOCUMENTATION:
                            group.findElement(formPage.documentationInputLocator)
                            return group
                        default:
                            throw new RIHATestException("Unknown form field with name $field")
                    }
                } catch (NoSuchElementException ignored) {
                    // This field is not correct
                }
            }
        } else {
            throw new RIHATestException("Form is not visible correctly!")
        }
    }

    /**
     * Gets user input for Field
     * @param field
     * @return Field WebElement
     */
    private WebElement getInput(Field field) {
        switch (field) {
            case Field.NAME:
                return formPage.getNameInput()
            case Field.SHORTNAME:
                return formPage.getShortNameInput()
            case Field.DOCUMENTATION:
                return formPage.getDocumentationInput()
            default:
                throw new RIHATestException("Unknown form field with name $field")
        }
    }
}
