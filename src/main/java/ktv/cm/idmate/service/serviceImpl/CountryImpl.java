package ktv.cm.idmate.service.serviceImpl;

import ktv.cm.idmate.dao.entites.Country;
import ktv.cm.idmate.dao.repositories.CountryRepository;
import ktv.cm.idmate.dao.repositories.PhoneRepository;
import ktv.cm.idmate.dao.repositories.UserRepository;
import ktv.cm.idmate.dto.CountryRequest;
import ktv.cm.idmate.service.metier.CountryMetier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CountryImpl implements CountryMetier {
    private final PhoneRepository phoneRepository;
    private final CountryRepository countryRepository;

    public CountryImpl(PhoneRepository phoneRepository, CountryRepository countryRepository) {
        this.phoneRepository = phoneRepository;
        this.countryRepository = countryRepository;
    }


    @Override
    public void saveCountry(CountryRequest countryRequest) {
      var phone=  phoneRepository. findByPhoneNumber(countryRequest.phoneNumber());
      if (phone.getUsers() != null && phone.getUsers().getCountry() == null){
          var country = countryRepository.save(new Country());
          country.setName(countryRequest.name());
          country.setUsers(phone.getUsers());
      }



    }
}
