package vn.edu.huflit.ttl_19dh110248.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.huflit.ttl_19dh110248.R;
import vn.edu.huflit.ttl_19dh110248.models.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Restaurant> mRestaurants;
    private int TYPE_LAYOUT;
    private Listener mlistener;

    public RestaurantAdapter(ArrayList<Restaurant> mRestaurants,Listener listener, int TYPE_LAYOUT) {
        this.mRestaurants = mRestaurants;
        this.TYPE_LAYOUT = TYPE_LAYOUT;
        this.mlistener=listener;
    }
    public interface Listener{
        void onClick(Restaurant restaurantItem);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if(TYPE_LAYOUT == 1){
            View view = inflater.inflate(R.layout.row_restaurant, parent, false);
            return new ViewHolderRestaurant(view);
        }else {
            View view = inflater.inflate(R.layout.row_top_restaurant, parent, false);
            return new ViewHolderTopRestaurant(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Restaurant restaurant = mRestaurants.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        if(TYPE_LAYOUT == 1){
            ViewHolderRestaurant viewHolderRestaurant = (ViewHolderRestaurant) viewHolder;
            StorageReference profileRef = storageReference.child("restaurants/"+ restaurant.getLogo());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(viewHolderRestaurant.ivImage);
                }
            });
            viewHolderRestaurant.tvName.setText(restaurant.getName());
            viewHolderRestaurant.tvAddress.setText(restaurant.getAddress());
            viewHolderRestaurant.tvOpenHour.setText(restaurant.openHours);
            viewHolderRestaurant.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.onClick(restaurant);
                }
            });
        }
        else {
            ViewHolderTopRestaurant viewHolderTopRestaurant = (ViewHolderTopRestaurant) viewHolder;
            StorageReference profileRef = storageReference.child("restaurants/"+ restaurant.getLogo());
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(viewHolderTopRestaurant.ivImage);
                }
            });
            viewHolderTopRestaurant.tvName.setText(restaurant.getName());
            viewHolderTopRestaurant.tvRate.setText("Rate: ".concat(String.valueOf(restaurant.rate)));
            viewHolderTopRestaurant.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.onClick(restaurant);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public class ViewHolderRestaurant extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvOpenHour;
        ImageView ivImage;
        public ViewHolderRestaurant(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOpenHour = itemView.findViewById(R.id.tvOpenHour);
        }
    }

    public class ViewHolderTopRestaurant extends RecyclerView.ViewHolder {
        TextView tvName, tvRate;
        ImageView ivImage;

        public ViewHolderTopRestaurant(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvRate = itemView.findViewById(R.id.tvRate);
        }
    }
}
