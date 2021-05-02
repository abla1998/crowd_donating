package org.mql.crowddonating.models;


import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Donor extends User {

    @Column
    private String phone;

    @Column
    private String address;

    @OneToMany(mappedBy = "donor", fetch = FetchType.LAZY)
    private List<Donation> donations;

    public Donor() {
        donations = new ArrayList<>();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public Donor setDonations(List<Donation> donations) {
        this.donations = donations;
        return this;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", donations=" + donations +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}