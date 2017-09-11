package impl.core

import io.cify.framework.core.Device

/**
 * Created by FOB Solutions
 */
class CoreActions {

    Device device

    CoreActions(Device device) {
        this.device = device
    }

    /**
     * Accepts web alerts
     */
    void acceptAlert() {
        device.getDriver().switchTo().alert().accept()
    }

    /**
     * Presses back on navigation bar
     */
    void pressBack() {
        device.getDriver().navigate().back()
    }
}
