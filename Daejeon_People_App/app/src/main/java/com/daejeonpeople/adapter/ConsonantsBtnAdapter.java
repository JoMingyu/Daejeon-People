package com.daejeonpeople.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daejeonpeople.R;

/**
 * Created by geni on 2017. 10. 4..
 */

public class ConsonantsBtnAdapter extends RecyclerView.Adapter<ConsonantsBtnAdapter.ViewHolder> {
    private String[] mArray;

    public ConsonantsBtnAdapter(String[] array){
        this.mArray = array;
    }

    @Override
    public ConsonantsBtnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_btn_item, parent, false);
        return new ConsonantsBtnAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsonantsBtnAdapter.ViewHolder holder, int position) {
        holder.consonantBtn.setText(mArray[position]);
    }

    @Override
    public int getItemCount() {
        return mArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button consonantBtn;
        public ViewHolder(View view){
            super(view);
            consonantBtn=(Button)view.findViewById(R.id.consonantBtn);
        }
    }
}
