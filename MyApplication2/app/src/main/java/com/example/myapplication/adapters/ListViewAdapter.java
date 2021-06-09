package com.example.myapplication.adapters;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.BR;
import com.example.myapplication.models.Fish;
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.FishViewModel;
import com.example.myapplication.databinding.ListItemsBinding;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder> {

    private List<Fish> fishList;
    private int rowLayout;
    private FishViewModel fishViewModel;

    public ListViewAdapter(int rowLayout,FishViewModel viewModel) {
        this.rowLayout=rowLayout;
        this.fishViewModel=viewModel;
    }

    public ListViewAdapter() {

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        ListItemsBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.list_items,parent,false);
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(fishViewModel,position);
//        Fish fish=fishList.get(position);
//        holder.binding.setVariable(BR.fishModel,fish);
//        holder.binding.setVariable(Integer.parseInt("fishImg"),fish.getFish_img());
    }



    @Override
    public int getItemCount() {
        return fishList==null?0:fishList.size();
    }

    public void setFishData(List<Fish> fishes){
        this.fishList=fishes;
        notifyDataSetChanged();
    }

     class ListViewHolder extends RecyclerView.ViewHolder{
        ViewDataBinding binding;
        public ListViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(FishViewModel viewModel,Integer position){
            binding.setVariable(BR.fishModel,viewModel);
//            binding.setVariable(BR.position,position);
            binding.executePendingBindings();
        }
    }
}
