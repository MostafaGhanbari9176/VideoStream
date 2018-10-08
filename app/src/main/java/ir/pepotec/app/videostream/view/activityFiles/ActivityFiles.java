package ir.pepotec.app.videostream.view.activityFiles;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.view.activityFiles.FilesAdapter;

public class ActivityFiles extends AppCompatActivity implements FilesAdapter.OnClickLisenerItem {

    RecyclerView list;
    FilesAdapter adapter;
    ArrayList<file> surce;
    ArrayList<File> folderList = new ArrayList<>();
    Stack<String> saveAddress = new Stack<>();
    Button btnBack;
    boolean flag = false;
    String root = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    Toolbar tlb;
    int position = -1;
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        btnBack = (Button) findViewById(R.id.btn_folder_list_back);
        tlb = (Toolbar) findViewById(R.id.tlbActivityFile);
        empty = findViewById(R.id.txtEmptyPicture);
        setSupportActionBar(tlb);
        getSupportActionBar().setTitle("انتخاب تصویر");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityFiles.this.finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        surce = new ArrayList<>();
        adapter = new FilesAdapter(this, surce, this);
        list = (RecyclerView) findViewById(R.id.folderview);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        requestStoragePermission();

    }

    private void setSurce(String address) {

        surce.clear();
        folderList.clear();
        saveAddress.push(address);
        File dir = new File(address);
        File[] files = dir.listFiles();
        if (files == null) {
            if (flag) {
                Intent intent = getIntent();
                intent.putExtra("path", address);
                setResult(RESULT_OK, intent);
                this.finish();
            } else {
                Toast.makeText(ActivityFiles.this, "لطفا اجازه دسترسی به حافظه را فعال کنید و مجددا تلاش نمایید...", Toast.LENGTH_SHORT).show();
            }
        } else {

            for (File file1 : files) {

                file file = new file();
                file.fileName = file1.getName();

                if (file1.isDirectory()) {
                    file.Image = R.drawable.ic_folder;
                    folderList.add(file1);
                    surce.add(file);
                }

                if (file1.getName().toLowerCase().endsWith(".png") || file1.getName().toLowerCase().endsWith(".jpeg") || file1.getName().toLowerCase().endsWith(".jpg")) {
                    file.Image = R.drawable.ic_file;
                    file.path = file1.getAbsolutePath();
                    folderList.add(file1);
                    surce.add(file);
                }

            }

        }


        adapter.notifyDataSetChanged();
        if (surce.size() == 0)
            empty.setVisibility(View.VISIBLE);
        else
            empty.setVisibility(View.GONE);
    }

    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            setSurce(root);

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // next();
                            // show alert dialog navigating to Settings
                            // showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "خطا در دسترسی به حافظه!لطفا دسترسی به حافظه را چک کنید ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void selectItem(int position) {
        flag = true;
        setSurce(folderList.get(position).getAbsolutePath());
        this.position = position;
    }

    @Override
    public void onBackPressed() {
        if (saveAddress.size() > 1) {
            list.scrollToPosition(position);
            saveAddress.pop();
            setSurce(saveAddress.pop());
        } else {
            finish();
            super.onBackPressed();
        }

    }
}