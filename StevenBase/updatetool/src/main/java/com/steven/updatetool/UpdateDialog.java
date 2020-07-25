package com.steven.updatetool;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

public class UpdateDialog extends Dialog {
    public UpdateDialog(Context context) {
        super(context);
    }

    public UpdateDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private boolean isForce;
        private String versionStr;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private int imgSrc;
        private int bottomBg;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setVersion(String versionStr) {
            this.versionStr = versionStr;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setForce(boolean force) {
            this.isForce = force;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setImgSrc(@DrawableRes int imgSrc) {
            this.imgSrc = imgSrc;
            return this;
        }

        public Builder setBottomBg(@DrawableRes int bottomBg) {
            this.bottomBg = bottomBg;
            return this;
        }

        public UpdateDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            UpdateDialog dialog = new UpdateDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.cutom_upgrade_layout2, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.tv_upgrade_confirm))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.tv_upgrade_confirm)
                            .setOnClickListener(v -> positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE));
                }
            }

            if (negativeButtonClickListener != null) {
                dialog.setOnDismissListener(dialog1 -> {
                    negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                });
            }

            if (versionStr != null) {
                ((TextView) layout.findViewById(R.id.tv_version)).setText("发现新版本: " + versionStr);
            }
            if (message != null) {
                ((TextView) layout.findViewById(R.id.tv_upgrade_feature)).setText(message);
            }

            if (imgSrc != 0) {
                ((ImageView) layout.findViewById(R.id.iv_top)).setImageResource(imgSrc);
            }
            if (bottomBg != 0) {
                ((LinearLayout) layout.findViewById(R.id.ll_bottom)).setBackgroundResource(bottomBg);
            }

            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);
            if (isForce) {
                dialog.setCancelable(false);
            }

            return dialog;
        }
    }

    public void setPositionBtnEnable(boolean isEnable) {
        TextView confirmBtn = findViewById(R.id.tv_upgrade_confirm);
        confirmBtn.setEnabled(isEnable);
    }

    public void setDownloadPresent(String present) {
        TextView confirmBtn = findViewById(R.id.tv_upgrade_confirm);
        confirmBtn.setEnabled(false);
        confirmBtn.setText(present + "%");
    }
}
