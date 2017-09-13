package com.rakatan.apiconsumer.ui.adapters;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rakatan.apiconsumer.R;
import com.rakatan.apiconsumer.models.User;
import com.rakatan.apiconsumer.ui.adapters.viewholders.UserViewHolder;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {
    ArrayList<User> users;
    private UsersAdapter.Inputs inputs;

    public interface Inputs {
        void userClicked(User user);
    }

    public UsersAdapter(UsersAdapter.Inputs inputs) {
        this.users = new ArrayList<>();
        this.inputs = inputs;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(inflateView(parent, R.layout.item_user), inputs);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
//        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
//        return users.size();
        return 20;
    }

    private View inflateView(final @NonNull ViewGroup viewGroup, final @LayoutRes int viewRes) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return layoutInflater.inflate(viewRes, viewGroup, false);
    }
}
