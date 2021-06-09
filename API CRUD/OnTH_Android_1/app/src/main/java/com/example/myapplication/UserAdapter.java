package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<User> list;

    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(list.get(position).getId()));
        holder.tvName.setText(list.get(position).getName());
        holder.tvAge.setText(String.valueOf(list.get(position).getAge()));
        if (list.get(position).isGender()) {
            holder.tvGender.setText("Female");
        } else {
            holder.tvGender.setText("Male");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvId;
        public TextView tvName;
        public TextView tvAge;
        public TextView tvGender;
        public Button btn_Update;
        public Button btn_remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvId = itemView.findViewById(R.id.tv_Id);
            this.tvName = itemView.findViewById(R.id.tv_Name);
            this.tvAge = itemView.findViewById(R.id.tv_Age);
            this.tvGender = itemView.findViewById(R.id.tv_Gender);
            this.btn_Update = itemView.findViewById(R.id.update_inline);
            this.btn_remove = itemView.findViewById(R.id.remove_inline);

            btn_Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, UpdateUser.class);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", list.get(getLayoutPosition()));
                    intent.putExtra("bundle", bundle);
                    context.startActivity(intent);
                }
            });

            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteApi(List_User.url+"user",list.get(getLayoutPosition()).getId());
                }
            });

        }
    }

    public void DeleteApi(String url,int id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url + '/' +id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, List_User.class);
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}


