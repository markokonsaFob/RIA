package impl

import impl.core.CoreActions
import impl.form.actions.FormActions
import impl.landing.actions.LandingPageActions
import io.cify.framework.core.Device
import io.cify.framework.core.DeviceManager

/**
 * Created by FOB Solutions
 */
class ActionsImpl {

    /**
     * Gets core actions for current device
     * @return CoreActions class instance
     */
    static CoreActions getCoreActions() {
        Device device = DeviceManager.getInstance().getActiveDevice()
        new CoreActions(device)
    }

    /**
     * Gets Landing page actions for current device
     * @return LandingPageActions class instance
     */
    static LandingPageActions getLandingPageActions() {
        Device device = DeviceManager.getInstance().getActiveDevice()
        new LandingPageActions(device)
    }

    /**
     * Gets Form actions for current device
     * @return FormActions class instance
     */
    static FormActions getFormActions() {
        Device device = DeviceManager.getInstance().getActiveDevice()
        new FormActions(device)
    }
}
