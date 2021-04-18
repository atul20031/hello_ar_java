package com.google.ar.core.examples.java.helloar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.collect.BiMap;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.google.ar.core.examples.java.helloar.ExploreActivity.imageUrl;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    Context mContext;

    List<Upload> mUploads;
    private Context context;
    public ImageAdapter(Context context,List<Upload> uploads)
    {
        this.mContext = context;
        mUploads = uploads;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        context = parent.getContext();

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        final Bitmap[] temp = new Bitmap[1];
        holder.textViewName.setText(uploadCurrent.getmName());
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.imageView.setImageBitmap(bitmap);
                temp[0] = bitmap;

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }



            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        try {
            Log.d("Firebase","Rendering Image");
//            Picasso.get().load(uploadCurrent.getImageUrl()).placeholder(R.drawable.ic_launcher).
//                    resize(250,250).centerCrop().into(holder.imageView);
            Picasso.get().load(uploadCurrent.getImageUrl()).placeholder(R.drawable.ic_launcher).
                    resize(600,600).centerCrop().into(target);
            Log.d("bitmap","Rendered Image" + position);
            holder.bind(uploadCurrent,temp[0]);
        }
        catch (Exception e)
        {
            Log.d("Firebase",e.toString());
        }


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName;
        public ImageView imageView;
        private Upload upload;
        private Bitmap bitmap;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
        public  void bind(Upload upload,Bitmap bitmap)
        {
            this.upload = upload;
            this.bitmap= bitmap;
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(context,upload.getmName()+" clicked !",Toast.LENGTH_SHORT).show();
            Log.d("Firebase",upload.getmName()+" clicked !");
            imageUrl = upload.getImageUrl();
            if(bitmap!=null) {
                save(bitmap);
                if (mContext instanceof ExploreActivity) {

                    HelloArActivity.image_bitmap = this.bitmap;
                    Log.d("Firebase", "Launching new instance");
                    ((ExploreActivity) mContext).launch_new_instance();

                }
            }
            else {
                Toast.makeText(mContext,"Image not rendered completely, try again later",Toast.LENGTH_SHORT).show();
            }
        }

        public void save(Bitmap bitmap1){

            Bitmap bitmap = bitmap1;
            Log.d("bitmap","saving bitmap");
            String path = "";
            try {
                if (bitmap != null) {

                    File file = new File(Environment.getExternalStorageDirectory().toString() );
                    if (!file.isDirectory()) {
                        file.mkdir();
                    }

                    file = new File(Environment.getExternalStorageDirectory() , "temp" + ".png");

                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                            Toast saved = Toast.makeText(mContext, "Image saved.", Toast.LENGTH_SHORT);
                            path = file.getAbsolutePath();
                            Log.d("bitmap",path);
                            imageUrl = path;
                            saved.show();
                        } else {
                            Toast unsaved = Toast.makeText(mContext, "Image not save.", Toast.LENGTH_SHORT);
                            Log.d("Bitmap","Image not saved");
                            unsaved.show();
                        }


                    } catch (IOException e) {
                        Log.d("Bitmap","Error saving bitmap " +e.toString());
                        e.printStackTrace();
                    } finally {
                        try {
                            if (fileOutputStream != null) {
                                // fileOutputStream.flush();
                                fileOutputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        catch(Exception e){
                    Log.d("bitmap", e.getMessage());
                }
            }

        }
    }


