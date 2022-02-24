package com.llf.t;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


public class MyBottomView extends LinearLayout {

    private int selectColor;
    private int color;
    private int[] tabsIcon;
    private int[] tabsSelectIcon;

    private Context context;
    private int currentTab = 0;


    public MyBottomView(Context context) {
        super(context);
        this.context = context;
        this.setOrientation(HORIZONTAL);
    }

    public MyBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setOrientation(HORIZONTAL);
    }

    public void setMyBottomView(Builder builder) {

        this.color = builder.color;
        this.selectColor = builder.selectColor;
        this.tabsIcon = builder.tabsIcon;
        this.tabsSelectIcon = builder.tabsSelectIcon;
        String[] tabsName = builder.tabsName;
        int w = builder.iconW == 0 ? 36 : builder.iconW; // 默认36dp
        int h = builder.iconH == 0 ? 36 : builder.iconH;// 默认36dp
        int txtSize = builder.txtSize == 0 ? 12 : builder.txtSize;// 默认12sp
        int txtTop = builder.txtTop;// 默认0dp

        for (int i = 0; i < tabsName.length; i++) {
            final int tab = i;
            LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.foot_view, null);
            LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
            layout.setLayoutParams(lp);

            ImageView imageView = layout.findViewById(R.id.iv_icon);
            LayoutParams imgLp = new LayoutParams(dp2px(w), dp2px(h));
            imageView.setLayoutParams(imgLp);

            View vTop = layout.findViewById(R.id.v_top);
            LayoutParams vLp = new LayoutParams(1, dp2px(txtTop));
            vTop.setLayoutParams(vLp);

            TextView textView = layout.findViewById(R.id.tv_name);
            textView.setText(tabsName[i]);
            textView.setTextSize(Dimension.SP, txtSize);
            if (currentTab == i) {
                textView.setTextColor(selectColor);
                imageView.setImageResource(tabsSelectIcon[i]);
            } else {
                textView.setTextColor(color);
                imageView.setImageResource(tabsIcon[i]);
            }

            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentTab == tab) return;
                    currentTab = tab;
                    selectItem();
                }
            });

            this.addView(layout);
        }
    }


    private void selectItem() {
        for (int i = 0; i < getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) getChildAt(i);
            ImageView imageView = (ImageView) layout.getChildAt(0);
            TextView textView = (TextView) layout.getChildAt(2);
            if (currentTab == i) {
                textView.setTextColor(selectColor);
                imageView.setImageResource(tabsSelectIcon[i]);
            } else {
                textView.setTextColor(color);
                imageView.setImageResource(tabsIcon[i]);
            }
        }
    }

    public static class Builder {
        int selectColor;
        int color;
        String[] tabsName;
        int[] tabsIcon;
        int[] tabsSelectIcon;
        int iconH;
        int iconW;
        int txtSize;
        int txtTop; // 文字距离图片的距离
        ViewPager viewPager;

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder selectColor(int selectColor) {
            this.selectColor = selectColor;
            return this;
        }

        public Builder tabsName(String[] tabs) {
            this.tabsName = tabs;
            return this;
        }

        public Builder tabsIcon(int[] tabsIcon) {
            this.tabsIcon = tabsIcon;
            return this;
        }

        public Builder tabsSelectIcon(int[] tabsSelectIcon) {
            this.tabsSelectIcon = tabsSelectIcon;
            return this;
        }

        public Builder iconW(int iconW) {
            this.iconW = iconW;
            return this;
        }

        public Builder iconH(int iconH) {
            this.iconH = iconH;
            return this;
        }

        public Builder txtSize(int txtSize) {
            this.txtSize = txtSize;
            return this;
        }

        public Builder txtTop(int txtTop) {
            this.txtTop = txtTop;
            return this;
        }

        public Builder viewPager(ViewPager viewPager) {
            this.viewPager = viewPager;
            return this;
        }


        public MyBottomView create(Context context) {
            return new MyBottomView(context);
        }
    }


    private int dp2px(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
