/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package com.workpoint.mwallet.client.ui.dashboard.samples;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.BaseDto;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.FolderDto;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.Photo;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.Post;
import com.workpoint.mwallet.client.ui.dashboard.samples.model.Stock;

@RemoteServiceRelativePath("service")
public interface ExampleService extends RemoteService {

  PagingLoadResult<Post> getPosts(PagingLoadConfig config);

  /**
   * Returns the music root folder with all child references.
   * 
   * @return the root folder
   */
  FolderDto getMusicRootFolder();

  List<BaseDto> getMusicFolderChildren(FolderDto folder);

  List<Photo> getPhotos();

  PagingLoadResult<Stock> getStocks(FilterPagingLoadConfig config);

}
