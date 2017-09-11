package steps

import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import impl.ActionsImpl
import impl.RIHATestException
import impl.form.Field
import impl.form.InputStatus

/**
 * Created by FOB Solutions
 */

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

Then(~/^user should be on form view$/) { ->
    if (!ActionsImpl.getFormActions().isFormViewVisible()) {
        throw new RIHATestException("User should be on the form view. Correct Field are not visible!")
    }
}
When(~/^user enters "([^"]*)" into ([^"]*) field$/) { String input, Field field ->
    ActionsImpl.getFormActions().enterInto(input, field)
}

When(~/^user enters unique ID into ([^"]*) field$/) { Field field ->
    String uniqueID = UUID.randomUUID().toString()
    ActionsImpl.getFormActions().enterInto(uniqueID, field)
}

And(~/^user clicks save button$/) { ->
    ActionsImpl.getFormActions().clickOnSaveButton()
}
And(~/^([^"]*) field should be ([^"]*)/) { Field field, InputStatus status ->
    if (status == InputStatus.INCORRECT && ActionsImpl.getFormActions().isInputCorrect(field) ||
            status == InputStatus.CORRECT && !ActionsImpl.getFormActions().isInputCorrect(field)) {
        throw new RIHATestException("Input with name $field should be $status, but it's not!")
    }
}