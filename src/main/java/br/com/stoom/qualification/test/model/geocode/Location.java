package br.com.stoom.qualification.test.model.geocode;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    @JsonProperty(value = "lat")
    private Float lat;

    @JsonProperty(value = "lng")
    private Float lng;

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }
}
