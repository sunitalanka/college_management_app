package org.launchcode.models.forms;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User_Data {


    @Id
    @GeneratedValue
    private int id;


    @NotNull
    @Size(min=3, max=15,message = "Login Id cannot be empty")
    private String login_id;


    @NotNull
    @Size(min=3, max=15)
    private String userPwd;


    // @NotNull
    //@Size(min=3, max=15)
    private String firstName;

    //@NotNull
    // @Size(min=3, max=15)
    private String lastName;

    //@NotNull
    private Character is_admin;

    public User_Data() {

    }

    // public int getId() {
    //   return id;
    //}

    //public void setId(int id) {
    //   this.id = id;
    //}

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }


    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
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

    public Character getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Character is_admin) {
        this.is_admin = is_admin;
    }

}


