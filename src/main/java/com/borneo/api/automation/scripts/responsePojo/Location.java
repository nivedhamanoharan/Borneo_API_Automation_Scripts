
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
    "byteRange",
    "codepointRange"
})
@Data
public class Location {

    @JsonProperty("byteRange")
    private ByteRange byteRange;
    @JsonProperty("codepointRange")
    private CodepointRange codepointRange;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
