package com.rakatan.apiconsumer.ui.activities;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.rakatan.apiconsumer.APIConsumerApp;
import com.rakatan.apiconsumer.R;
import com.rakatan.apiconsumer.models.User;
import com.rakatan.apiconsumer.ui.adapters.UsersAdapter;
import com.rakatan.apiconsumer.ui.utils.RecyclerViewPaginator;
import com.rakatan.apiconsumer.viewmodels.UsersViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action0;

/**
 * We want both the lifecycle goodies and the ability to simply bind the drawer to the toolbar
 * for the purpose of this exercise. As such we implement this class in this odd way to overcome
 * the alpha stage of the LifeCycle .
 */
public class UsersActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    //region View Binds
    @BindView(R.id.recyclerUsers)
    RecyclerView recyclerUsers;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.progressSmall)
    ProgressBar progressSmall;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bigLoading)
    ProgressBar bigLoading;
    @BindView(R.id.layoutSwipeRefresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    //endregion

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    UsersViewModel viewModel;
    UsersAdapter usersAdapter;
    RecyclerViewPaginator paginator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);
        initViewModel();

        progressSmall.getIndeterminateDrawable().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initUserRecycler();

        initOutputs();

        toggleBigLoading(true);
        toggleSmallLoading(false);

        RxSwipeRefreshLayout.refreshes(layoutSwipeRefresh)
                .subscribe(__ -> viewModel.refreshUsers());
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refreshUsers();
    }

    private void initOutputs() {
        viewModel.outputs.bigLoadingToggled()
                .subscribe(this::toggleBigLoading);

        viewModel.outputs.smallLoadingToggled()
                .subscribe(this::toggleSmallLoading);

        viewModel.outputs.initialUsersReceived()
                .subscribe(usersAdapter::refreshUsers);

        viewModel.outputs.moreUsersReceived()
                .subscribe(usersAdapter::addUsers);

        viewModel.outputs.selectedUser()
                .subscribe(this::launchUserDetails);

        viewModel.errors.getUsersErred()
                .subscribe(message -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        viewModel.init(((APIConsumerApp) getApplication()).environment());
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initUserRecycler() {
        usersAdapter = new UsersAdapter(viewModel.inputs, this);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        recyclerUsers.setLayoutManager(llManager);
        recyclerUsers.setAdapter(usersAdapter);
        paginator = new RecyclerViewPaginator(recyclerUsers, getMoreUsers());
    }

    private void toggleBigLoading(boolean isLoading) {
        if (isLoading) {
            recyclerUsers.setVisibility(View.GONE);
            bigLoading.setVisibility(View.VISIBLE);
        } else {
            bigLoading.setVisibility(View.GONE);
            recyclerUsers.setVisibility(View.VISIBLE);
            layoutSwipeRefresh.setRefreshing(false);
        }
    }

    private void toggleSmallLoading(boolean isLoading) {
        progressSmall.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @NonNull
    private Action0 getMoreUsers() {
        return () -> viewModel.inputs.getMoreUsers();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            viewModel.refreshUsers();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    private void launchUserDetails(User user){
        startActivity(new Intent(this, UserDetailsActivity.class));
    }
}
