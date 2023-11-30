package io.tecky.forms;

import io.tecky.models.Role;
import io.tecky.models.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
//import org.hibernate.validator.constraints.ScriptAssert;


//@ScriptAssert.List({
//        @ScriptAssert(script = "io.tecky.forms.LoginForm.checkRole(_this.role)", lang = "javascript", message = "role must be admin | member | guest"),
//        @ScriptAssert(script = "io.tecky.forms.LoginForm.checkUsername(_this.username)", lang = "javascript", message = "username doesn't allow contain ! @ # $ % & * \\ / ?"),
//})
public class LoginForm extends AuthForm{


    @NotEmpty(message = "The username is required.")
    private String username;

    @NotEmpty(message = "The password is required.")
    @Size(min = 3, max = 20, message = "The length of password must be between 3 and 20 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$", flags = { Pattern.Flag.CASE_INSENSITIVE }, message = "The password must contain [0-9] & [A-Z] & [a-z].")
    private String password;

    private Role role;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
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
