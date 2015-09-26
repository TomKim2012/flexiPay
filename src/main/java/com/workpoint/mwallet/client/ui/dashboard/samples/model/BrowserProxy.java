/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples.model;


public interface BrowserProxy {

  public double getIE();

  public String getDate();

  public double getFirefox();

  public double getChrome();
  
  public double getSafari();
  
  public double getOpera();
  
  public double getOther();
  
  public void setIE(double IE);

  public void setDate(String date);

  public void setFirefox(double Firefox);

  public void setChrome(double Chrome);
  
  public void setSafari(double Safari);
  
  public void setOpera(double Opera);
  
  public void setOther(double Other);
}
