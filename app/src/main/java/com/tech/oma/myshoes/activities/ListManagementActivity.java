package com.tech.oma.myshoes.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.ViewGroupOverlay;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.oma.myshoes.R;
import com.tech.oma.myshoes.adapter.ShoeListRecyclerAdapter;
import com.tech.oma.myshoes.dataobjects.ShoeList;
import com.tech.oma.myshoes.dataobjects.ShoeListDao;


public class ListManagementActivity extends AppCompatActivity {

    private Context mContext;
    private ShoeListDao shoeListDao;
    private ShoeListRecyclerAdapter shoeListRecyclerAdapter;
    private CoordinatorLayout mCoordinatorLayout;
    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listmanagement_layout);

        this.mCoordinatorLayout = findViewById(R.id.lm_coordinate_layout);
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
                Toast.makeText(mContext, "ADD", Toast.LENGTH_LONG).show();
                this.popupWindowLmAdd();
                return true;
            case R.id.lm_action_delete:
                Toast.makeText(mContext, "Delete", Toast.LENGTH_LONG).show();
                return true;
            case R.id.lm_action_edit:
                Toast.makeText(mContext, "Edit", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void popupWindowLmAdd(){
        // Android PopUp Window
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        if(inflater != null) {
            this.container = (ViewGroup) inflater.inflate(R.layout.popub_lm_add, null);
        }

        if(this.container == null){
            throw new Error("Kein Container");
        }

        // PopUpWindow initialising
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setWidth((int) (width*.95));
        popupWindow.setHeight((int) (height*.5));
        popupWindow.setAnimationStyle(R.style.style_popup_anim);
        popupWindow.showAtLocation(mCoordinatorLayout, Gravity.CENTER, 0, 0);

        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

        final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
        this.applyDim(root, .5);

        // Close PopUp outside
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim(root);
            }
        });

        // Close Button
        TextView chancel = container.findViewById(R.id.lm_add_chancel);
        chancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               popupWindow.dismiss();
               clearDim(root);
           }
        });


        // OK
        TextView ok = container.findViewById(R.id.lm_add_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Liste erzeugen
                TextView editName = container.findViewById(R.id.lm_new_list_name);
                String name = editName.getText().toString();
                ShoeList newList = shoeListDao.createShoeList(name, false);

                shoeListDao.saveShoeList(newList);

                popupWindow.dismiss();
                clearDim(root);
            }
        });
    }

    private void applyDim(@NonNull ViewGroup parent, double dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }
}
