package in.gripxtech.ormexample.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

import in.gripxtech.ormexample.R;
import in.gripxtech.ormexample.databinding.ItemViewMonthBinding;
import in.gripxtech.ormexample.models.MonthViewHolder;
import in.gripxtech.ormexample.orm.Month;

public class MonthAdapter extends RecyclerView.Adapter<MonthViewHolder> implements Filterable {

    private FragmentActivity activity;
    private ArrayList<Month> months;
    private ArrayList<Month> monthsClone;

    public MonthAdapter(FragmentActivity activity, final ArrayList<Month> months) {
        this.activity = activity;
        this.months = months;
        this.monthsClone = new ArrayList<Month>() {{
            addAll(months);
        }};
    }

    @Override
    public MonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemViewMonthBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_view_month,
                parent,
                false
        );
        return new MonthViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MonthViewHolder holder, int position) {
        Month month = months.get(position);
        holder.getBinding().tvTitle.setText(month.getName());
        holder.getBinding().tvSubhead.setText(month.getDescription());
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public ArrayList<Month> getMonths() {
        return months;
    }

    public ArrayList<Month> getMonthsClone() {
        return monthsClone;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                String query = constraint.toString().trim().toLowerCase();
                if (!query.isEmpty()) {
                    ArrayList<Month> arrayList = new ArrayList<>();
                    for (Month month : monthsClone) {
                        if (month.getName().toLowerCase().contains(query)) {
                            arrayList.add(month);
                        } else if (String.valueOf(month.getNumber()).toLowerCase().contains(query)) {
                            arrayList.add(month);
                        } else if (month.getDescription().toLowerCase().contains(query)) {
                            arrayList.add(month);
                        }
                    }
                    filterResults.count = arrayList.size();
                    filterResults.values = arrayList;
                } else {
                    filterResults.count = monthsClone.size();
                    filterResults.values = monthsClone;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //noinspection unchecked
                months = (ArrayList<Month>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
