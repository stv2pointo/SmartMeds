package com.stvjuliengmail.smartmeds.adapter;

import android.view.View;

/**
 * Created by Steven on 2/12/2018.
 */

public interface RecyclerViewItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
