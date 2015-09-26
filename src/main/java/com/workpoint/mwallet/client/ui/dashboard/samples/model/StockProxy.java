/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples.model;

import java.util.Date;

public interface StockProxy {

  public double getChange();

  public Date getDate();

  public String getName();

  public String getSymbol();

  public void setChange(double change);

  public void setDate(Date date);

  public void setName(String name);

  public void setSymbol(String symbol);

}
