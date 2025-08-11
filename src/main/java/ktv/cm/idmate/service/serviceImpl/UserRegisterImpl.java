package ktv.cm.idmate.service.serviceImpl;
import ktv.cm.idmate.dao.repositories.UserRepository;
import ktv.cm.idmate.dto.ResponseUserDto;
import ktv.cm.idmate.mapper.UserMapper;
import ktv.cm.idmate.service.metier.UserMetier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRegisterImpl implements UserMetier {

    private final UserRepository userRepository;

    public UserRegisterImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override

    public ResponseUserDto getByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("phoneNumber is null or empty");
        }
       var users= userRepository.findByPhoneNumber_phoneNumber(phoneNumber).orElseThrow(() -> new RuntimeException("User not found with a phoneNumber " + phoneNumber));


return UserMapper.convertToResponseUserDto(users);
    }
}
