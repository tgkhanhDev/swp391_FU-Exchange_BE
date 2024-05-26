package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.RegisteredStudentDTO;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {StudentMapper.class, RoleMapper.class})
public interface RegisteredStudentMapper {

    @Mapping(source = "registeredStudentId", target = "registeredStudentId")
    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "roleId", target = "role")
    @Mapping(source = "active", target = "active")
    @Mapping(source = "password", target = "password", ignore = true)
    RegisteredStudentDTO toRegisteredStudentDTO(RegisteredStudent registeredStudent);

    @InheritInverseConfiguration(name = "toRegisteredStudentDTO")
    RegisteredStudent toRegisteredStudent(RegisteredStudentDTO registeredStudentDTO);

    List<RegisteredStudentDTO> totoRegisteredStudentDTOList(List<RegisteredStudent> registeredStudentList);

    List<RegisteredStudent> toRegisteredStudentList(List<RegisteredStudentDTO> registeredStudentDTO);
}
