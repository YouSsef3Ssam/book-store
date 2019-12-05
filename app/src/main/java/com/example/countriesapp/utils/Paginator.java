package com.example.countriesapp.utils;

import androidx.recyclerview.widget.LinearLayoutManager;

public class Paginator {

    private boolean isLoading;
    private int pageNumber;
    private int itemsCount;
    private int visibleItemsCount;
    private int totalItemsCount;
    private int pastVisibleItems;
    private int previousTotal;
    private int threshold;
    private boolean isExtraData;

    public Paginator(int pageNumber, int itemsCount) {
        this.pageNumber = pageNumber;
        this.itemsCount = itemsCount;

        this.visibleItemsCount = 0;
        this.totalItemsCount = 0;
        this.pastVisibleItems = 0;
        this.previousTotal = 0;
        this.threshold = 0;

        this.isLoading = true;
        isExtraData = true;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public boolean isExtraData() {
        return isExtraData;
    }

    public void setExtraData(boolean extraData) {
        isExtraData = extraData;
    }

    public void performChanged(LinearLayoutManager linearLayoutManager) {
        visibleItemsCount = linearLayoutManager.getChildCount();
        totalItemsCount = linearLayoutManager.getItemCount();
        pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
    }

    public boolean loadMore() {
        if (!isLoading && (totalItemsCount - visibleItemsCount) <= (pastVisibleItems + threshold)) {
            pageNumber++;
            isLoading = true;
            return true;
        } else if (isLoading && (totalItemsCount > previousTotal)) {
            isLoading = false;
            previousTotal = totalItemsCount;
        }
        return false;
    }
}
