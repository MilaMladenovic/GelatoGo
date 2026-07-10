/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.entity.impl;

import njt.icecreamdeliveryapp.entity.MyEntity;

/**
 *
 * @author milam
 */
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_user_username", columnNames = "username"),
            @UniqueConstraint(name = "uk_user_email", columnNames = "email")
        })
public class User implements MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required.")
    @Size(max = 50, message = "Username cannot exceed 50 characters.")
    @Column(nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please enter a valid email address.")
    @Size(max = 120, message = "Email cannot exceed 120 characters.")
    @Column(nullable = false, length = 120)
    private String email;

    @NotBlank(message = "Password is required.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,100}$",
            message = "Password must contain at least one letter and one digit."
    )
    @Column(nullable = false, length = 255) // čuvamo hash, ne plain lozinku
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "First name cannot exceed 50 characters.")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name cannot exceed 50 characters.")
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Phone number is required.")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters.")
    @Column(nullable = false, length = 20)
    @Pattern(
            regexp = "^\\+?[0-9 ]{8,20}$",
            message = "Please enter a valid phone number."
    )
    private String phoneNumber;

    @NotBlank(message = "Address is required.")
    @Size(max = 150, message = "Address cannot exceed 150 characters.")
    @Column(nullable = false, length = 150)
    private String address;

    @Column(nullable = false)
    private boolean enabled = false;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
