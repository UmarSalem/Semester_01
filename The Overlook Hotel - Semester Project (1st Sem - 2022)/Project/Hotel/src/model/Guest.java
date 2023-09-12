package model;

import java.io.Serializable;

public class Guest implements Serializable {
    private String firstname, lastname, nationality, phoneNumber;

    public Guest(String firstname, String lastname, String nationality, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Guest)) return false;

        Guest guest = (Guest) obj;
        return guest.firstname.equals(firstname) && guest.lastname.equals(lastname) && guest.nationality.equals(nationality) && guest.phoneNumber.equals(phoneNumber);
    }
}
