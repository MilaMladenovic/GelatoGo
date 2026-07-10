/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.dto.impl;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author milam
 */
public class RegisterRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    private String username;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please enter a valid email address.")
    @Size(max = 120, message = "Email cannot exceed 120 characters.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,100}$",
            message = "Password must contain at least one letter and one digit."
    )
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String password;

    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "First name cannot exceed 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name cannot exceed 50 characters.")
    private String lastName;

    @NotBlank(message = "Phone number is required.")
    @Pattern(
            regexp = "^\\+?[0-9 ]{8,20}$",
            message = "Please enter a valid phone number."
    )
    @Size(max = 20, message = "Phone number cannot exceed 20 characters.")
    private String phoneNumber;

    @NotBlank(message = "Address is required.")
    @Size(max = 150, message = "Address cannot exceed 150 characters.")
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
