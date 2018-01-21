package com.sabbir.admin.demoproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sabbir.admin.demoproject.R;
import com.sabbir.admin.demoproject.model.UserData;

import java.util.ArrayList;

/**
 * Created by Admin on 1/21/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<UserData> mUserData;

    public UserAdapter(Context mContext, ArrayList<UserData> mUserData) {
        this.mContext = mContext;
        this.mUserData = mUserData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.single_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.user_name.setText("Name : "+mUserData.get(position).getUserName());
        holder.user_phn.setText("Phone : "+mUserData.get(position).getUserPhn());
        holder.user_age.setText("Age : "+mUserData.get(position).getUserAge());
        holder.user_email.setText("Email : "+mUserData.get(position).getUserEmail());
        holder.user_img.setImageBitmap(mUserData.get(position).getUserImg());

        holder.holder_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mUserData.get(position).getUserName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView user_img;
        private TextView user_name;
        private TextView user_email;
        private TextView user_age;
        private TextView user_phn;
        private LinearLayout holder_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            user_age = itemView.findViewById(R.id.id_age);
            user_name = itemView.findViewById(R.id.id_name);
            user_email = itemView.findViewById(R.id.id_email);
            user_img = itemView.findViewById(R.id.img_user);
            user_phn = itemView.findViewById(R.id.id_number);
            holder_layout = itemView.findViewById(R.id.holder_layout);

        }
    }
}
