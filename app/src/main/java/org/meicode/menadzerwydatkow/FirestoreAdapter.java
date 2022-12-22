package org.meicode.menadzerwydatkow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

public class FirestoreAdapter  extends FirestoreRecyclerAdapter<WydatkiModel, FirestoreAdapter.WydatkiViewHolder> {

    private OnItemClickListener listener;


    public FirestoreAdapter(@NonNull FirestoreRecyclerOptions<WydatkiModel> options, OnItemClickListener onItemClickListener) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull WydatkiViewHolder holder, int position, @NonNull WydatkiModel model) {
        holder.list_name.setText(model.getNazwa());
        holder.list_category.setText(model.getKategoria());
        SimpleDateFormat DateFor = new SimpleDateFormat("E dd.MM.yyyy  |  HH:mm");
        String stringDate= DateFor.format(model.getData());


        holder.list_date.setText(stringDate+"");
        holder.list_price.setText(model.getKwota()+"");
    }

    @NonNull
    @Override
    public WydatkiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent,false);
        return new WydatkiViewHolder(view);
    }
    public void deleteWydatek(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
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
            int position = getAdapterPosition();
            if(position !=RecyclerView.NO_POSITION && listener!= null){
                listener.onItemClick(getSnapshots().getSnapshot(position),position);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
}
