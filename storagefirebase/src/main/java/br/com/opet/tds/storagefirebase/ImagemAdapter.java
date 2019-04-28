package br.com.opet.tds.storagefirebase;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagemAdapter extends ArrayAdapter<Uri> {

    private int rId;

    public ImagemAdapter(Context context, int resource, List<Uri> objects) {
        super(context, resource, objects);
        rId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = convertView;

        if(mView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(rId,null);
        }

        Uri image_link = getItem(position);

        ImageView img = mView.findViewById(R.id.imageDownload);

        Picasso.get().load(image_link).into(img);

        return mView;
    }

}
