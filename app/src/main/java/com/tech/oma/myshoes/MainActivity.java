package com.tech.oma.myshoes;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Shoe> shoes ;
    private Context mContext;
    private CoordinatorLayout mCoordianteLayout;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = this.getApplicationContext();
        this.mCoordianteLayout = findViewById(R.id.mainactivity_layout);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // Android PopUp Window
            inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

            ViewGroup container= null;
            if(inflater != null) {
                container = (ViewGroup) inflater.inflate(R.layout.popupwindow__layout, null);
            }

            // PopUpWindow initialising
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;

            final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setWidth((int) (width*.7));
            popupWindow.setHeight((int) (height*.6));

            popupWindow.showAtLocation(mCoordianteLayout, Gravity.CENTER, 0, 0);
            popupWindow.setOutsideTouchable(true);

            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
