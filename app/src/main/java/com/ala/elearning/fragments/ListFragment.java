package com.ala.elearning.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ala.elearning.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public abstract class ListFragment extends Fragment {
    public static final String ARG_NUM_OF_COLS = "numberOfCols";
    public static final String ARG_ITEM = "item";
    protected RecyclerView mRecyclerView;
    protected Adapter mAdapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    public ListFragment() {
    }

    protected void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        int numberOfCols = getNumberOfCols();
        if(numberOfCols == 1)
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        else
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),numberOfCols));
    }

    private int getNumberOfCols(){
        return getArguments().getInt(ARG_NUM_OF_COLS);
    }
    public abstract void setupRecyclerViewAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        initRecyclerView();
        initSwipeToRefresh();
        setupRecyclerViewAdapter();
        return view;
    }

    private void initSwipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupRecyclerViewAdapter();
            }
        });
    }


    public abstract class Holder<T> extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected T mItem;
        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onClicked(v);
        }

        public abstract void onClicked(View v);
        public abstract void bind(T item, int pos);
        public void setItem(T item){mItem = item;}
    }


    public abstract class Adapter<T> extends RecyclerView.Adapter<Holder>
    {
        private List<T> mItems;

        public Adapter(List<T> items)
        {
            mItems = items;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view = layoutInflater.inflate(getLayoutId(), parent, false);
            return getNewHolder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position)
        {
            T item = mItems.get(position);
            holder.setItem(item);
            holder.bind(item,position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
        public void setList(List<T> items){
            mItems=items;
        }
        public List<T> getList(){
            return mItems;
        }

        public void clear(){
            mItems.clear();
            notifyDataSetChanged();
        }

        public abstract int getLayoutId();
        public abstract Holder getNewHolder(View v);
    }

}
