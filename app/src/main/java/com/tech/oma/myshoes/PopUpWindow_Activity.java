package com.tech.oma.myshoes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by b.kriese on 01.03.2018.
 */

public class PopUpWindow_Activity extends Activity {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow__layout);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        this.getWindow().setLayout((int)(width*0.8), (int)(height*0.5));

        ImageButton ib = (ImageButton)findViewById(R.id.ib_close);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
