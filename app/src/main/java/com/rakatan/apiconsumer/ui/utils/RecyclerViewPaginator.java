package com.rakatan.apiconsumer.ui.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import rx.Subscription;
import rx.functions.Action0;

public final class RecyclerViewPaginator {
    private final
    @NonNull
    RecyclerView recyclerView;
    private final
    @NonNull
    Action0 nextPage;
    private Subscription subscription;

    public RecyclerViewPaginator(final @NonNull RecyclerView recyclerView, final @NonNull Action0 nextPage) {
        this.recyclerView = recyclerView;
        this.nextPage = nextPage;
        start();
    }

    /**
     * Begin listening to the recycler view scroll events to determine
     * when pagination should happen.
     */
    public void start() {
        stop();

        subscription = RxRecyclerView.scrollEvents(recyclerView)
                .map(__ -> recyclerView.getLayoutManager())
                .ofType(LinearLayoutManager.class)
                .map(this::displayedItemFromLinearLayout)
                .filter(item -> item.second != 0)
                .filter(this::visibleItemIsCloseToBottom)
                // NB: We think this operation is suffering from back pressure problems due to the volume of scroll events:
                // https://rink.hockeyapp.net/manage/apps/239008/crash_reasons/88318986
                // If it continues to happen we can also try `debounce`.
                .onBackpressureDrop()
                .distinctUntilChanged()
                .subscribe(__ -> nextPage.call());
    }

    /**
     * Stop listening to recycler view scroll events and discard the
     * associated resources. This should be done when the object that
     * created `this` is released.
     */
    public void stop() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    /**
     * Returns a (visibleItem, totalItemCount) pair given a linear layout manager.
     */
    private
    @NonNull
    Pair<Integer, Integer> displayedItemFromLinearLayout(final @NonNull LinearLayoutManager manager) {
        return new Pair<>(manager.findLastVisibleItemPosition(), manager.getItemCount());
    }

    private boolean visibleItemIsCloseToBottom(final @NonNull Pair<Integer, Integer> visibleItemOfTotal) {
        return visibleItemOfTotal.first == visibleItemOfTotal.second - 1;
    }
}
