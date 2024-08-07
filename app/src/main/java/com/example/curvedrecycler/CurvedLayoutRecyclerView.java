package com.example.curvedrecycler;

import android.content.res.Resources;
import android.graphics.Point;
import android.view.View;

import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CurvedLayoutRecyclerView extends RecyclerView.LayoutManager {

    private int verticalScrollOffSet = 0;
    private int viewWidth;
    private final double recyclerViewWidth;
    private final int screenHeight;

    private Point xy = new Point();
    private int  xyz = xy.y;

    public CurvedLayoutRecyclerView(Resources resources, int screenHeight) {
        this.screenHeight = screenHeight;
        this.viewWidth = resources.getDimensionPixelSize(R.dimen.item_width);
        this.recyclerViewWidth = (double) resources.getDimensionPixelSize(R.dimen.recyclerview_height);
    }




    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        fill(recycler);
    }



    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        verticalScrollOffSet += dy;
        fill(recycler);
        return dy;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private void layoutChildView(int i, int viewWidthSpacing, View view) {
        int top = i * viewWidthSpacing - verticalScrollOffSet; // starts from 0
        int bottom = top + viewWidth;
        int left = getTopOffsetForView(top + viewWidth / 4);  // it will keep pushing the element to the curve side
        int right = left + viewWidth;

        measureChild(view, viewWidth, viewWidth);
        layoutDecorated(view, left, top, right, bottom);
    }


    private int getTopOffsetForView(int viewCentreX) {
        double s = (double) screenHeight / 2;
        double h = recyclerViewWidth - (double) viewWidth;
        double radius = (h * h + s * s) / (2 * h);  // simple pythagoras theorem with the help of the diagram

        double cosAlpha = (s - viewCentreX) / radius;
        double alpha = Math.acos(MathUtils.clamp(cosAlpha, -1.0, 1.0));

        double xComponent = radius - (radius * Math.sin(alpha));
        return (int) xComponent;
    }

    public void fill(RecyclerView.Recycler recycler){
        detachAndScrapAttachedViews(recycler);
        for(int i=0;i<=4;i++) {


            View view = recycler.getViewForPosition(i);
            addView(view);

           layoutChildView(i,viewWidth,view);

            List<RecyclerView.ViewHolder> scrapListCopy = new ArrayList<>(recycler.getScrapList());
            for(RecyclerView.ViewHolder holder: scrapListCopy) {
                recycler.recycleView(holder.itemView); // helps to scrap the list items as then the movement will go smooth else, it expands the object.
            }
        }

    }




}
