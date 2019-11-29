package com.example.ilm.pra1moviles.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilm.pra1moviles.R;
import com.example.ilm.pra1moviles.drawer.producto.ItemListFragment.OnListFragmentInteractionListener;
import com.example.ilm.pra1moviles.drawer.producto.Producto;

import java.util.ArrayList;
import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Producto> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Producto> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        this(new ArrayList<Producto>(), listener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_itemlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getNombre());
        //holder.mContentView.setText(mValues.get(position).getDescripcion());
        holder.mMoneyView.setText(mValues.get(position).getPrecio());
        holder.mImgView.setImageBitmap(mValues.get(position).getImagen());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        //public final TextView mContentView;
        public final TextView mMoneyView;
        public final ImageView mImgView;
        public Producto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            //mContentView = (TextView) view.findViewById(R.id.content);
            mImgView = (ImageView) view.findViewById(R.id.item_img);
            mMoneyView = (TextView) view.findViewById(R.id.item_money);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
