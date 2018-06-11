package com.tech.oma.myshoes.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tech.oma.myshoes.R;
import com.tech.oma.myshoes.adapter.ShoeListRecyclerAdapter;
import com.tech.oma.myshoes.dataobjects.ShoeListDao;

public class ListManagementActivity extends AppCompatActivity {

    private Context mContext;
    private ShoeListDao shoeListDao;
    private ShoeListRecyclerAdapter shoeListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listmanagement_layout);

        this.mContext = getApplicationContext();
        this.shoeListDao = new ShoeListDao(mContext);

        // Create ShoeList
        RecyclerView shoeListRecycleView = findViewById(R.id.lm_cardList);
        LinearLayoutManager lim = new LinearLayoutManager(this.mContext);
        shoeListRecycleView.setHasFixedSize(true);
        shoeListRecycleView.setLayoutManager(lim);
        lim.setOrientation(LinearLayoutManager.VERTICAL);

        // Adapter
        this.shoeListRecyclerAdapter = new ShoeListRecyclerAdapter(this.mContext, shoeListDao.getAllLists());
        shoeListRecycleView.setAdapter(this.shoeListRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lm_toobar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.lm_action_add:
                return true;
            case R.id.lm_action_delete:
                return true;
            case R.id.lm_action_edit:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
