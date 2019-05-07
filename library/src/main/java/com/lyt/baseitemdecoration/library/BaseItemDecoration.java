package com.lyt.baseitemdecoration.library;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;

import java.lang.ref.WeakReference;

public class BaseItemDecoration extends RecyclerView.ItemDecoration {
    private int leftMargin;
    private int rightMargin;
    private int topMargin;
    private int bottomMargin;
    private int itemMargin;
    private int designWidth;
    private int headCount;
    private int footCount;

    public BaseItemDecoration(Context context, int itemMargin) {
        this(context, 750, itemMargin);
    }

    public BaseItemDecoration(Context context, int designWidth, int itemMargin) {
        this(context, designWidth, itemMargin, itemMargin, itemMargin, itemMargin, itemMargin);
    }

    public BaseItemDecoration(Context context, int designWidth, int itemMargin, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        this(context, designWidth, itemMargin, leftMargin, topMargin, rightMargin, bottomMargin, 0, 0);
    }

    public BaseItemDecoration(Context context, int designWidth, int itemMargin, int leftMargin, int topMargin, int rightMargin, int bottomMargin, int headCount, int footCount) {
        WeakReference<Context> weakReference = new WeakReference<Context>(context);
        if (weakReference.get() != null) {
            this.designWidth = 750;
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            this.designWidth = designWidth;
            this.itemMargin = itemMargin * width / designWidth;
            this.leftMargin = leftMargin * width / designWidth;
            this.topMargin = topMargin * width / designWidth;
            this.rightMargin = rightMargin * width / designWidth;
            this.bottomMargin = bottomMargin * width / designWidth;
            this.headCount = headCount;
            this.footCount = footCount;
        }

    }

    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager staggeredGridLayoutManager = (GridLayoutManager) manager;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = staggeredGridLayoutManager.getSpanSizeLookup();
            if (staggeredGridLayoutManager.getOrientation() == 0) {
                this.setGridLayoutManagerHorizontalOutRect(outRect, itemPosition, parent);
            } else {
                this.setGridLayoutManagerVerticalOutRect(outRect, itemPosition, parent);
            }
        } else if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager staggeredGridLayoutManager1 = (LinearLayoutManager) manager;
            if (staggeredGridLayoutManager1.getOrientation() == 0) {
                this.setLinearLayoutManagerHorizontalOutRect(outRect, itemPosition, parent);
            } else {
                this.setLinearLayoutManagerVerticalOutRect(outRect, itemPosition, parent);
            }
        } else if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager2 = (StaggeredGridLayoutManager) manager;
            if (staggeredGridLayoutManager2.getOrientation() == 0) {
                this.setStaggeredGridLayoutManagerHorizontalOutRect(outRect, itemPosition, parent);
            } else {
                this.setStaggeredGridLayoutManagerVerticalOutRect(outRect, itemPosition, parent);
            }
        } else {
            outRect.set(0, 0, 0, 0);
        }

    }

    private void setLinearLayoutManagerHorizontalOutRect(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition == 0) {
            outRect.set(this.leftMargin, this.topMargin, this.itemMargin, this.bottomMargin);
        } else if (itemPosition == parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, this.topMargin, this.itemMargin, this.bottomMargin);
        } else {
            outRect.set(0, this.topMargin, this.itemMargin, this.bottomMargin);
        }

    }

    private void setLinearLayoutManagerVerticalOutRect(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition == 0) {
            outRect.set(this.leftMargin, this.topMargin, this.rightMargin, this.itemMargin);
        } else if (itemPosition == parent.getAdapter().getItemCount() - 1) {
            outRect.set(this.leftMargin, 0, this.rightMargin, this.bottomMargin);
        } else {
            outRect.set(this.leftMargin, 0, this.rightMargin, this.itemMargin);
        }

    }

    private void setGridLayoutManagerHorizontalOutRect(Rect outRect, int itemPosition, RecyclerView parent) {
        int childCount = parent.getAdapter().getItemCount();
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        if (this.isGridHorizontalFirstColumn(itemPosition, childCount, spanCount) && this.isGridHorizontalFirstRow(itemPosition, childCount, spanCount)) {
            outRect.set(this.leftMargin, this.topMargin, this.itemMargin, this.itemMargin);
        } else if (this.isGridHorizontalEndColumn(itemPosition, childCount, spanCount) && this.isGridHorizontalEndRow(itemPosition, childCount, spanCount)) {
            outRect.set(0, 0, this.rightMargin, this.bottomMargin);
        } else if (this.isGridHorizontalFirstRow(itemPosition, childCount, spanCount) && this.isGridHorizontalEndColumn(itemPosition, childCount, spanCount)) {
            outRect.set(0, this.topMargin, this.rightMargin, this.itemMargin);
        } else if (this.isGridHorizontalFirstColumn(itemPosition, childCount, spanCount) && this.isGridHorizontalEndRow(itemPosition, childCount, spanCount)) {
            outRect.set(this.leftMargin, 0, this.itemMargin, this.bottomMargin);
        } else if (this.isGridHorizontalFirstRow(itemPosition, childCount, spanCount)) {
            outRect.set(0, this.topMargin, this.itemMargin, this.itemMargin);
        } else if (this.isGridHorizontalFirstColumn(itemPosition, childCount, spanCount)) {
            outRect.set(this.leftMargin, 0, this.itemMargin, this.itemMargin);
        } else if (this.isGridHorizontalEndColumn(itemPosition, childCount, spanCount)) {
            outRect.set(0, 0, this.rightMargin, this.itemMargin);
        } else if (this.isGridHorizontalEndRow(itemPosition, childCount, spanCount)) {
            outRect.set(0, 0, this.itemMargin, this.bottomMargin);
        } else {
            outRect.set(0, 0, this.itemMargin, this.itemMargin);
        }

    }

    private void setGridLayoutManagerVerticalOutRect(Rect outRect, int itemPosition, RecyclerView parent) {
        int childCount = parent.getAdapter().getItemCount();
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        if (this.headCount > 0 && this.footCount > 0) {
            if (itemPosition >= this.headCount && itemPosition < childCount - this.footCount) {
                childCount = childCount - this.headCount - this.footCount;
                itemPosition -= this.headCount;
                this.setDefaultGridLayoutManagerVerticalOutRect(outRect, itemPosition, childCount, spanCount);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        } else if (this.headCount > 0) {
            if (itemPosition < this.headCount) {
                outRect.set(0, 0, 0, 0);
            } else {
                childCount -= this.headCount;
                itemPosition -= this.headCount;
                this.setDefaultGridLayoutManagerVerticalOutRect(outRect, itemPosition, childCount, spanCount);
            }
        } else if (this.footCount > 0) {
            if (itemPosition >= spanCount - this.footCount) {
                outRect.set(0, 0, 0, 0);
            } else {
                childCount -= this.footCount;
                itemPosition -= this.headCount;
                this.setDefaultGridLayoutManagerVerticalOutRect(outRect, itemPosition, childCount, spanCount);
            }
        } else {
            this.setDefaultGridLayoutManagerVerticalOutRect(outRect, itemPosition, childCount, spanCount);
        }

    }

    private void setDefaultGridLayoutManagerVerticalOutRect(Rect outRect, int itemPosition, int childCount, int spanCount) {
        if (this.isGridVerticalFirstColumn(itemPosition, childCount, spanCount) && this.isGridVerticalFirstRow(itemPosition, childCount, spanCount)) {
            outRect.set(this.leftMargin, this.topMargin, this.itemMargin / 2, this.itemMargin / 2);
        } else if (this.isGridVerticalFirstRow(itemPosition, childCount, spanCount) && this.isGridVerticalEndColumn(itemPosition, childCount, spanCount)) {
            outRect.set(this.itemMargin / 2, this.topMargin, this.rightMargin, this.itemMargin / 2);
        } else if (this.isGridVerticalEndColumn(itemPosition, childCount, spanCount) && this.isGridVerticalEndRow(itemPosition, childCount, spanCount)) {
            outRect.set(this.itemMargin / 2, this.itemMargin / 2, this.rightMargin, this.bottomMargin);
        } else if (this.isGridVerticalFirstColumn(itemPosition, childCount, spanCount) && this.isGridVerticalEndRow(itemPosition, childCount, spanCount)) {
            outRect.set(this.leftMargin, this.itemMargin / 2, this.itemMargin / 2, this.bottomMargin);
        } else if (this.isGridVerticalFirstRow(itemPosition, childCount, spanCount)) {
            outRect.set(this.itemMargin / 2, this.topMargin, this.itemMargin / 2, this.itemMargin / 2);
        } else if (this.isGridVerticalFirstColumn(itemPosition, childCount, spanCount)) {
            outRect.set(this.leftMargin, this.itemMargin / 2, this.itemMargin / 2, this.itemMargin / 2);
        } else if (this.isGridVerticalEndColumn(itemPosition, childCount, spanCount)) {
            outRect.set(this.itemMargin / 2, this.itemMargin / 2, this.rightMargin, this.itemMargin / 2);
        } else if (this.isGridVerticalEndRow(itemPosition, childCount, spanCount)) {
            outRect.set(this.itemMargin / 2, this.itemMargin / 2, this.itemMargin / 2, this.bottomMargin);
        } else {
            outRect.set(this.itemMargin / 2, this.itemMargin / 2, this.itemMargin / 2, this.itemMargin / 2);
        }

    }

    private void setStaggeredGridLayoutManagerVerticalOutRect(Rect outRect, int itemPosition, RecyclerView parent) {
        outRect.set(this.leftMargin, this.topMargin, 0, 0);
        parent.setPadding(0, 0, this.rightMargin, this.bottomMargin);
    }

    private void setStaggeredGridLayoutManagerHorizontalOutRect(Rect outRect, int itemPosition, RecyclerView parent) {
        outRect.set(this.leftMargin, this.topMargin, 0, 0);
        parent.setPadding(0, 0, 0, this.bottomMargin);
    }

    private boolean isGridHorizontalFirstColumn(int itemPosition, int childCount, int spanCount) {
        return itemPosition < spanCount;
    }

    private boolean isGridHorizontalEndColumn(int itemPosition, int childCount, int spanCount) {
        int yu = childCount % spanCount;
        if (yu == 0) {
            if (itemPosition >= childCount - spanCount) {
                return true;
            }
        } else if (itemPosition >= childCount - yu) {
            return true;
        }

        return false;
    }

    private boolean isGridHorizontalFirstRow(int itemPosition, int childCount, int spanCount) {
        return itemPosition % spanCount == 0;
    }

    private boolean isGridHorizontalEndRow(int itemPosition, int childCount, int spanCount) {
        return (itemPosition + 1) % spanCount == 0;
    }

    private boolean isGridVerticalFirstColumn(int itemPosition, int childCount, int spanCount) {
        return itemPosition % spanCount == 0;
    }

    private boolean isGridVerticalEndColumn(int itemPosition, int childCount, int spanCount) {
        return (itemPosition + 1) % spanCount == 0;
    }

    private boolean isGridVerticalFirstRow(int itemPosition, int childCount, int spanCount) {
        return itemPosition < spanCount;
    }

    private boolean isGridVerticalEndRow(int itemPosition, int childCount, int spanCount) {
        int yu = childCount % spanCount;
        if (yu == 0) {
            if (itemPosition >= childCount - spanCount) {
                return true;
            }
        } else if (itemPosition >= childCount - yu) {
            return true;
        }

        return false;
    }
}
