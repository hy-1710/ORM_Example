package in.gripxtech.ormexample.models;

import android.support.v7.widget.RecyclerView;

import in.gripxtech.ormexample.databinding.ItemViewMonthBinding;

public class MonthViewHolder extends RecyclerView.ViewHolder {

    private ItemViewMonthBinding binding;

    public MonthViewHolder(ItemViewMonthBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemViewMonthBinding getBinding() {
        return binding;
    }
}
