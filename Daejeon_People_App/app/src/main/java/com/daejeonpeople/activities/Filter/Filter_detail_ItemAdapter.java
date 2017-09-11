package com.daejeonpeople.activities.Filter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.Filter.Filter_model.ChildItem;
import com.daejeonpeople.activities.Filter.Filter_model.Item;
import com.daejeonpeople.activities.Filter.Filter_model.ParentItem;

import java.util.ArrayList;

/**
 * Created by KimDongGyu on 2017-09-01.
 */

public class Filter_detail_ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int PARENT_ITEM_VIEW = 0;
    private final int CHILD_ITEM_VIEW = 1;

    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> visibleItems = new ArrayList<>();

    public Filter_detail_ItemAdapter(String[][] array){

        for(int i = 0; i < array.length; i++){
            Item item1 = new ParentItem((String)array[i][0],PARENT_ITEM_VIEW);
            items.add(item1);
            visibleItems.add(item1);
            for(int j = 1; j < array[i].length; j++){
                Item item2 = new ChildItem((String)array[i][j],CHILD_ITEM_VIEW);
                items.add(item2);
                visibleItems.add(item2);
            }
        }
    }

    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return visibleItems.get(position).viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch(viewType){
            case PARENT_ITEM_VIEW:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_detail_item, parent, false);
                viewHolder = new ParentItemVH(view);
                break;
            case CHILD_ITEM_VIEW:
                View subview = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_detail_sub_item, parent, false);
                viewHolder = new ChildItemVH(subview);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ParentItemVH){
            ParentItemVH parentItemVH = (ParentItemVH)holder;

            parentItemVH.name.setText(visibleItems.get(position).name);
            parentItemVH.arrow.setTag(holder);

            parentItemVH.arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int holderPosition = ((ParentItemVH)v.getTag()).getAdapterPosition();
                    if(((ParentItem)visibleItems.get(holderPosition)).visibilityOfChildItems){
                        collapseChildItems(holderPosition);
                    }else{
                        expandChildItems(holderPosition);
                    }
                }
            });

            parentItemVH.itemView.setTag(holder);
            if(parentItemVH.getItemViewType() == PARENT_ITEM_VIEW) {
                parentItemVH.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int holderPosition = ((ParentItemVH)v.getTag()).getAdapterPosition();
                        if(((ParentItem)visibleItems.get(holderPosition)).visibilityOfChildItems){
                            collapseChildItems(holderPosition);
                        }
                        return true;
                    }
                });
            }

        }else if(holder instanceof ChildItemVH){
            ((ChildItemVH)holder).name.setText(visibleItems.get(position).name);
        }
    }

    private void collapseChildItems(int position){
        ParentItem parentItem = (ParentItem)visibleItems.get(position);
        parentItem.visibilityOfChildItems = false;

        int subItemSize = getVisibleChildItemSize(position);
        for(int i = 0; i < subItemSize; i++){
            parentItem.unvisibleChildItems.add((ChildItem) visibleItems.get(position + 1));
            visibleItems.remove(position + 1);
        }

        notifyItemRangeRemoved(position + 1, subItemSize);
    }

    private int getVisibleChildItemSize(int parentPosition){
        int count = 0;
        parentPosition++;
        while(true){
            if(parentPosition == visibleItems.size() || visibleItems.get(parentPosition).viewType == PARENT_ITEM_VIEW){
                break;
            }else{
                parentPosition++;
                count++;
            }
        }
        return count;
    }

    private void expandChildItems(int position){
        ParentItem parentItem = (ParentItem)visibleItems.get(position);
        parentItem.visibilityOfChildItems = true;
        int childSize = parentItem.unvisibleChildItems.size();

        for(int i = childSize - 1; i >= 0; i--){
            visibleItems.add(position + 1, parentItem.unvisibleChildItems.get(i));
        }
        parentItem.unvisibleChildItems.clear();

        notifyItemRangeInserted(position + 1, childSize);
    }


    private int getParentPosition(int position){
        while(true){
            if(visibleItems.get(position).viewType == PARENT_ITEM_VIEW){
                break;
            }else{
                position--;
            }
        }
        return position;
    }

    public class ParentItemVH extends RecyclerView.ViewHolder {

        TextView name;
        ImageButton arrow;

        public ParentItemVH(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.item_name);
            arrow = (ImageButton)itemView.findViewById(R.id.item_arrow);
        }
    }

    public class ChildItemVH extends RecyclerView.ViewHolder {

        TextView name;

        public ChildItemVH(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.subitem_name);
        }
    }
}
