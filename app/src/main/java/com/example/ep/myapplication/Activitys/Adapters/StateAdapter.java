package com.example.ep.myapplication.Activitys.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ep.myapplication.Activitys.Model.State;
import com.example.ep.myapplication.Activitys.Services.ImageLoadTask;
import com.example.ep.myapplication.R;
import java.util.ArrayList;

// adapter have responsible on portfolio investing list recycle view and watch list recycle view
public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<State> list;
    private OnItemClickListener onItemClickListener;

    public StateAdapter(Context context, ArrayList list)
    {
        this.context = context;
        this.list = list;
        this.onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(int position,View v,State s)
            {
            }
        };
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.rowlayout, parent, false);
        return new ViewHolder(view,onItemClickListener);
    }

    // setting information on specific recycle view
    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position)
    {
        if (list != null && list.size() > 0)
        {
            State state = list.get(position);
            ImageLoadTask imageLoadTask = new ImageLoadTask(state.getFlag(),holder.flagImageView);
            holder.countryNameId.setText("Country Name: "+state.getName());
            holder.countryNativeNameId.setText("Native Name: "+state.getNativeName());
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public void filterList(ArrayList<State> filteredList)
    {
        list =filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView countryNameId,countryNativeNameId;
        ImageView flagImageView;
        OnItemClickListener onItemClickListener;
        public ViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener)
        {
            super(itemView);
            countryNameId = itemView.findViewById(R.id.countryNameId);
            countryNativeNameId = itemView.findViewById(R.id.countryNativeNameId);
            flagImageView = itemView.findViewById(R.id.flagImageView);
            this.onItemClickListener =onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            State state = list.get(position);
            onItemClickListener.onItemClick(position,v,state);
        }
    }
    public interface OnItemClickListener
    {
        void onItemClick(int position,View v,State state);
    }
}
