package com.borneo.api.automation.scripts;

import com.borneo.api.automation.framework.API_Client;
import com.borneo.api.automation.scripts.inspectionAPI.InspectAPI_Actions;
import com.borneo.api.automation.scripts.utils.DataProviderUtils;
import com.borneo.api.automation.scripts.utils.ResponseValidator;

public class Borneo_Actions {
    public API_Client borneoMasterClient;
    public InspectAPI_Actions inspectAPI_actions;

    public ResponseValidator responseValidator;
    public DataProviderUtils dataProviderUtils;

    public Borneo_Actions() {
        this.borneoMasterClient = new API_Client("https://dlp.googleapis.com/v2");
        inspectAPI_actions = new InspectAPI_Actions(this);

        this.responseValidator = new ResponseValidator(borneoMasterClient);
        this.dataProviderUtils = new DataProviderUtils();
    }
}
