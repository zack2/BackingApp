package com.nanodegree.udacy.backingapp.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.udacy.backingapp.R;

import butterknife.BindView;

import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_INGREDIENT;
import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_INGREDIENT_MEASURE;
import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_INGREDIENT_QUANTITY;


/**
 * Created by root on 02/09/2017.
 */

public class IngredientsCursorRecyclerAdapter extends RecyclerViewCursorAdapter<IngredientsCursorRecyclerAdapter.MyViewHolder> {

    public IngredientsCursorRecyclerAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(MyViewHolder holder, Cursor cursor) {

        holder.tvQuantity.setText(cursor.getDouble(COLUMN_INGREDIENT_QUANTITY) + "");
        holder.tvMeasure.setText(cursor.getString(COLUMN_INGREDIENT_MEASURE));
        holder.tvIngredient.setText(cursor.getString(COLUMN_INGREDIENT));

        //content description for TalkBack (Android Acessibilities)
        holder.tvIngredient.setContentDescription(cursor.getString(COLUMN_INGREDIENT));

    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quantity)
        TextView tvQuantity;
        @BindView(R.id.measure)
        TextView tvMeasure;
        @BindView(R.id.ingredient)
        TextView tvIngredient;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvQuantity = itemView.findViewById(R.id.quantity);
            tvMeasure = itemView.findViewById(R.id.measure);
            tvIngredient = itemView.findViewById(R.id.ingredient);
        }
    }
}
