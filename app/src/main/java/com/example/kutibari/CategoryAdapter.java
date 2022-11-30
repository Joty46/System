package com.example.kutibari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    /**
     * my code
//     * @param categories
//     * @param ctx
//     */
////    private String[] categories;
//    private Context context;
//    public CategoryAdapter(Context ctx){
////        this.categories = categories;
//        context = ctx;
//    }
//
//
//

    private Context context;
    ArrayList<AllProduct>productlist=new ArrayList<>();
    public CategoryAdapter(Context context){
        this.context=context;
    }

    public void setProductlist(ArrayList<AllProduct> productlist) {
        this.productlist = productlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allproductlist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
        String link=productlist.get(position).getImage();
        Picasso.get().load(link).into(holder.pimage);
        holder.pcode.setText(productlist.get(position).getPid());
        holder.pname.setText(productlist.get(position).getPname());
        holder.puname.setText(productlist.get(position).getUname());
        final Integer index =holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.parent.getContext(), Works.class);
                intent.putExtra("Uid",productlist.get(index).getPid());
                intent.putExtra("from","catagoryadapter");
                intent.putExtra("Uuid",productlist.get(index).getUid());
                context.startActivity(intent);
                Toast.makeText(context,"selected", Toast.LENGTH_SHORT).show();
            }
        });
//        holder.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(holder.parent.getContext(), Works.class);
//                intent.putExtra("Uid",productlist.get(position).getPid());
//                context.startActivity(intent);
//                Toast.makeText(context,"selected", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return productlist.size();
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
//        /**
//         * my code
//         */
//        convertView = LayoutInflater.from(context).inflate(R.layout.activity_category_show, null);
//        ImageView imageView = convertView.findViewById(R.id.categoryImage);
//        imageView.setImageResource(MainActivity.categoryImages[position]);
//        TextView title = convertView.findViewById(R.id.productname);
//        title.setText(MainActivity.productName[position]);
//        return convertView;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView pimage;
        private TextView pname,pcode,puname;
        private ConstraintLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pimage=itemView.findViewById(R.id.imageforallproduct);
            pname=itemView.findViewById(R.id.pname);
            pcode=itemView.findViewById(R.id.pcode);
            puname=itemView.findViewById(R.id.arname);
            parent=itemView.findViewById(R.id.parent);

        }
    }


}