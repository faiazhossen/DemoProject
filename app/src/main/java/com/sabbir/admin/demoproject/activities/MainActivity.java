package com.sabbir.admin.demoproject.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sabbir.admin.demoproject.R;
import com.sabbir.admin.demoproject.model.UserData;
import com.sabbir.admin.demoproject.utils.DatabaseHandler;
import com.sabbir.admin.demoproject.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA =1212 ;
    private static final int SELECT_FILE =1424 ;
    private String userChoosenTask= "";
    private DatabaseHandler mDatabaseHandler;
    private ArrayList<UserData> mUserData;

    @BindView(R.id.profile_image) de.hdodenhof.circleimageview.CircleImageView profile_img;
    @BindView(R.id.id_name)  EditText edit_name;
    @BindView(R.id.id_email) EditText edit_email;
    @BindView(R.id.id_age) EditText edit_age;
    @BindView(R.id.id_number) EditText edit_number;

    @BindView(R.id.btn_change) Button btn_change;
    @BindView(R.id.id_btn_submit) Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDatabaseHandler = new DatabaseHandler(this);
        mUserData = new ArrayList<>();
    }

    @OnClick(R.id.btn_change)
    public void change(){
        selectImage();
    }

    @OnClick(R.id.id_btn_submit)
    public void submit(){
        BitmapDrawable drawable = (BitmapDrawable) profile_img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        if(checkIfValidData()){
            UserData userData = new UserData(edit_name.getText().toString(),edit_number.getText().toString(),
                    edit_email.getText().toString(),edit_age.getText().toString(),bitmap);
            mDatabaseHandler.addUserData(userData);
            startActivity(new Intent(this,ListData.class));
            finish();
        }
    }

    private boolean checkIfValidData() {
        BitmapDrawable drawable = (BitmapDrawable) profile_img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(edit_name.getText().toString().length()<3){
            edit_name.setError("Name is atleast 3 character long");
            return false;
        }else if(edit_number.getText().toString().length()< 11) {
            edit_number.setError("Number is atleast 11 character long");
            return false;
        }
        else if(edit_age.getText().toString().length()< 2) {
            edit_age.setError("You are not old enough");
            return false;
        }else if(edit_email.getText().toString().length()< 1) {
            edit_age.setError("please Insert Email");
            return false;
        }
        else if(bitmap == null){
            Toast.makeText(this, "Please Insert Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(edit_age.getText().toString().length()< 2) {
            edit_age.setError("You are not old enough");
            return false;
        }

        return true;
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(MainActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        profile_img.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profile_img.setImageBitmap(thumbnail);
    }
}
