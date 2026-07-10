/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.dto.impl;

/**
 *
 * @author milam
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {

    @NotBlank(message = "Token is required.")
    private String token;

    @NotBlank(message = "Password is required.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,100}$",
            message = "Password must contain at least one letter and one digit."
    )
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String password;

    public ResetPasswordRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
