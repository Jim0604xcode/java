package io.tecky.forms;

import io.tecky.models.Role;

public class AuthForm {


    public boolean checkUsername(String currentUsername){
        String[] chars = {"!","@","#","$","%","&","*","\\","/","?","? "};
        for(String c : chars){
            if(currentUsername.toString().contains(c)){
                return false;
            }
        }
        return true;
    }
    public boolean checkRole(Role currentRole){
//        System.out.println(currentRole.toString());
        if(currentRole.toString().equals("admin") || currentRole.toString().equals("member") || currentRole.toString().equals("guest")){
            return true;
        }
        return false;
    }
}
