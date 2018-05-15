package com.tech.oma.myshoes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ActionMode.Callback{

    private Context mContext;
    private CoordinatorLayout mCoordinateLayout;
    private LayoutInflater inflater;
    private ViewGroup container;
    private String mCurrentPhotoPath;
    private ShoeDao shoeDao;
    private ShoeRecyclerAdapter shoeRecyclerAdapter;
    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    private ArrayList<Integer> selectedIds = new ArrayList<>();
    private ImageView mImageView;
    private File tmpFile;

    private static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = this.getApplicationContext();
        this.mCoordinateLayout = findViewById(R.id.coordinate_layout);
        this.shoeDao = ShoeDaoImpl.getShoeDaoInstance(mContext);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        // Create ShoeList
        RecyclerView shoeRecycleView = findViewById(R.id.cardList);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        shoeRecycleView.setHasFixedSize(true);
        shoeRecycleView.setLayoutManager(lim);
        lim.setOrientation(LinearLayoutManager.VERTICAL);

        // Custom Card Adapter
        this.shoeRecyclerAdapter = new ShoeRecyclerAdapter(mContext, this.shoeDao.getShoes());
        shoeRecycleView.setAdapter(shoeRecyclerAdapter);

        shoeRecycleView.addOnItemTouchListener(
            new RecyclerItemClickListener(this, shoeRecycleView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (isMultiSelect){
                        //if multiple selection is enabled then select item on single click else perform normal click on item.
                        multiSelect(position);
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    if (!isMultiSelect){
                        selectedIds = new ArrayList<>();
                        isMultiSelect = true;

                        if (actionMode == null){
                            actionMode = startActionMode(MainActivity.this); //show ActionMode.
                        }
                    }

                    multiSelect(position);
                }
            }));

        // ADD Button mit öffnen des PopUpWindows
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new OnClickListener() {

            @Override
                public void onClick(View view) {

                // Android PopUp Window
                inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                if(inflater != null) {
                    container = (ViewGroup) inflater.inflate(R.layout.popupwindow__layout, null);
                    createPopUpWindow(container);
                }
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

        switch (item.getItemId()){
//            case R.id.action_settings:
//                // User chose the "Settings" item, show the app settings UI...
//                Toast.makeText(mContext,  "Settings", Toast.LENGTH_LONG).show();
//                return true;
            case R.id.action_update:
                shoeRecyclerAdapter.refresh(shoeDao.getShoes());
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    // ACTION MODE
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                Iterator<Integer> iter = selectedIds.iterator();
                while(iter.hasNext()) {
                    int shoeId = iter.next();
                    Shoe shoe = shoeDao.getShoe(shoeId);
                    if(shoe != null && selectedIds.contains(shoe.getId()))
                    {
                        // remove file
                        File file = new File(shoe.getImagePath());
                        if (file.exists()){
                            file.delete();
                        }

                        this.shoeDao.deleteShoe(shoe);
                        iter.remove();
                    }
                }

                this.shoeRecyclerAdapter.refresh(shoeDao.getShoes());
                this.shoeRecyclerAdapter.setSelectedIds(this.selectedIds);

                if(selectedIds.size() <= 0){
                    actionMode.setTitle(""); //remove item count from action mode.
                    actionMode.finish(); //hide action mode.
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        isMultiSelect = false;
        selectedIds = new ArrayList<>();
        this.shoeRecyclerAdapter.setSelectedIds(new ArrayList<Integer>());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            setPic();
        }
    }

    // PRIVATE METHODEN
    private void createPopUpWindow(final ViewGroup container) {
        // PopUpWindow initialising
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setWidth((int) (width*.95));
        popupWindow.setAnimationStyle(R.style.style_popup_anim);
        popupWindow.showAtLocation(mCoordinateLayout, Gravity.TOP, 0, (int) (height*.3));

        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

        final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
        this.applyDim(root, .5);

        // Close PopUp outside
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tmpFile != null && tmpFile.exists()){
                    tmpFile.delete();
                }

                mCurrentPhotoPath = null;
                clearDim(root);
                Toast.makeText(mContext, "Dismiss", Toast.LENGTH_LONG).show();
            }
        });

        // Close Button
        ImageButton ibClose = container.findViewById(R.id.closeBtn);
        ibClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        // Capture Photo
        mImageView = container.findViewById(R.id.editphotoView);
        final TextView capture = container.findViewById(R.id.capturePhoto);
        capture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tmpFile != null && tmpFile.exists())
                    tmpFile.delete();
                dispatchTakePictureIntent();
            }
        });

        // OK
        TextView ok = container.findViewById(R.id.okBtn);
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                savePhoto();
                Shoe shoe = createShoeFromView(container);
                shoeDao.saveShoe(shoe);

                popupWindow.dismiss();
                shoeRecyclerAdapter.refresh(shoeDao.getShoes());
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

    private Shoe createShoeFromView(ViewGroup container) {
        TextView tvTitel = container.findViewById(R.id.editTitel);
        String titel = tvTitel.getText().toString();

        TextView tvDescription = container.findViewById(R.id.editDescription);
        String decription = tvDescription.getText().toString();

        String filePath = mCurrentPhotoPath != null ? mCurrentPhotoPath : "";

        TextView tvTag = container.findViewById(R.id.editTag);
        String tag = tvTag.getText().toString();

        EditText tvPrice = container.findViewById(R.id.editPrice);
        String priceStr = tvPrice.getText().toString();

        double price = 0.0;
        if(!priceStr.isEmpty()){
            if(priceStr.indexOf('.') < 0){
                priceStr += ".0";
            }

            try{
                price = Double.parseDouble(priceStr);
            }
            catch (NumberFormatException e){
                price = 0.0;
            }
        }
        return shoeDao.createShoe(titel, decription, filePath, tag, price);
    }

    private void savePhoto() {
        if (mCurrentPhotoPath == null)
            return;

        String date = new SimpleDateFormat("yyyyMMdd", Locale.GERMANY).format(new Date());
        int id = shoeDao.getMaxId()+1;
        File filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String fileName = "/Shoe_" + id + date + ".jpg";

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

        File file = new File(filePath.getPath()+fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCurrentPhotoPath = filePath+fileName;
        tmpFile.deleteOnExit();
    }

    private void setPic() {
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createTmpFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                throw new Error("File not found! " + ex.getStackTrace());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.tech.oms.myshoes",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createTmpFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "tmp_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        tmpFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = tmpFile.getAbsolutePath();
        return tmpFile;
    }

    private void multiSelect(int position) {
        Shoe shoe = this.shoeRecyclerAdapter.getItem(position);
        if (shoe != null){
            if (actionMode != null) {
                if (selectedIds.contains(shoe.getId()))
                    selectedIds.remove(Integer.valueOf(shoe.getId()));
                else
                    selectedIds.add(shoe.getId());

                if (selectedIds.size() > 0)
                    actionMode.setTitle(String.valueOf(selectedIds.size())); //show selected item count on action mode.
                else{
                    actionMode.setTitle(""); //remove item count from action mode.
                    actionMode.finish(); //hide action mode.
                }
                this.shoeRecyclerAdapter.setSelectedIds(selectedIds);
                this.shoeRecyclerAdapter.refresh(this.shoeDao.getShoes());
            }
        }
    }
}
