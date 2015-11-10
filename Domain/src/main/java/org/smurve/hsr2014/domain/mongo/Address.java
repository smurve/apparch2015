package org.smurve.hsr2014.domain.mongo;


public class Address {

    private String street;
    private String no;
    private String zip;
    private String city;

    public Address(String street, String no, String zip, String city) {
        this.street = street;
        this.no = no;
        this.zip = zip;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toString() {
        return street + " " + no + " in " + zip + " " + city;
    }

}
