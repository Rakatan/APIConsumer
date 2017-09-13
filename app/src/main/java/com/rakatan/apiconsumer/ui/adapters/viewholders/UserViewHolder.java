package com.rakatan.apiconsumer.ui.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rakatan.apiconsumer.R;
import com.rakatan.apiconsumer.models.User;
import com.rakatan.apiconsumer.ui.adapters.UsersAdapter;

public class UserViewHolder extends RecyclerView.ViewHolder{

    int layout = R.layout.item_user;
    private UsersAdapter.Inputs inputs;

    public UserViewHolder(View itemView, UsersAdapter.Inputs inputs) {
        super(itemView);
        this.inputs = inputs;
    }

    public void bind(User user) {

    }
}
