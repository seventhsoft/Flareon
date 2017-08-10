package com.seventhsoft.kuni.game;

/**
 * Created by olibits on 8/08/17.
 */

public interface GamePresenter {

    void getDashboard();

    void setDashboard();

    void onBindRepositoryRowViewAtPosition(int position, RecyclerViewAdapter.RepositoryViewHolder holder);

    void getRepositoriesRowsCount();
}
