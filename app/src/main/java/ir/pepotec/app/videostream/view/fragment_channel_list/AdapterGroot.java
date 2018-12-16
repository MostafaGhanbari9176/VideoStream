package ir.pepotec.app.videostream.view.fragment_channel_list;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.pepotec.app.videostream.G;
import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.model.local_data_base.LocalDataBase;
import ir.pepotec.app.videostream.model.struct.StGrouping;
import ir.pepotec.app.videostream.view.ActivityMain;
import ir.pepotec.app.videostream.view.activityFiles.ActivityFiles;

public class AdapterGroot extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ActivityMain.OnResultListener {


    public interface grootItemsClicked {
        void grootItemClicked(int position);
    }

    private Context context;
    private ArrayList<StGrouping> source = new ArrayList<>();
    private grootItemsClicked grootItemsClicked;
    private Holder lastHolder = null;
    private int lastPosition = -1;
    private TextView txtSubject;
    private StGrouping stGrouping = new StGrouping();

    private ImageView dialogImg;

    public AdapterGroot(Context context, ArrayList<StGrouping> source, grootItemsClicked grootItemsClicked) {
        this.context = context;
        this.source = source;
        this.grootItemsClicked = grootItemsClicked;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1)
            return new Holder(LayoutInflater.from(context).inflate(R.layout.item_groot, viewGroup, false));
        else
            return new HolderAdd(LayoutInflater.from(context).inflate(R.layout.item_add_groot, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {

        if (holder.getItemViewType() == 1) {
            StGrouping item = source.get(i);
            ((Holder) holder).onBind(item, i);
        } else
            ((HolderAdd) holder).onBind();


    }

    @Override
    public int getItemViewType(int position) {
        if (position == source.size() && ActivityMain.editeMode)
            return 2;
        return 1;
    }

    @Override
    public int getItemCount() {
        if (ActivityMain.editeMode)
            return source.size() + 1;
        return source.size();
    }


    private void changeBacground(Holder newHolder, int position) {
        if (lastHolder != null)
            lastHolder.imageParent.setBackgroundResource(R.drawable.circle);
        newHolder.imageParent.setBackgroundResource(R.drawable.selected_circle);
        lastHolder = newHolder;
        lastPosition = position;

    }

    private void stableBackground(Holder holder, int position) {
        if (lastHolder != null && position == lastPosition)
            holder.imageParent.setBackgroundResource(R.drawable.selected_circle);
        else
            holder.imageParent.setBackgroundResource(R.drawable.circle);
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        LinearLayout imageParent;
        LinearLayout item;

        private Holder(@NonNull View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.imgItemGRoot);
            txt = v.findViewById(R.id.txtItemGRoot);
            imageParent = v.findViewById(R.id.LLImageParent);
            item = v.findViewById(R.id.itemGRoot);
        }

        private void onBind(StGrouping itemData, final int position) {
            final Holder holder = this;
            if (itemData.id == 1 && !ActivityMain.editeMode)
                item.setVisibility(View.GONE);
            if (itemData.img == null)
                G.setImage(context, "g" + itemData.id, img, false);
            else
                G.setImageFromPhone(context, itemData.img, img, false);
            txt.setText(itemData.name);
            if (position == ActivityMain.currentGrootPosition && lastHolder == null)
                changeBacground(holder, position);
            stableBackground(holder, position);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeBacground(holder, position);
                    grootItemsClicked.grootItemClicked(position);
                }
            });
        }
    }

    public class HolderAdd extends RecyclerView.ViewHolder {
        LinearLayout item;

        private HolderAdd(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.itemAddGRoot);
        }

        private void onBind() {
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getGroupDataDialog();
                }
            });
        }
    }

    private void getGroupDataDialog() {
        final Dialog dialog = new Dialog(context);
        LayoutInflater LI = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = LI.inflate(R.layout.dialog_add_grouping, null, false);
        (view.findViewById(R.id.btnCancellGroupDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        (view.findViewById(R.id.btnGroupingConfirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(dialog);
            }
        });
        (view.findViewById(R.id.btnChoseGroupImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMain.onResultListener = AdapterGroot.this;
                ActivityMain.StarterActivity(ActivityFiles.class, true);
            }
        });
        dialogImg = view.findViewById(R.id.imgDialogGroupung);
        txtSubject = view.findViewById(R.id.txtGroupSubject);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private void getData(Dialog dialog) {
        if (TextUtils.isEmpty(txtSubject.getText().toString().trim())) {
            txtSubject.setError("عنوان گروه را وارد کنید.");
            txtSubject.requestFocus();
        } else {
            stGrouping.name = txtSubject.getText().toString().trim();
            stGrouping.sign = "root";
            LocalDataBase.saveGroupings(context, stGrouping);
            Toast.makeText(context, "افزوده شد", Toast.LENGTH_SHORT).show();
            stGrouping.img = null;
            dialog.cancel();

        }
    }

    @Override
    public void dataFromActivity(Intent data, int reqCode) {
        stGrouping.img = data.getStringExtra("path");
        dialogImg.setVisibility(View.VISIBLE);
        G.setImageFromPhone(context, stGrouping.img, dialogImg, false);
    }

}
