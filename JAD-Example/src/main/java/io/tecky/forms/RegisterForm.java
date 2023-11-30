package io.tecky.forms;

import io.tecky.models.Role;
import io.tecky.models.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;




public class RegisterForm extends AuthForm{

    @NotEmpty(message = "The username is required.")
    private String username;

    @NotEmpty(message = "The password is required.")
    @Size(min = 3, max = 20, message = "The length of password must be between 3 and 20 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$", flags = { Pattern.Flag.CASE_INSENSITIVE }, message = "The password must contain [0-9] & [A-Z] & [a-z].")
    private String password;

    @NotEmpty(message = "The confirm password is required.")
    @Size(min = 3, max = 20, message = "The length of confirm password must be between 3 and 20 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$", flags = { Pattern.Flag.CASE_INSENSITIVE }, message = "The confirm password must contain [0-9] & [A-Z] & [a-z].")
    private String confirmPassword;



    private Role role;

    public String getUsername() {return username;}

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", role=" + role +
                '}';
    }



    public User createUser(){
        var newUser = new User();
        newUser.setUsername(this.username);
        newUser.setRole(this.role);

        return newUser;
    }
}
