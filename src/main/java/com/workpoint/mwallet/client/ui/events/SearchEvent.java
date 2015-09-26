package com.workpoint.mwallet.client.ui.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter.SearchType;
import com.workpoint.mwallet.shared.model.SearchFilter;

public class SearchEvent extends GwtEvent<SearchEvent.SearchHandler> {

	public static Type<SearchHandler> TYPE = new Type<SearchHandler>();
	private SearchFilter filter;
	private SearchType searchType;

	public interface SearchHandler extends EventHandler {
		void onSearch(SearchEvent event);
	}

	public SearchEvent(SearchFilter filter, SearchType searchType) {
		this.filter = filter;
		this.searchType = searchType;
	}

	public SearchFilter getFilter() {
		return filter;
	}

	@Override
	protected void dispatch(SearchHandler handler) {
		handler.onSearch(this);
	}

	@Override
	public Type<SearchHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<SearchHandler> getType() {
		return TYPE;
	}
	
	public SearchType getSearchType() {
		return searchType;
	}

	public static void fire(HasHandlers source, SearchFilter filter, SearchType searchType) {
		source.fireEvent(new SearchEvent(filter,searchType));
	}
}
