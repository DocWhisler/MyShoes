package com.tech.oma.myshoes.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tech.oma.myshoes.R;
import com.tech.oma.myshoes.dataobjects.ShoeListDao;

public class ListManagementActivity extends AppCompatActivity {

    private Context mContext;
    private ShoeListDao shoeListDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = getApplicationContext();
        this.shoeListDao = new ShoeListDao(mContext);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.lm_toolbar);
        this.setSupportActionBar(toolbar);


    }

}
