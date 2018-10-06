package ir.pepotec.app.videostream.view.fragment_channel_list.fragment_channel_items;

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
import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.model.struct.StChannel;
import ir.pepotec.app.videostream.view.date.DateCreator;

public class AdapterChannels extends RecyclerView.Adapter<AdapterChannels.Holder> {

    public interface OnChannelItemClick{
        void channelItemClicked(int position);
    }
   private Context context;
   private ArrayList<StChannel> source = new ArrayList<>();
   private OnChannelItemClick onChannelItemClick;

    public AdapterChannels(Context context, ArrayList<StChannel> source, OnChannelItemClick onChannelItemClick) {
        this.context = context;
        this.source = source;
        this.onChannelItemClick = onChannelItemClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_channel, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        StChannel item = source.get(i);
        holder.img.setImageResource(R.drawable.channel_do);
        holder.txtName.setText(item.name);
        if(item.hd == 0)
            holder.imgHD.setVisibility(View.GONE);
        else
            holder.imgHD.setVisibility(View.VISIBLE);
        if(item.vpn == 0)
            holder.imgVPN.setVisibility(View.GONE);
        else
            holder.imgVPN.setVisibility(View.VISIBLE);
        if(item.last_seen == null)
            holder.txtSeenDate.setText("هیچ وقت");
        else if((item.last_seen).equalsIgnoreCase(DateCreator.todayDate()))
            holder.txtSeenDate.setText("امروز");
        else
            holder.txtSeenDate.setText(item.last_seen);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChannelItemClick.channelItemClicked(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return source.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView img;
        ImageView imgHD;
        ImageView imgVPN;
        TextView txtName;
        TextView txtSeenDate;
        LinearLayout item;
        public Holder(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.imgChannel);
            txtName = v.findViewById(R.id.txtChannelName);
            txtSeenDate = v.findViewById(R.id.txtChannelSeenDate);
            item = v.findViewById(R.id.itemChannel);
            imgHD = v.findViewById(R.id.imgHD);
            imgVPN = v.findViewById(R.id.imgVPN);
        }
    }
}
