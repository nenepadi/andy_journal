package io.github.nenepadi.djorna;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.github.nenepadi.djorna.database.DjornaEntry;

public class DjornaEntryAdapter extends RecyclerView.Adapter<DjornaEntryAdapter.DjornaViewHolder> {
    private LayoutInflater layoutInflater;
    private List<DjornaEntry> mEntries;
    private SimpleDateFormat mDateFormat;

    DjornaEntryAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DjornaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.entry_layout, parent, false);
        return new DjornaViewHolder(itemView);
    }

    @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull DjornaViewHolder holder, int position) {
        if(mEntries != null){
            DjornaEntry current = mEntries.get(position);
            Date createdAt = current.getCreatedAt();

            mDateFormat = new SimpleDateFormat("EEEE");
            holder.tvDayOfWeek.setText(mDateFormat.format(createdAt).toUpperCase());

            mDateFormat = new SimpleDateFormat("MMM");
            holder.tvShortMonth.setText(mDateFormat.format(createdAt).toUpperCase());

            Calendar cal = Calendar.getInstance();
            cal.setTime(createdAt);
            holder.tvDateOfMonth.setText(String.format("%d", cal.get(Calendar.DAY_OF_MONTH)));
            holder.tvYear.setText(String.format("%d", cal.get(Calendar.YEAR)));

            holder.tvEntryDetail.setText(current.getDetails());
        }
    }

    public void setEntries(List<DjornaEntry> entries) {
        this.mEntries = entries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mEntries != null){
            return mEntries.size();
        } else{
            return 0;
        }
    }

    class DjornaViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDateOfMonth;
        private final TextView tvDayOfWeek;
        private final TextView tvShortMonth;
        private final TextView tvYear;
        private final TextView tvEntryDetail;

        DjornaViewHolder(View itemView) {
            super(itemView);

            tvDateOfMonth = itemView.findViewById(R.id.tv_date_of_month);
            tvDayOfWeek = itemView.findViewById(R.id.tv_day_of_week);
            tvShortMonth = itemView.findViewById(R.id.tv_short_month);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvEntryDetail = itemView.findViewById(R.id.tv_entry_detail);
        }
    }
}
