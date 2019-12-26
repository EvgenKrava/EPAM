package ua.nure.kravchenko.summaryTask4.db.entity;

import ua.nure.kravchenko.summaryTask4.db.Role;

import java.util.*;

public class User extends Entity {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private int roleId;

    public User(){
        tariffs = new ArrayList<>();
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public Role getRole() {
        return Role.getRole(this);
    }

    private List<Tariff> tariffs;

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return login + " " +
                firstName + " " +
                lastName + " " +
                balance + " " +
                getRole();

    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private double balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
