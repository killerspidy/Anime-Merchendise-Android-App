package com.example.weebstore.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weebstore.Model.Products;
import com.example.weebstore.ProductDetailsActivity;
import com.example.weebstore.R;
import com.example.weebstore.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class WomenActivity extends AppCompatActivity
{
    private DatabaseReference ProductsRef;
    private RecyclerView categoryView_women;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women);

        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        categoryView_women=(RecyclerView) findViewById(R.id.category_list_women);
        categoryView_women.setLayoutManager(new LinearLayoutManager(WomenActivity.this));
    }

    @Override
    protected void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<Products> options= new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef.orderByChild("category").equalTo("women"), Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDetails.setText(model.getDetails());
                        holder.txtProductPrice.setText("Price = "+model.getPrice()+"tk");
                        Picasso.get().load(model.getImage()).into(holder.imgView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(WomenActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder= new ProductViewHolder(view);
                        return holder;
                    }
                };
        categoryView_women.setAdapter(adapter);
        adapter.startListening();
    }
}
