package com.borneo.api.automation.scripts.inspectionAPI;

import com.borneo.api.automation.scripts.Borneo_Actions;

public class InspectAPI_Actions {
    public final InspectAPI inspectAPI;

    public InspectAPI_Actions(Borneo_Actions actions) {
        this.inspectAPI = new InspectAPI(actions);
    }
}
