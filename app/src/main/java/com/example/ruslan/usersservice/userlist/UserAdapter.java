package com.example.ruslan.usersservice.userlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ruslan.usersservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 19.03.2017.
 */

public class UserAdapter extends RecyclerView.Adapter{
    private List<UserItem> users;
    private OnUserItemClickListener onUserItemClickListener;


    public UserAdapter(List<UserItem> users) {
        this.users = users;
    }

    public void setOnUserItemClickListener(OnUserItemClickListener OnUserItemClickListener) {
        this.onUserItemClickListener = OnUserItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((UserViewHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void deleteUsers(){
        users.removeAll(users);
        notifyDataSetChanged();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvSureName;

        public UserViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvSureName = (TextView) itemView.findViewById(R.id.tv_sure_name);
        }

        void bind(final int position){
            tvName.setText(users.get(position).getName());
            tvSureName.setText(users.get(position).getSureName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   onUserItemClickListener.onClickListener(position);
                }
            });
        }
    }
}
