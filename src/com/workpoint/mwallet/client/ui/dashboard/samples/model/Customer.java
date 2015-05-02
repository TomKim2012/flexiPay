/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples.model;

import java.io.Serializable;

public class Customer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 257625675029730084L;
  private String name;
  private String email;
  private int age;

  public Customer() {

  }

  public Customer(String name, String email, int age) {
    this.age = age;
    this.email = email;
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
