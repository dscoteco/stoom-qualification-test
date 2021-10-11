package br.com.stoom.qualification.test.model.geocode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GeoCoding {
    private String status;

    @JsonProperty(value="results")
    GeoCodingResult[] geoCodingResults;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GeoCodingResult[] getGeoCodingResults() {
        return geoCodingResults;
    }

    public void setGeoCodingResults(GeoCodingResult[] geoCodingResults) {
        this.geoCodingResults = geoCodingResults;
    }
}
