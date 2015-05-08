/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples.model;

import java.util.Date;

public class Site {

  private Date date;
  private double visits;
  private double views;
  private double veins;
  
  public Site(Date date, double visits, double views, double veins) {
    this.date = date;
    this.visits = visits;
    this.views = views;
    this.veins = veins;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public double getVisits() {
    return visits;
  }

  public void setVisits(double visits) {
    this.visits = visits;
  }

  public double getViews() {
    return views;
  }

  public void setViews(double views) {
    this.views = views;
  }

  public double getVeins() {
    return veins;
  }

  public void setVeins(double veins) {
    this.veins = veins;
  }
  
  
}
