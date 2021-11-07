
package com.borneo.api.automation.scripts.responsePojo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "infoType",
    "likelihood",
    "location",
    "createTime",
    "findingId"
})
@Data
public class InspectAPIResponse {

    @JsonProperty("infoType")
    private InfoType infoType;
    @JsonProperty("likelihood")
    private String likelihood;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("findingId")
    private String findingId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
