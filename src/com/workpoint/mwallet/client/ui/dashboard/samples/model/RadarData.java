/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples.model;

public class RadarData {
  private String name;
  private double data;

  public RadarData(String name, double data) {
    this.name = name;
    this.data = data;
  }

  public double getData() {
    return data;
  }

  public String getName() {
    return name;
  }

  public void setData(double data) {
    this.data = data;
  }

  public void setName(String name) {
    this.name = name;
  }
}
