package com.hx.mediaselect.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hx.mediaselect.R;
import com.hx.mediaselect.constract.Setting;
import com.hx.mediaselect.model.Photo;

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter {
    private static final int TYPE_CAMERA = 1;
    private static final int TYPE_ALBUM_ITEMS = 2;

    private ArrayList<Photo> dataList;
    private ArrayList<Photo> selectPhotos;
    private LayoutInflater mInflater;
    private OnClickListener listener;
    private boolean unable, isSingle;
    private int singlePosition;

    public PhotosAdapter(Context cxt, ArrayList<Photo> dataList, OnClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
        this.mInflater = LayoutInflater.from(cxt);
        this.selectPhotos = new ArrayList<>();
        this.isSingle = Setting.mostSelectCount == 1;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CAMERA:
                return new CameraViewHolder(mInflater.inflate(R.layout.item_camera_easy_photos, parent, false));
            default:
                return new PhotoViewHolder(mInflater.inflate(R.layout.item_rv_photos_easy_photos, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final int p = position;
        if (holder instanceof PhotoViewHolder) {
            final Photo item = dataList.get(p);
            if (item == null) return;
            updateSelector(((PhotoViewHolder) holder).tvSelector, item.selected, item, p);
            String path = item.getPath();
            boolean isGif = path.endsWith("gif") || item.getType().endsWith("gif");
            if (isGif) {
                ((PhotoViewHolder) holder).tvType.setVisibility(View.VISIBLE);
                ((PhotoViewHolder) holder).tvType.setText(R.string.gif_easy_photos);
                Glide.with(((PhotoViewHolder) holder).ivPhoto.getContext())
                        .asGif()
                        .load(item.getUri())
                        .into(((PhotoViewHolder) holder).ivPhoto);
            } else {
                ((PhotoViewHolder) holder).tvType.setVisibility(View.GONE);
                Glide.with(((PhotoViewHolder) holder).ivPhoto.getContext())
                        .load(item.getUri())
                        .into(((PhotoViewHolder) holder).ivPhoto);
            }

            ((PhotoViewHolder) holder).vSelector.setVisibility(View.VISIBLE);
            ((PhotoViewHolder) holder).tvSelector.setVisibility(View.VISIBLE);

            ((PhotoViewHolder) holder).ivPhoto.setOnClickListener(v -> {
                int realPosition = p;
                if (Setting.isShowCamera) {
                    realPosition--;
                }
                int selectPosition = 0;
                if (item.selected) {
                    selectPosition = selectPhotos.indexOf(item);
                }
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.95f, 1f, 0.95f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(200);
                v.startAnimation(scaleAnimation);

                listener.onPhotoClick(p, realPosition, selectPosition);
            });


            ((PhotoViewHolder) holder).vSelector.setOnClickListener(view -> {
                if (selectPhotos.size() >= Setting.mostSelectCount && !item.isSelected()) {
                    listener.onSelectorOutOfMax(Setting.mostSelectCount);
                } else {
                    item.selected = !item.selected;
                    if (item.isSelected()) {
                        selectPhotos.add(item);
                    } else {
                        selectPhotos.remove(item);
                    }
                    notifyDataSetChanged();
                }
                listener.onSelectorChanged();
            });
            return;
        }

        if (holder instanceof CameraViewHolder) {
            ((CameraViewHolder) holder).flCamera.setOnClickListener(v -> listener.onCameraClick());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            if (Setting.isShowCamera) {
                return TYPE_CAMERA;
            }
        }
        return TYPE_ALBUM_ITEMS;
    }

    public void notifyData() {
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<Photo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onCameraClick();

        void onPhotoClick(int position, int realPosition, int selectPosition);

        void onSelectorOutOfMax(@Nullable Integer result);

        void onSelectorChanged();
    }

    private class CameraViewHolder extends RecyclerView.ViewHolder {
        final FrameLayout flCamera;

        CameraViewHolder(View itemView) {
            super(itemView);
            this.flCamera = itemView.findViewById(R.id.fl_camera);
        }
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        final AppCompatImageView ivPhoto;
        final TextView tvSelector;
        final View vSelector;
        final TextView tvType;

        PhotoViewHolder(View itemView) {
            super(itemView);
            this.ivPhoto = itemView.findViewById(R.id.iv_photo);
            this.tvSelector = itemView.findViewById(R.id.tv_selector);
            this.vSelector = itemView.findViewById(R.id.v_selector);
            this.tvType = itemView.findViewById(R.id.tv_type);
        }
    }


    private void updateSelector(TextView tvSelector, boolean selected, Photo photo, int position) {
        if (selected) {
            String number = String.valueOf(selectPhotos.indexOf(photo) + 1);
            if (number.equals("0")) {
                tvSelector.setBackgroundResource(R.drawable.bg_select_false_easy_photos);
                tvSelector.setText(null);
                return;
            }
            tvSelector.setText(number);
            tvSelector.setBackgroundResource(R.drawable.bg_select_true_easy_photos);
            if (isSingle) {
                singlePosition = position;
                tvSelector.setText("1");
            }
        } else {
            if (unable) {
                tvSelector.setBackgroundResource(R.drawable.bg_select_false_unable_easy_photos);
            } else {
                tvSelector.setBackgroundResource(R.drawable.bg_select_false_easy_photos);
            }
            tvSelector.setText(null);
        }
    }

    public ArrayList<Photo> getSelectPhotos() {
        return selectPhotos;
    }

}
