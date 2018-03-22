
package com.hoaianh.smsfilter.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hoaianh.smsfilter.adapter.ListItemCallback;

import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/13/17.
 */


public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private int mCurrentPosition;
    private ListItemCallback mListItemCallback;

    public BaseViewHolder(View itemView, ListItemCallback listItemCallback) {
        super(itemView);
        this.mListItemCallback = listItemCallback;
        ButterKnife.bind(this, itemView);

    }

    protected abstract void clear();
    public abstract void bind(Object o, int position);

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public ListItemCallback getListItemCallback() {
        return mListItemCallback;
    }
}
