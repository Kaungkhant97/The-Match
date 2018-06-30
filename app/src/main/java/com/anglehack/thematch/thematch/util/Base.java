package com.anglehack.thematch.thematch.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ko Oo on 30/6/2018.
 */

public class Base
{
    public static abstract class RecyclerAdapter<VH extends RecyclerViewHolder, OBJ> extends RecyclerView.Adapter<VH>
    {
        private Context context;
        private ArrayList<OBJ> itemList;
        private View layoutView;

        public RecyclerAdapter(Context context) {
            this.context = context;
            this.itemList = new ArrayList<>();
        }

        public RecyclerAdapter(Context context, ArrayList<OBJ> itemList) {
            this.context = context;
            this.itemList = itemList;
        }

        public View createView(@LayoutRes int resource, @Nullable ViewGroup parent)
        {
            layoutView = LayoutInflater.from(context).inflate(resource, parent, false);
            return layoutView;
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public ArrayList<OBJ> getItemList() {
            return itemList;
        }

        public Context getContext() {
            return context;
        }

        public View getLayoutView() {
            return layoutView;
        }

        public void setItemList(ArrayList<OBJ> itemList) {
            this.itemList = itemList;
            notifyDataSetChanged();
        }

        public boolean isEmpty() {
            return itemList.isEmpty();
        }

        public void add(OBJ item)
        {
            itemList.add(item);
            notifyDataSetChanged();
        }

        public void add(OBJ item, int position)
        {
            itemList.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(OBJ item)
        {
            itemList.remove(item);
            notifyDataSetChanged();
        }

        public void remove(int position)
        {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static abstract class RecyclerViewHolder<OBJ> extends RecyclerView.ViewHolder
    {
        private Context context;
        private View view;

        public RecyclerViewHolder(View itemView, Context context) {
            super(itemView);
            this.view = itemView;
            this.context = context;
        }

        public OBJ getItem(ArrayList<OBJ> itemList)
        {
            return itemList.get(getAdapterPosition());
        }

        public Context getContext() {
            return context;
        }

        public View getView() {
            return view;
        }

        public abstract void onBind(ArrayList<OBJ> itemList);
    }
}
