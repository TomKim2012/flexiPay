/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.BaseDto;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.FolderDto;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.Photo;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.Post;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.Stock;

public interface ExampleServiceAsync {

  void getPosts(PagingLoadConfig config, AsyncCallback<PagingLoadResult<Post>> callback);

  void getMusicRootFolder(AsyncCallback<FolderDto> callback);
  
  void getMusicFolderChildren(FolderDto folder, AsyncCallback<List<BaseDto>> callback);
  
  void getPhotos(AsyncCallback<List<Photo>> callback);
  
  void getStocks(FilterPagingLoadConfig config, AsyncCallback<PagingLoadResult<Stock>> callback);
}
