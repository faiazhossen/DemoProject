package com.sabbir.admin.demoproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sabbir.admin.demoproject.R;
import com.sabbir.admin.demoproject.adapter.UserAdapter;
import com.sabbir.admin.demoproject.model.UserData;
import com.sabbir.admin.demoproject.utils.DatabaseHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListData extends AppCompatActivity {
    private DatabaseHandler mDatabaseHandler;
    private ArrayList<UserData> mUserData = new ArrayList<>();
    @BindView(R.id.list_user_view) RecyclerView list_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        ButterKnife.bind(this);

        mDatabaseHandler = new DatabaseHandler(this);



    }

    private void initView() {

        mUserData = mDatabaseHandler.getcallDataByAge();
        list_user.setLayoutManager(new LinearLayoutManager(this));
        list_user.hasFixedSize();
        list_user.setAdapter(new UserAdapter(this,mUserData));

        Toast.makeText(this, mUserData.size()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_user) {
            startActivity(new Intent(this,MainActivity.class));
        }
        if(id == R.id.view_image){
            startActivity(new Intent(this,GalleryActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
