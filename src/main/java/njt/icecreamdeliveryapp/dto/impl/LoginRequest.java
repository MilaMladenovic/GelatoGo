/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.dto.impl;

import jakarta.validation.constraints.NotBlank;

/**
 *
 * @author milam
 */
public class LoginRequest {

    @NotBlank(message = "Username is required.")
    private String username;
    
    @NotBlank(message = "Password is required.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
