package com.borneo.api.automation.scripts.inspectionAPI;

import com.borneo.api.automation.framework.API_Client;
import com.borneo.api.automation.scripts.Borneo_Actions;
import com.borneo.api.automation.scripts.requestPojo.InspectAPIRequest;
import com.borneo.api.automation.scripts.requestPojo.Item;
import com.borneo.api.automation.scripts.responsePojo.InspectAPIResponse;
import com.borneo.api.automation.scripts.utils.ModuleFactory;
import io.qameta.allure.Step;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InspectAPI {
    private final API_Client client;
    private final Borneo_Actions actions;

    public InspectAPI(Borneo_Actions actions) {
        this.client = actions.borneoMasterClient;
        this.actions = actions;
    }

    /**
     * @param value               Data to do analysis on info types
     * @param assertPositiveResponse If this is true and the response is not successful the test will fail
     * @return The details of required activity
     */
    @Step("Inspection")
    public List<InspectAPIResponse> perform(String projectName, String apiKey, String value, boolean assertPositiveResponse) throws Exception {
        log.info("Finding Sensitive Data using Google Inspection API");
        client.headers().put("Content-Type", "application/json");

        //setting value
        Item item = new Item();
        item.setValue(value);

        //constructing Inspection API Request
        InspectAPIRequest inspectAPIRequest = new InspectAPIRequest();
        inspectAPIRequest.setItem(item);

        client.post("/projects/" + projectName + "/content:inspect?key=" + apiKey, inspectAPIRequest);

        List<InspectAPIResponse> inspectAPIResponseList;
        if(client.jsonResponse.get("result").getAsJsonObject().size() == 0)
            inspectAPIResponseList = null;
        else
            inspectAPIResponseList = ModuleFactory.parseList(client.jsonResponse.get("result").getAsJsonObject().get("findings").getAsJsonArray(), InspectAPIResponse.class);

        client.validator().assertPositiveResponse(assertPositiveResponse);
        log.info("Sensitive Data successfully fetched");

        return inspectAPIResponseList;
    }
}
