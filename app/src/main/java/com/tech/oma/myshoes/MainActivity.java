package com.tech.oma.myshoes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private CoordinatorLayout mCoordianteLayout;
    private LayoutInflater inflater;
    private String mCurrentPhotoPath;
    private File photoFile;
    private ShoeDao shoeDao;
    private RecyclerView shoeListRv;
    private ShoeRecyclerAdapter shoeLvAdapter;

    private static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = this.getApplicationContext();
        this.mCoordianteLayout = findViewById(R.id.coordinate_layout);
        this.shoeDao = ShoeDaoImpl.getShoeDaoInstance(mContext);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        // Create ShoeList
        this.shoeListRv = findViewById(R.id.cardList);
        this.shoeListRv.setHasFixedSize(true);

        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        this.shoeListRv.setLayoutManager(lim);

        // Custom Card Adapter
        this.shoeLvAdapter = new ShoeRecyclerAdapter(this.shoeDao.getShoes());
        this.shoeListRv.setAdapter(shoeLvAdapter);

        // ADD Button mit öffnen des PopUpWindows
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                // Android PopUp Window
                inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                ViewGroup container= null;
                if(inflater != null) {
                    container = (ViewGroup) inflater.inflate(R.layout.popupwindow__layout, null);
                    createPopUpWindow(container);
                }
            }
        });

        Toast.makeText(mContext, "Anz Schuhe " + this.shoeDao.getShoes().size() + "'\nMaxId '" + this.shoeDao.getMaxId() + "'", Toast.LENGTH_LONG).show();
    }

    private void createPopUpWindow(final ViewGroup container) {
        // PopUpWindow initialising
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setWidth((int) (width*.7));
        popupWindow.setHeight((int) (height*.7));

        popupWindow.showAtLocation(mCoordianteLayout, Gravity.CENTER, 0, 0);

        // Close Button
        ImageButton ibClose = container.findViewById(R.id.closeBtn);
        ibClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        // Capture Photo
        final ImageView photoView = container.findViewById(R.id.photoView);
        TextView capture = container.findViewById(R.id.capturePhoto);
        capture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                photoFile = dispatchTakePictureIntent();
                setPic(photoView);
            }
        });

        // OK
        TextView ok = container.findViewById(R.id.okBtn);
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Shoe shoe = createShoeFromView(container);
                shoeDao.saveShoe(shoe);
                savePhoto();
                popupWindow.dismiss();
                shoeLvAdapter.refreshEvents(shoeDao.getShoes());
            }
        });
    }

    private Shoe createShoeFromView(ViewGroup container) {
        TextView tvTitel = container.findViewById(R.id.editTitel);
        String titel = tvTitel.getText().toString();

        TextView tvDescription = container.findViewById(R.id.editDescription);
        String decription = tvDescription.getText().toString();

        String filePath = mCurrentPhotoPath != null ? mCurrentPhotoPath : "";

        TextView tvArt = container.findViewById(R.id.editArt);
        String art = tvArt.getText().toString();

        EditText tvPrice = container.findViewById(R.id.editPrice);
        String priceStr = tvPrice.getText().toString();

        double price = 0.0;
        if(!priceStr.isEmpty()){
            if(priceStr.indexOf('.') < 0){
                priceStr += ".0";
            }

            price = Double.parseDouble(priceStr);
        }
        return shoeDao.createShoe(titel, decription, filePath, art, price);
    }

    private void savePhoto() {
        File photo = this.photoFile;
        if(photo == null)
            return;

        OutputStream fOutputStream = null;
        try {
            fOutputStream = new FileOutputStream(photo);

            fOutputStream.flush();
            fOutputStream.close();

            // Add Phosto to gallery
            galleryAddPic(photo);
            Toast.makeText(this, "Save successful", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void setPic(ImageView mImageView) {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    private void galleryAddPic(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                throw new Error(ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.tech.oms.myshoes",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
            return photoFile;
        }
        return null;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Shoe_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        this.mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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

        switch (item.getItemId()){
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(mContext,  "Settings", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_delete:
                // User chose the "Delete" item, show the app settings UI...
                Toast.makeText(mContext,  "Delete", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
