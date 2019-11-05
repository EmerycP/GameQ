package com.emerycprimeau.gameq.GUI.toComplete;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.emerycprimeau.gameq.GUI.EditGame;
import com.emerycprimeau.gameq.R;
import com.emerycprimeau.gameq.http.GameRetrofit;
import com.emerycprimeau.gameq.http.mock.ServiceMock;
import com.emerycprimeau.gameq.models.Game;
import com.emerycprimeau.gameq.models.transfer.GameResponseEdit;
import com.emerycprimeau.gameq.models.transfer.GameToCompleteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MonAdapteurToComplete extends RecyclerView.Adapter<MonAdapteurToComplete.MyViewHolder>{
    public Context context;
    public List<Game> mDataset;
    public ServiceMock serviceMock = GameRetrofit.get();
    public Bundle bundle;
    public TextView textViewGameName;
    public Intent i;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvDateAdded;
        public TextView tvName;
        public LinearLayout ll;
        public MyViewHolder(LinearLayout v) {
            super(v);
            this.ll = v;
            tvDateAdded = v.findViewById(R.id.textDateToComplete);
            tvName = v.findViewById(R.id.textGameToComplete);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MonAdapteurToComplete(List<Game> pDataset, Context ctx) {
        mDataset = pDataset;
        this.context = ctx;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MonAdapteurToComplete.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tocomplete, parent, false);



        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Game objetActuel = mDataset.get(position);

        holder.tvDateAdded.setText(objetActuel.date);
        holder.tvName.setText(String.valueOf(objetActuel.Name));
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditGame.class);
                i.putExtra("id" , objetActuel.ID);
                view.getContext().startActivity(i);
            }
        });
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
