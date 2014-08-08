package com.smarthi.sample.rest;

public class Sample {

  private int number;
  private String city;
  private String state;

  public Sample() {
  }

  public Sample(int number, String city, String state) {
    this.number = number;
    this.city = city;
    this.state = state;
  }

  public int getName() {
    return number;
  }

  public void setName(int name) {
    this.number = name;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "Sample{" +
        "number='" + number + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        '}';
  }
}
