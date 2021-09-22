package com.example.buildingblock.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.buildingblock.Interface.ItemClickListner;
import com.example.buildingblock.R;

import org.w3c.dom.Text;

public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtPropertyType, txtPropertyPrice, txtPropertyCategory, txtPropertyDescription;
    public ImageView imageView;
    public ItemClickListner listner;
    public PropertyViewHolder(View itemView)
    {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.property_image);
        txtPropertyType = (TextView) itemView.findViewById(R.id.property_type);
        txtPropertyCategory = (TextView) itemView.findViewById(R.id.property_category);
        txtPropertyPrice = (TextView) itemView.findViewById(R.id.property_price);
        txtPropertyDescription = (TextView) itemView.findViewById(R.id.property_description);


    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
    listner.onClick(view, getAdapterPosition(), false);
    }
}
