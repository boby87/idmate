package ktv.cm.idmate.dao.repositories;

import ktv.cm.idmate.dao.entites.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByLastName(String lastName);
    Optional<Users>findByPhoneNumber_phoneNumber(String phoneNumber);
    Optional<Users>findByCountry_phoneNumber(  String phoneNumber);


}
