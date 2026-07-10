/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.mapper.impl;

import njt.icecreamdeliveryapp.dto.impl.UserDto;
import njt.icecreamdeliveryapp.entity.impl.User;
import njt.icecreamdeliveryapp.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author milam
 */
@Component
public class UserMapper implements DtoEntityMapper<UserDto, User> {

    @Override
    public UserDto toDto(User e) {
        if (e == null) {
            return null;
        }
        return new UserDto(e.getId(), e.getUsername(), e.getEmail(), e.getFirstName(), e.getLastName(), e.getPhoneNumber(), e.getAddress(), e.getRole());
    }

    @Override
    public User toEntity(UserDto t) {
        if (t == null) {
            return null;
        }
        User u = new User();
        u.setId(t.getId());
        u.setUsername(t.getUsername());
        u.setEmail(t.getEmail());
        u.setRole(t.getRole());
        return u;
    }
}
