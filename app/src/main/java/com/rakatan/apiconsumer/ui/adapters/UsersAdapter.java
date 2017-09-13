package com.rakatan.apiconsumer.ui.adapters;

import android.content.Context;
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
    private Context context;

    public interface Inputs {
        void userClicked(User user);
    }

    public UsersAdapter(UsersAdapter.Inputs inputs, Context context) {
        this.users = new ArrayList<>();
        this.inputs = inputs;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(inflateView(parent, R.layout.item_user), inputs);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bind(users.get(position), context);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private View inflateView(final @NonNull ViewGroup viewGroup, final @LayoutRes int viewRes) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return layoutInflater.inflate(viewRes, viewGroup, false);
    }

    public void refreshUsers(ArrayList<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public void addUsers(ArrayList<User> newUsers) {
        int initialUsers = users.size();
        users.addAll(newUsers);
        notifyItemRangeInserted(initialUsers, users.size());
    }
}
