package com.adkp.fuexchange.security;

import com.adkp.fuexchange.pojo.Staff;
import com.adkp.fuexchange.repository.StaffRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class StaffDetailService implements UserDetailsService {
    private StaffRepository staffRepository;



    @Override
    public UserDetails loadUserByUsername(String numberPhone) {
        Staff staff = staffRepository.findStaffByNumberPhone(numberPhone);
        if (staff == null) {
            throw new UsernameNotFoundException("staff not found");
        }
        return new StaffDetails(staff);
    }


}
