package com.nenton.photon.ui.screens.album;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nenton.photon.R;
import com.nenton.photon.data.storage.realm.PhotocardRealm;
import com.nenton.photon.utils.PhotoTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serge_000 on 06.06.2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AccountViewHolder> {

    @Inject
    Picasso picasso;

    private List<PhotocardRealm> mAlbums = new ArrayList<>();

    public void addAlbum(PhotocardRealm album){
        mAlbums.add(album);
        notifyDataSetChanged();
    }

    @Override
    public AlbumAdapter.AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_album, parent, false);
        return new AccountViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.AccountViewHolder holder, int position) {
        PhotocardRealm mPhoto = mAlbums.get(position);

        picasso.load(mPhoto.getPhoto())
                .fit()
                .centerCrop()
                .transform(new PhotoTransform())
                .into(holder.mPhoto);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.photo_card_album_IV)
        ImageView mPhoto;

        public AccountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
