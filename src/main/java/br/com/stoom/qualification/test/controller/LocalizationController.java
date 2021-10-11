package br.com.stoom.qualification.test.controller;

import br.com.stoom.qualification.test.model.geocode.GeoCoding;
import br.com.stoom.qualification.test.model.Localization;
import br.com.stoom.qualification.test.repository.LocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/localizations")
public class LocalizationController {

    private static final String GEOCODING_URI = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String TOKEN = "AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw";

    @Autowired
    private LocalizationRepository localizationRepository;

    @GetMapping
    public List<Localization> findAll(){
        return localizationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        return localizationRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Localization create(@RequestBody Localization localization){

        if (localization.getLatitude() == null || localization.getLongitude() == null)
            getLatLongLocation(localization);

        return localizationRepository.save(localization);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Localization localization) {
        return localizationRepository.findById(id)
                .map(record -> {
                    if (localization.getLatitude() == null || localization.getLongitude() == null)
                        getLatLongLocation(localization);

                    record.setCity(localization.getCity());
                    record.setComplement(localization.getComplement());
                    record.setCountry(localization.getCountry());
                    record.setState(localization.getState());
                    record.setNeighbourhood(localization.getNeighbourhood());
                    record.setNumber(localization.getNumber());
                    record.setStreetName(localization.getStreetName());
                    record.setZipcode(localization.getZipcode());
                    record.setLatitude(localization.getLatitude());
                    record.setLongitude(localization.getLongitude());

                    Localization updated = localizationRepository.save(record);

                    return ResponseEntity.ok().body(updated);

                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id){
        return localizationRepository.findById(id)
                .map(record -> {
                    localizationRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    private void getLatLongLocation(Localization localization) {
        RestTemplate restTemplate = new RestTemplate();

        String address = localization.getNumber() + " " +
                            localization.getStreetName() + ", " +
                            localization.getCity() + ", " +
                            localization.getState() + ", " +
                            localization.getCountry();

        String url = GEOCODING_URI + "?address=" + address + "&key=" + TOKEN;

        GeoCoding geoCoding = restTemplate.getForObject(url, GeoCoding.class);

        if (geoCoding.getStatus().equals("OK")) {
            localization.setLatitude(geoCoding.getGeoCodingResults()[0].getGeometry().getLocation().getLat());
            localization.setLongitude(geoCoding.getGeoCodingResults()[0].getGeometry().getLocation().getLng());
        }
    }
}
