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
import java.util.Date;
import java.util.List;

import io.github.nenepadi.djorna.database.DjornaEntry;

public class DjornaEntryAdapter extends RecyclerView.Adapter<DjornaEntryAdapter.DjornaViewHolder> {
    private LayoutInflater layoutInflater;
    private List<DjornaEntry> mEntries;
    final private ItemClickListener mListener;

    DjornaEntryAdapter(Context context, ItemClickListener itemClickListener){
        layoutInflater = LayoutInflater.from(context);
        mListener = itemClickListener;
    }

    @NonNull
    @Override
    public DjornaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.entry_layout, parent, false);
        return new DjornaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DjornaViewHolder holder, int position) {
        if(mEntries != null){
            DjornaEntry current = mEntries.get(position);
            Date createdAt = current.getCreatedAt();

            holder.tvDayOfWeek.setText(formatter("EEEE").format(createdAt).toUpperCase());
            holder.tvShortMonth.setText(formatter("MMM").format(createdAt).toUpperCase());
            holder.tvDateOfMonth.setText(formatter("dd").format(createdAt));
            holder.tvYear.setText(formatter("yyyy").format(createdAt));
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

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat formatter(String pattern){
        return new SimpleDateFormat(pattern);
    }

    public interface ItemClickListener{
        void onItemClickLister(int itemId);
    }

    class DjornaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = mEntries.get(getAdapterPosition()).getId();
            mListener.onItemClickLister(id);
        }
    }
}
