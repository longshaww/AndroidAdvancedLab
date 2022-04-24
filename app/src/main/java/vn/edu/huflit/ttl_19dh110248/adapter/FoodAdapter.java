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
import vn.edu.huflit.ttl_19dh110248.models.Food;

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Food> mfoods;
    private OnFoodItemClickListener foodItemClickListener;

    public interface OnFoodItemClickListener {
        void onFoodItemClick(Food food);
    }
    public FoodAdapter(ArrayList<Food> foods,OnFoodItemClickListener foodItemClickListener) {
        this.mfoods = foods;
        this.foodItemClickListener = foodItemClickListener;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_food, parent, false);
        return new ViewHolderFood(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Food food = mfoods.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        ViewHolderFood viewHolderFood= (ViewHolderFood) holder;
        StorageReference profileRef = storageReference.child("foods/"+ food.getImage());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(viewHolderFood.ivImageFood);
            }
        });
        viewHolderFood.tvNameFood.setText(food.getName());
        viewHolderFood.tvRateFood.setText("Rate: ".concat(String.valueOf(food.rate)));
        viewHolderFood.tvPriceFood.setText("Price: ".concat(String.valueOf(food.price)));
        viewHolderFood.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodItemClickListener.onFoodItemClick(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mfoods.size();
    }

    private class ViewHolderFood extends RecyclerView.ViewHolder {
        TextView tvNameFood, tvRateFood, tvPriceFood;
        ImageView ivImageFood;
        public ViewHolderFood(View view) {
            super(view);
            tvNameFood = view.findViewById(R.id.tvNameFood);
            tvRateFood = view.findViewById(R.id.tvRateFood);
            tvPriceFood = view.findViewById(R.id.tvPriceFood);
            ivImageFood = view.findViewById(R.id.ivImageFood);
        }
    }

}
