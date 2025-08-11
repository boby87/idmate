package ktv.cm.idmate.dao.repositories;

import ktv.cm.idmate.dao.entites.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Phone findByPhoneNumber(String phoneNumber);
}
