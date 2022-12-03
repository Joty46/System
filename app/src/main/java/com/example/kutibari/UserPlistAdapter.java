package com.example.kutibari;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserPlistAdapter extends RecyclerView.Adapter<UserPlistAdapter.ViewHolder> {
    private Context context;
    ArrayList<Product> userplist=new ArrayList<>();
    public UserPlistAdapter(Context context){
        this.context=context;
    }

    public void setUserplist(ArrayList<Product> userplist) {
        this.userplist = userplist;
        notifyDataSetChanged();
    }

    @android.support.annotation.NonNull
    @Override
    public UserPlistAdapter.ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plistforuser, parent, false);
        UserPlistAdapter.ViewHolder holder = new UserPlistAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull UserPlistAdapter.ViewHolder holder, int position) {
        String link=userplist.get(position).getImage();
        Picasso.get().load(link).into(holder.uplistimage);
        holder.uplistcode.setText(userplist.get(position).getId());
        holder.uplistname.setText(userplist.get(position).getTitle());
        holder.uplistprice.setText(userplist.get(position).getPrice());
        final Integer index =holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.parent.getContext(), ProductDetails.class);
                intent.putExtra("Uid",userplist.get(index).getId());
                intent.putExtra("Uuid",userplist.get(index).getUid());
                context.startActivity(intent);
                Toast.makeText(context,"selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return userplist.size();
    }


    public int getCount() {
        return  0;//MainActivity.categoryImages.length;
    }

    public Object getItem() {
        return getItem();
    }

    public Object getItem(int position) {
        return null;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView uplistimage;
        private TextView uplistname,uplistcode,uplistprice;
        private ConstraintLayout parent;

        public ViewHolder(@android.support.annotation.NonNull View itemView) {
            super(itemView);
            uplistimage=itemView.findViewById(R.id.imageforuplist);
            uplistname=itemView.findViewById(R.id.pnameuplist);
            uplistcode=itemView.findViewById(R.id.pcodeuplist);
            uplistprice=itemView.findViewById(R.id.ppriceuplist);
            parent=itemView.findViewById(R.id.parent);

        }
    }
}
