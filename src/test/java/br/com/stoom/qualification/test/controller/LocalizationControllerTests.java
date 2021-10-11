package br.com.stoom.qualification.test.controller;

import br.com.stoom.qualification.test.model.Localization;
import br.com.stoom.qualification.test.repository.LocalizationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocalizationControllerTests {

    @Autowired
    private LocalizationRepository localizationRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void createTest(){

        Localization localization = new Localization(
                1l,
                "Street",
                456,
                "Complement",
                "Neighbourhood",
                "City",
                "State",
                "Country",
                13000000,
                22.22f,
                20.00f
        );

        localizationRepository.save(localization);

        Assertions.assertThat(localization.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void findByIdTest(){
        Localization localization = localizationRepository.findById(1L).get();

        Assertions.assertThat(localization.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void findAllTest(){
        List<Localization> employees = localizationRepository.findAll();

        Assertions.assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateTest(){
        Localization localization = localizationRepository.findById(1L).get();

        localization.setNumber(100);

        Localization localizationUpdated =  localizationRepository.save(localization);

        Assertions.assertThat(localizationUpdated.getNumber()).isEqualTo(100);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteTest(){

        Localization localization = localizationRepository.findById(1L).get();

        localizationRepository.delete(localization);

        Localization localization1 = null;

        Optional<Localization> optionalLocalization = localizationRepository.findByNumber(100);

        if(optionalLocalization.isPresent()){
            localization1 = optionalLocalization.get();
        }

        Assertions.assertThat(localization1).isNull();
    }
}
