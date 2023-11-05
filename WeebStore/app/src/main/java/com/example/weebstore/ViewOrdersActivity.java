package com.example.weebstore;

import static com.example.weebstore.Prevalent.Prevalent.currentUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weebstore.HomeActivity;
import com.example.weebstore.Model.Orders;
import com.example.weebstore.Prevalent.Prevalent;
import com.example.weebstore.ProductDetailsActivity;
import com.example.weebstore.R;
import com.example.weebstore.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewOrdersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        recyclerView = findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference orderListRef = FirebaseDatabase.getInstance().getReference().child("ViewOrders");

        FirebaseRecyclerOptions<Orders> options = new FirebaseRecyclerOptions.Builder<Orders>().setQuery(orderListRef, Orders.class).build();

        FirebaseRecyclerAdapter<Orders, OrderViewHolder> adapter =
                new FirebaseRecyclerAdapter<Orders, OrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull final Orders model) {
                        holder.order_pr_date.setText(model.getDate());
                        holder.order_pr_number.setText(model.getNumber());
                        holder.order_pr_price.setText(model.getTotalAmount() + "Rs");

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[] = new CharSequence[]{
                                        "Edit Orders",
                                        "Cancel Orders"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(ViewOrdersActivity.this);
                                builder.setTitle("Orders Options:");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0) {
                                            Intent intent = new Intent(ViewOrdersActivity.this, ProductDetailsActivity.class);
                                            startActivity(intent);
                                        }
                                        if (i == 1) {
                                            // Remove the user's order
                                            orderListRef.child(currentUser.getPhone())
                                                    .child(model.getNumber())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(ViewOrdersActivity.this, "Order removed successfully.", Toast.LENGTH_SHORT).show();

                                                                // Remove the corresponding admin order
                                                                DatabaseReference adminOrdersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
                                                                adminOrdersRef.child(model.getNumber())
                                                                        .removeValue();

                                                                Intent intent = new Intent(ViewOrdersActivity.this, HomeActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_items_layout, parent, false);
                        OrderViewHolder holder = new OrderViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
