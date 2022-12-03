package com.example.kutibari;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WomenAdapter extends RecyclerView.Adapter<WomenAdapter.ViewHolder> {
//    /**
//     * my code
//     * @param categories
//     * @param ctx
//     */
//    private String[] categories;
//    private Context context;
//    public WomenAdapter(Context ctx){
////        this.categories = categories;
//        context = ctx;
//    }

//    @Override
//    public int getCount() {
//        return  0;//MainActivity.womenImages.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return null;
//    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        /**
//         * my code
//         */
//        convertView = LayoutInflater.from(context).inflate(R.layout.activity_woman_list, null);
//        ImageView imageView = convertView.findViewById(R.id.imageView);
//        imageView.setImageResource(MainActivity.womenImages[position]);
//        TextView title = convertView.findViewById(R.id.name);
//        title.setText(MainActivity.womenName[position]);
//        return convertView;
//    }
    private Context context;
    ArrayList<User> userlist=new ArrayList<>();
    public WomenAdapter(Context context){
        this.context=context;
    }

    public void setUserlist(ArrayList<User> userlist) {
        this.userlist = userlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WomenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artistlist, parent, false);
        WomenAdapter.ViewHolder holder = new WomenAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull WomenAdapter.ViewHolder holder, int position) {
        holder.artistname.setText(userlist.get(position).getUsername());
        holder.artistemail.setText(userlist.get(position).getMail());
        final Integer index = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.parent.getContext(), UsersProductList.class);
                intent.putExtra("Uid", userlist.get(index).getUid());
                intent.putExtra("name",userlist.get(index).getUsername());
                context.startActivity(intent);
                Toast.makeText(context, "selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
        @Override
        public int getItemCount() {
            return userlist.size();
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
            private TextView artistname,artistemail;
            private ConstraintLayout parent;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                artistname=itemView.findViewById(R.id.artistname);
                artistemail=itemView.findViewById(R.id.artistemail);
                parent=itemView.findViewById(R.id.parent);

            }
        }
}
