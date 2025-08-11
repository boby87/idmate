package ktv.cm.idmate.dao.repositories;

import ktv.cm.idmate.dao.entites.KycIdentification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KycIdentityRepository extends JpaRepository<KycIdentification, Long> {
    // Additional query methods can be defined here if needed
}
