package com.example.buildingblock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.buildingblock.Model.Property;
import com.example.buildingblock.ViewHolder.PropertyViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {
    private Button searchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputText = findViewById(R.id.search_property_type);
        searchBtn = findViewById(R.id.search_btn);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = inputText.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

       DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Property");

        FirebaseRecyclerOptions<Property> options = new FirebaseRecyclerOptions.Builder<Property>()
                .setQuery(reference.orderByChild("category").startAt(searchInput), Property.class).build();

        FirebaseRecyclerAdapter<Property, PropertyViewHolder> adapter =
                new FirebaseRecyclerAdapter<Property, PropertyViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PropertyViewHolder holder, int position, @NonNull final Property model) {
                        holder.txtPropertyType.setText(model.getPtype());
                        holder.txtPropertyDescription.setText(model.getDescription());
                        holder.txtPropertyCategory.setText(model.getCategory());

                        holder.txtPropertyPrice.setText("Price = " + model.getPrice() );
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(SearchActivity.this, PropertyDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item_layout, parent, false);
                        PropertyViewHolder holder = new PropertyViewHolder(view);
                        return holder;
                    }
                };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}