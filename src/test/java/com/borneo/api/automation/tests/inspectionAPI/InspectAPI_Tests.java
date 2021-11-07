package com.borneo.api.automation.tests.inspectionAPI;

import com.borneo.api.automation.scripts.Borneo_Actions;
import com.borneo.api.automation.scripts.responsePojo.InspectAPIResponse;
import com.borneo.api.automation.scripts.utils.Utils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Stories;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@Epic("GoogleInspect")
@Feature("Inspection API")
public class InspectAPI_Tests {
    private Borneo_Actions actions;
    private int tpCount=0, tnCount=0, fpCount=0, fnCount=0;
    private double precision, recall, f_measure;
    private final DecimalFormat df = new DecimalFormat("0.00");

    @Stories({"Pre-Conditions (Inspection_API)"})
    @BeforeClass
    @Step
    public void setup() {
        actions = new Borneo_Actions();
    }

    @DataProvider(name = "MPK_DataProvider")
    public Object[][] dataProvider(Method method) throws Exception {
        return actions.dataProviderUtils.getDataProvider(this.getClass().getName(), method.getName(), Utils.getWorkingDirectory());
    }

    @Stories({"Inspection_API_Tests"})
    @Test(dataProvider = "MPK_DataProvider")
    public void InspectAPI(@Parameter("sNo") String sNo, @Parameter("testCaseName") String testCaseName, @Parameter("Priority") String Priority,
                                     @Parameter("projectName") String projectName, @Parameter("apiKey") String apiKey, @Parameter("value") String value,
                                     @Parameter("responseCode") String responseCode, @Parameter("errorCode") String errorCode,
                                     @Parameter("errorParam") String errorParam, @Parameter("errorMessage") String errorMessage) throws Exception {

        log.info("Executing the Inspection_API");
        List<InspectAPIResponse> inspectAPIList = actions.inspectAPI_actions.inspectAPI.perform(projectName, apiKey, value, false);

        log.info("Response Validations");
        actions.responseValidator.assertResponseCode(responseCode);

        log.info("Validations of likelihood");
        if(inspectAPIList != null) {
            for (int i = 0; i < inspectAPIList.size(); i++) {
                Assert.assertEquals(inspectAPIList.get(i).getLikelihood(), errorMessage, "Likelihood does not match");
            }
        }

        log.info("Calculating tp, tn, fp, fn counts");
        if(errorCode.equalsIgnoreCase("True Positive"))
            tpCount++;
        else if(errorCode.equalsIgnoreCase("True Negative"))
            tnCount++;
        else if(errorCode.equalsIgnoreCase("False Positive"))
            fpCount++;
        else if(errorCode.equalsIgnoreCase("False Negative"))
            fnCount++;
    }

    @AfterClass
    @Step
    public void tearDown() {
        log.info("Precision calculations");
        precision = (tpCount + fpCount);
        precision = (tpCount/precision);
        precision = Double.valueOf(df.format(precision));

        log.info("Precision validations (Expected : Good Precision)");
        if(precision > 0.70) {
            Assert.assertTrue(true, "Data has good precision");
            System.out.println("Sensitive Data has good precision : " + precision);
        } else {
            Assert.assertTrue(false, "Data has poor precision");
            System.out.println("Sensitive Data has poor precision : " + precision);
        }

        log.info("Recall calculations");
        recall = (tpCount + fnCount);
        recall = (tpCount/recall);
        recall = Double.valueOf(df.format(recall));

        log.info("Recall validations (Expected : Poor Recall)");
        if(recall < 0.50) {
            Assert.assertTrue(true, "Data has poor recall");
            System.out.println("Sensitive Data has poor recall : " + recall);
        } else {
            Assert.assertTrue(false, "Data has good recall");
            System.out.println("Sensitive Data has good recall : " + recall);
        }

        log.info("F_Measure calculations");
        f_measure = (precision + recall);
        f_measure = ((2 * precision * recall)/f_measure);
        f_measure = Double.valueOf(df.format(f_measure));

        log.info("F_Measure validations (Expected : poor f_measure)");
        if(f_measure > 0.70) {
            Assert.assertTrue(false, "Data has good f_measure");
            System.out.println("Sensitive Data has good f_measure : " + f_measure);
        } else {
            Assert.assertTrue(true, "Data has poor f_measure");
            System.out.println("Sensitive Data has poor f_measure : " + f_measure);
        }
    }
}
