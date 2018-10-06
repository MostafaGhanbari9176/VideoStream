package ir.pepotec.app.videostream.view.fragment_channel_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.pepotec.app.videostream.G;
import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.model.struct.StGrouping;
import ir.pepotec.app.videostream.view.ActivityMain;

public class AdapterGroot extends RecyclerView.Adapter<AdapterGroot.Holder> {


    public interface grootItemsClicked {
        void grootItemClicked(int position);
    }

    private Context context;
    private ArrayList<StGrouping> source = new ArrayList<>();
    private grootItemsClicked grootItemsClicked;
    private Holder lastHolder = null;

    public AdapterGroot(Context context, ArrayList<StGrouping> source, grootItemsClicked grootItemsClicked) {
        this.context = context;
        this.source = source;
        this.grootItemsClicked = grootItemsClicked;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_groot, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {
        StGrouping item = source.get(i);
        G.steImage(context, "g" + item.id, holder.img);
        holder.txt.setText(item.name);
        if (i == ActivityMain.currentGrootPosition && lastHolder == null)
            changeBacground(holder);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBacground(holder);
                grootItemsClicked.grootItemClicked(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return source.size();
    }

    private void changeBacground(Holder newHolder) {
        if (lastHolder != null)
            lastHolder.imageParent.setBackgroundResource(R.drawable.circle);
        newHolder.imageParent.setBackgroundResource(R.drawable.selected_circle);
        lastHolder = newHolder;

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        LinearLayout imageParent;
        LinearLayout item;

        public Holder(@NonNull View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.imgItemGRoot);
            txt = v.findViewById(R.id.txtItemGRoot);
            imageParent = v.findViewById(R.id.LLImageParent);
            item = v.findViewById(R.id.itemGRoot);
        }
    }
}
