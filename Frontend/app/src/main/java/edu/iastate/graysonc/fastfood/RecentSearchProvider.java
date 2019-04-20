package edu.iastate.graysonc.fastfood;

import android.content.SearchRecentSuggestionsProvider;

public class RecentSearchProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "edu.iastate.graysonc.fastfood.RecentSearchProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public RecentSearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
