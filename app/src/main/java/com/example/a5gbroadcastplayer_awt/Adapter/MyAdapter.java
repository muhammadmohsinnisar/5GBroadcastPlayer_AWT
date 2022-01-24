package com.example.a5gbroadcastplayer_awt.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5gbroadcastplayer_awt.Interface.ILoadMore;
import com.example.a5gbroadcastplayer_awt.Model.Item;
import com.example.a5gbroadcastplayer_awt.R;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder{

    public ProgressBar progressBar;


    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);


    }

class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView name, length;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.txtName);
        length = (TextView) itemView.findViewById(R.id.txtlength);
    }
}

}

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    List<Item> items;
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;

    public MyAdapter(RecyclerView recyclerView, Activity activity, List<Item> items){
        this.activity  = activity;
        this.items = items;

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold)){
                    if(iLoadMore != null)
                        iLoadMore.onLoadMore();
                }
                isLoading = true;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }


    public void setiLoadMore(ILoadMore iLoadMore) {
        this.iLoadMore = iLoadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if(holder instanceof LoadingViewHolder.ItemViewHolder){
        Item item = items.get(position);
        LoadingViewHolder.ItemViewHolder viewHolder = (LoadingViewHolder.ItemViewHolder) holder;
        viewHolder.name.setText(items.get(position).getName());
        viewHolder.length.setText(String.valueOf(items.get(position).getLength()));
    }
    else if(holder instanceof LoadingViewHolder){
        LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
        loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded(){
        isLoading = false;
    }
}
