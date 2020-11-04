package com.borges.api_complete.Model;



import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;


    private String id;
    private String firstName;
    private String lastName;
    private String CPF;
    private String email;
    private String birthday;

    private String password;
    private String number;

    private String type ;
    public UserModel(){
        type= "CLIENTE";
    }

    public UserModel( String firstName,String type, String lastName, String CPF, String birthday, String email, String password, String number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.CPF = CPF;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.number = number;
       this.type= "CLIENTE";
    }



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) &&
                Objects.equals(firstName, userModel.firstName) &&
                Objects.equals(lastName, userModel.lastName) &&
                Objects.equals(CPF, userModel.CPF) &&
                Objects.equals(email, userModel.email) &&
                Objects.equals(birthday, userModel.birthday) &&
                Objects.equals(password, userModel.password) &&
                Objects.equals(number, userModel.number) &&
                Objects.equals(type, userModel.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, CPF, email, birthday, password, number, type);
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", CPF='" + CPF + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", password='" + password + '\'' +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
