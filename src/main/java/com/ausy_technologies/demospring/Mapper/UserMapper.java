package com.ausy_technologies.demospring.Mapper;

import com.ausy_technologies.demospring.Model.DAO.Role;
import com.ausy_technologies.demospring.Model.DAO.User;
import com.ausy_technologies.demospring.Model.DTO.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {



    public UserDto convertToUserDto(User user)
    {
        UserDto userDto = new UserDto();
        List<String> roles= user.getRoleList()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRoleList(roles);
        return userDto;
    }
}