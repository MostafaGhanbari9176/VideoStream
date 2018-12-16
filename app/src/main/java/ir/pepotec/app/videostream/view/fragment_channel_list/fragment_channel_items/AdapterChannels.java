package ir.pepotec.app.videostream.view.fragment_channel_list.fragment_channel_items;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.pepotec.app.videostream.G;
import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.model.local_data_base.LocalDataBase;
import ir.pepotec.app.videostream.model.struct.StChannel;
import ir.pepotec.app.videostream.view.ActivityMain;
import ir.pepotec.app.videostream.view.activityFiles.ActivityFiles;
import ir.pepotec.app.videostream.view.date.DateCreator;

public class AdapterChannels extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ActivityMain.OnResultListener {


    public interface OnChannelItemClick {
        void channelItemClicked(int position);
    }

    private Context context;
    private ArrayList<StChannel> source = new ArrayList<>();
    private OnChannelItemClick onChannelItemClick;
    private int grootId;
    private int srootId;
    private TextView txtChannelName;
    private TextView txturl;
    private RadioButton hd;
    private RadioButton vpn;
    private StChannel stChannel = new StChannel();
    private ImageView dialogImg;

    public AdapterChannels(Context context, ArrayList<StChannel> source, OnChannelItemClick onChannelItemClick) {
        this.context = context;
        this.source = source;
        this.onChannelItemClick = onChannelItemClick;
    }

    public AdapterChannels(Context context, ArrayList<StChannel> source, OnChannelItemClick onChannelItemClick, int grootId, int srootId) {
        this.context = context;
        this.source = source;
        this.grootId = grootId;
        this.srootId = srootId;
        this.onChannelItemClick = onChannelItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == 1)
            return new Holder(LayoutInflater.from(context).inflate(R.layout.item_channel, viewGroup, false));
        else
            return new HolderAdd(LayoutInflater.from(context).inflate(R.layout.item_add_channel, viewGroup, false));

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && ActivityMain.editeMode)
            return 2;
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {


        if (holder.getItemViewType() == 1) {
            StChannel item = source.get(i);
            ((Holder) holder).onBind(i, item);
        } else
            ((HolderAdd) holder).onBind();

    }

    @Override
    public int getItemCount() {
        if (ActivityMain.editeMode)
            return 1;
        return source.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView imgHD;
        ImageView imgVPN;
        TextView txtName;
        TextView txtSeenDate;
        LinearLayout item;

        private Holder(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.imgChannel);
            txtName = v.findViewById(R.id.txtChannelName);
            txtSeenDate = v.findViewById(R.id.txtChannelSeenDate);
            item = v.findViewById(R.id.itemChannel);
            imgHD = v.findViewById(R.id.imgHD);
            imgVPN = v.findViewById(R.id.imgVPN);
        }

        private void onBind(final int position, StChannel itemDate) {
            if (itemDate.img == null)
                G.setImage(context,"12",img, true);
            else
                G.setImageFromPhone(context, itemDate.img, img,true);
            txtName.setText(itemDate.name);
            if (itemDate.hd == 0)
                imgHD.setVisibility(View.GONE);
            else
                imgHD.setVisibility(View.VISIBLE);
            if (itemDate.vpn == 0)
                imgVPN.setVisibility(View.GONE);
            else
                imgVPN.setVisibility(View.VISIBLE);
            if (itemDate.last_seen == null)
                txtSeenDate.setText("هیچ وقت");
            else if ((itemDate.last_seen).equalsIgnoreCase(DateCreator.todayDate()))
                txtSeenDate.setText("امروز");
            else
                txtSeenDate.setText(itemDate.last_seen);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onChannelItemClick.channelItemClicked(position);
                }
            });
        }
    }

    public class HolderAdd extends RecyclerView.ViewHolder {

        RelativeLayout item;

        private HolderAdd(@NonNull View v) {
            super(v);
            item = v.findViewById(R.id.itemAddChannel);
        }

        private void onBind() {
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getChannelDataDialog();
                }
            });
        }
    }

    private void getChannelDataDialog() {

        final Dialog dialog = new Dialog(context);
        LayoutInflater LI = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = LI.inflate(R.layout.dialog_add_channel, null, false);
        txtChannelName = view.findViewById(R.id.txtChannelSubjectDialog);
        txturl = view.findViewById(R.id.txtUrlDialog);
        hd = view.findViewById(R.id.rbHd);
        vpn = view.findViewById(R.id.rbVpn);
        (view.findViewById(R.id.btnConfirmChannel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(dialog);
            }
        });
        (view.findViewById(R.id.btnCancellChannelDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        (view.findViewById(R.id.btnChoseChannelImg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMain.onResultListener = AdapterChannels.this;
                ActivityMain.StarterActivity(ActivityFiles.class, true);
            }
        });
        dialogImg = view.findViewById(R.id.imgDialogChannel);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private void saveData(Dialog dialog) {
        String url = txturl.getText().toString().trim();
        if (TextUtils.isEmpty(txtChannelName.getText().toString().trim())) {
            txtChannelName.setError("نام کانال را وارد کنید.");
            txtChannelName.requestFocus();
        } else if (TextUtils.isEmpty(url)) {
            txturl.setError("آدرس کانال را وارد کنید.");
            txturl.requestFocus();
        } else {

            if (!url.contains("http://"))
                url = "http://" + url;

            stChannel.name = txtChannelName.getText().toString().trim();
            stChannel.url = "[ {\"url\": \"" + url + "\", \"qu\": \"auto\"},{\"url\": \"empty\",\"qu\": \"medium\"},{\"url\": \"empty\",\"qu\": \"high\"}]";
            stChannel.hd = hd.isChecked() ? 1 : 0;
            stChannel.vpn = vpn.isChecked() ? 1 : 0;
            stChannel.gsub_id = srootId;
            stChannel.groot_id = grootId;
            LocalDataBase.saveChannels(context, stChannel);
            Toast.makeText(context, "افزوده شد.", Toast.LENGTH_SHORT).show();
            stChannel.img = null;
            dialog.cancel();
        }
    }

    @Override
    public void dataFromActivity(Intent data, int reqCode) {
        stChannel.img = data.getStringExtra("path");
        dialogImg.setVisibility(View.VISIBLE);
        G.setImageFromPhone(context, stChannel.img, dialogImg,true);

    }
}
