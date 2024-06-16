package com.adkp.fuexchange.security;

import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class RegisteredStudentDetailService implements UserDetailsService {


    private RegisteredStudentRepository registeredStudentRepository;

    @Override
    public UserDetails loadUserByUsername(String studentId)  {
        RegisteredStudent registeredStudent = registeredStudentRepository.findRegisteredStudentByStudentId(studentId);

        if (registeredStudent == null){
            throw new UsernameNotFoundException("Student not found");
        }

        return new RegisteredStudentDetails(registeredStudent);
    }
}
