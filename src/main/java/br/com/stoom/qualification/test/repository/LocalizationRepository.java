package br.com.stoom.qualification.test.repository;

import br.com.stoom.qualification.test.model.Localization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalizationRepository extends JpaRepository<Localization, Long> {

    Optional<Localization> findByNumber(Integer number);

}
