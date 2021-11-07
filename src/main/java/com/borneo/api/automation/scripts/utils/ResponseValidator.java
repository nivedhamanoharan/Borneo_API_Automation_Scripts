package com.borneo.api.automation.scripts.utils;

import com.borneo.api.automation.framework.API_Client;
import com.borneo.api.automation.scripts.Borneo_Actions;
import org.testng.Assert;

public class ResponseValidator {
    private final API_Client httpClient;

    public ResponseValidator(Borneo_Actions actions) {
        this.httpClient = actions.borneoMasterClient;
    }

    public ResponseValidator(API_Client httpClient) {
        this.httpClient = httpClient;
    }

    public void assertResponseCode(String responseCode) {
        Assert.assertEquals(httpClient.responseCode, Integer.parseInt(responseCode), "Response code mis-match");
    }

    public void assertErrorCode(String responseCode, String errorCode) {
        Assert.assertEquals(httpClient.responseCode, Integer.parseInt(responseCode), "Response code mis-match");
        Assert.assertEquals(httpClient.getJsonObjectValue("errorcode"), errorCode, "Error code mis-match");
    }

    public void assertError(String responseCode, String errorCode) {
        Assert.assertEquals(httpClient.responseCode, Integer.parseInt(responseCode), "Response code mis-match");
        Assert.assertEquals(httpClient.getJsonObjectValue("error"), errorCode, "Error code mis-match");
    }

    public void assertErrorMessage(String responseCode, String message) {
        Assert.assertEquals(httpClient.responseCode, Integer.parseInt(responseCode), "Response code mis-match");
        Assert.assertEquals(httpClient.getJsonObjectValue("message"), message.toString(), "Message mis-match");
    }

    public void assertErrorResponse(String errorCode, String errorParam, String errorMessage) {
        Assert.assertEquals(httpClient.getJsonObjectValue("error_message"), errorCode, "Error code mis-match");
        Assert.assertEquals(httpClient.getJsonArrayObjectValue(errorParam), errorMessage, "Error code mis-match");
    }

    public void assertErrorResponse(String responseCode, String errorCode, String errorParam, String errorMessage) {
        assertResponseCode(responseCode);
        assertErrorResponse(errorCode, errorParam, errorMessage);
    }

    public void assertContentLength() {
        if(Integer.parseInt(httpClient.getResponseHeaders().get("content-length")) > 0)
            Assert.assertTrue(true);
        else
            Assert.assertTrue(false);
    }
}