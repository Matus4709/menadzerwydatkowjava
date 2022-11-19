package org.meicode.menadzerwydatkow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FirestoreAdapter  extends FirestoreRecyclerAdapter<WydatkiModel, FirestoreAdapter.WydatkiViewHolder> {

    private OnListItemClick onListItemClick;


    public FirestoreAdapter(@NonNull FirestoreRecyclerOptions<WydatkiModel> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull WydatkiViewHolder holder, int position, @NonNull WydatkiModel model) {
        holder.list_name.setText(model.getNazwa());
        holder.list_category.setText(model.getKategoria());
        holder.list_date.setText(model.getData()+"");
        holder.list_price.setText(model.getKwota()+"");
    }

    @NonNull
    @Override
    public WydatkiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent,false);
        return new WydatkiViewHolder(view);
    }

    public class WydatkiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView list_name;
        private TextView list_category;
        private TextView list_date;
        private TextView list_price;

        public WydatkiViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.list_name);
            list_category = itemView.findViewById(R.id.list_category);
            list_date = itemView.findViewById(R.id.list_date);
            list_price = itemView.findViewById(R.id.list_price);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onListItemClick.onItemClick();
        }
    }

    public interface OnListItemClick {
        void onItemClick();
    }

}
