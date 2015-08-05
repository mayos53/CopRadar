package com.app.copradar.activities.base;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.app.copradar.R;
import com.app.copradar.controller.AppController;
import com.app.copradar.util.util.Tools;


/**
 * Created by Yossef on 5/11/15.
 */
public class BaseActivity extends FragmentActivity {




    private PopupWindow popupLoading;
    private AnimationDrawable animationLoading;
    protected View contentView;

    protected AppController app;
    protected boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = AppController.getInstance(getApplicationContext());
    }

    @Override
    public void setContentView(int id) {
        this.contentView = LayoutInflater.from(this).inflate(id,null);
        setContentView(contentView);
    }


    public void goToFragment(int containerId, Fragment fragment, Bundle args, String tag, boolean replace) {
        if(args != null){
            fragment.setArguments(args);
        }
        if (replace) {
            getSupportFragmentManager().beginTransaction().replace(containerId, fragment,
                    tag).commitAllowingStateLoss();
        } else {
            getSupportFragmentManager().beginTransaction().add(containerId, fragment,
                    tag).commitAllowingStateLoss();
        }
    }

    public void showLoading() {

       if(popupLoading == null) {
           View view = LayoutInflater.from(this).inflate(
                   R.layout.loading, null);
           int width = (int) Tools.convertDpToPixel(80, this);
           int height = (int) Tools.convertDpToPixel(80, this);

           popupLoading = new PopupWindow(width, height);
           popupLoading.setContentView(view);
           popupLoading.setBackgroundDrawable(new BitmapDrawable());


           View contentLoader = (ImageView) view.findViewById(R.id.dataLoader);
           AnimationDrawable loaderDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable
                   .progress_bar);

           contentLoader.setVisibility(View.VISIBLE);
           contentLoader.setBackgroundDrawable(loaderDrawable);

           // Start Loading animation
           animationLoading = (AnimationDrawable) contentLoader.getBackground();
       }
        isLoading = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLoading) {
                    popupLoading.showAtLocation(contentView, Gravity.CENTER, 0, 0);
                    animationLoading.start();
                }

            }
        },500);


    }



    public void hideLoading() {
        isLoading = false;
        if(animationLoading != null) {
            animationLoading.stop();
        }
        if(popupLoading != null){
            popupLoading.dismiss();
        }
    }

    public void showError(int errorResourceId){
        Tools.showToast(this, errorResourceId);

    }

    public void showError(int title,int message){
        Tools.showAlert(this,title,message);

    }



}
