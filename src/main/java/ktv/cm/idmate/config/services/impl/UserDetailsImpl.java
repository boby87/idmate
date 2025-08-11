package ktv.cm.idmate.config.services.impl;

import ktv.cm.idmate.dao.repositories.PhoneRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsImpl implements UserDetailsService {
    private final PhoneRepository phoneRepository;

    public UserDetailsImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        var phone = phoneRepository.findByPhoneNumber(phoneNumber);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(phone.getUsers().getRole().name()));
        return new User(phone.getPhoneNumber(), phone.getUsers().getPinHash(), authorities);
    }


}
