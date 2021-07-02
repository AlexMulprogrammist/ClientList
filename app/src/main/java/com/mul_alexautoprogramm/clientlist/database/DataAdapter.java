package com.mul_alexautoprogramm.clientlist.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mul_alexautoprogramm.clientlist.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {
    private List<Client> clientListArray;
    //private int [] colorArray = {R.drawable.cirlce_red, R.drawable.cirlce_green, R.drawable.cirlce_blue};
    private int [] colorArray = {R.drawable.ic_important, R.drawable.ic_normal, R.drawable.ic_no_important};
    private AdapterOnItemClick adapterOnItemClick;
    private Context context;
    private SharedPreferences def_preference;


    public DataAdapter(List<Client> clientListArray, AdapterOnItemClick adapterOnItemClick, Context context) {
        this.clientListArray = clientListArray;
        this.adapterOnItemClick = adapterOnItemClick;
        this.context = context;
        def_preference = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_2, parent, false);
        return new ViewHolderData(view, adapterOnItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        holder.setData(clientListArray.get(position));
    }

    @Override
    public int getItemCount() {
        return clientListArray.size();
    }

    //View holderClass
    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvSecName;
        TextView tvTelNumb;
        ImageView imImportance;
        ImageView special;
        private AdapterOnItemClick adapterOnItemClick;

        public ViewHolderData(@NonNull View itemView, AdapterOnItemClick adapterOnItemClick) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imImportance = itemView.findViewById(R.id.imImportance);
            tvSecName = itemView.findViewById(R.id.tvSecName);
            tvTelNumb = itemView.findViewById(R.id.tvTel);
            special = itemView.findViewById(R.id.imSpecial);
            this.adapterOnItemClick = adapterOnItemClick;
            itemView.setOnClickListener(this);
        }

        public void setData(Client client) {

            tvName.setTextColor(Color.parseColor(def_preference.getString(context.getResources().getString(R.string.text_name_color_key), "#FF000000")));
            tvSecName.setTextColor(Color.parseColor(def_preference.getString(context.getResources().getString(R.string.text_sec_name_color_key), "#FF000000")));

            tvName.setText(client.getName());
            tvSecName.setText(client.getSur_name());

            tvTelNumb.setText(client.getTel_number());
            imImportance.setImageResource(colorArray[client.getImportance()]);

            if(client.getSpecial() == 1){
                special.setVisibility(View.VISIBLE);
            }else{

                special.setVisibility(View.GONE);

            }


        }

        @Override
        public void onClick(View v) {

                adapterOnItemClick.onAdapterItemClick(getAdapterPosition());

        }
    }

    public interface AdapterOnItemClick{

        void onAdapterItemClick(int position);

    }

    public void updateAdapter(List<Client> newClientList){

            clientListArray.clear();
            clientListArray.addAll(newClientList);
            notifyDataSetChanged();


    }

}
