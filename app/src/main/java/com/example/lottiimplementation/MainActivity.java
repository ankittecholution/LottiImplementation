package com.example.lottiimplementation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.FontAssetDelegate;
import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.TextDelegate;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    LottieAnimationView box_animation1,box_animation2,box_animation3,box_animation4,circle_animation,circle_animation1;

    Bitmap b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        box_animation1 = findViewById(R.id.box_animation1);
        box_animation2 = findViewById(R.id.box_animation2);
        box_animation3 = findViewById(R.id.box_animation3);
        box_animation4 = findViewById(R.id.box_animation4);
        circle_animation = findViewById(R.id.circle_animation);
        circle_animation1 = findViewById(R.id.circle_animation1);

        box_animation1.setAnimation("Box_in_horizontal.json");
        box_animation2.setAnimation("Box_in&out.json");
        box_animation3.setAnimation("Box_out&in.json");
        box_animation4.setAnimation("Box_out_horizontal.json");

        circle_animation.setAnimation("Circle_to_Left.json");
        circle_animation.setImageAssetsFolder("images/");
        circle_animation1.setAnimation("Circle_to_Right.json");
        circle_animation1.setImageAssetsFolder("images/");
        b = readFromAssets();

        circle_animation.setFontAssetDelegate(new FontAssetDelegate(){
            public Typeface fetchFont(String fontFamily) {
                Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
                return customFont;
            }
        });
        circle_animation1.setFontAssetDelegate(new FontAssetDelegate(){
            public Typeface fetchFont(String fontFamily) {
                Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
                return customFont;
            }
        });

        boxIn();

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circle_animation.setAnimation("Box_out_horizontal.json");
                circle_animation.playAnimation();
            }
        });



        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circle_animation.setAnimation("Circle_to_Left.json");
                circle_animation.setImageAssetDelegate(new ImageAssetDelegate() {
                    @Nullable
                    @Override
                    public Bitmap fetchBitmap(LottieImageAsset asset) {
                        return readFromAssets();
                    }
                });
                TextDelegate t = new TextDelegate(circle_animation);
                t.setText("John Doe","Ankit");
                t.setText("Welcome","Sorry");
                circle_animation.setTextDelegate(t);
                circle_animation.playAnimation();

            }
        });


    }

    private void boxIn(){
        box_animation1.setVisibility(View.VISIBLE);
        box_animation1.playAnimation();
        box_animation2.setVisibility(View.GONE);
        box_animation3.setVisibility(View.GONE);
        box_animation4.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boxInIn();
                circleStart();
            }
        },2000);
    }

    private void boxInIn(){
        box_animation2.setVisibility(View.VISIBLE);
        box_animation2.playAnimation();
        box_animation3.setSpeed(0.3f);
        box_animation1.setVisibility(View.GONE);
        box_animation3.setVisibility(View.GONE);
        box_animation4.setVisibility(View.GONE);
    }
    private void boxOutIn(){
        box_animation3.setVisibility(View.VISIBLE);
        box_animation3.playAnimation();
        box_animation3.setSpeed(0.13f);
        box_animation2.setVisibility(View.GONE);
        box_animation4.setVisibility(View.GONE);
        box_animation1.setVisibility(View.GONE);
    }
    private void boxout(){
        box_animation4.setVisibility(View.VISIBLE);
        box_animation4.playAnimation();
        box_animation1.setVisibility(View.GONE);
        box_animation2.setVisibility(View.GONE);
        box_animation3.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boxIn();
            }
        },2000);
    }

    private void circleStart(){
//        circle_animation.addValueCallback(
//                new KeyPath("Fill 1", "**"),
//                LottieProperty.COLOR_FILTER,
//                new SimpleLottieValueCallback<ColorFilter>() {
//                    @Override
//                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
//                        return new PorterDuffColorFilter(Color.CYAN, PorterDuff.Mode.SRC_ATOP);
//                    }
//                }
//        );
        circle_animation.updateBitmap("image_0",b);
        TextDelegate t = new TextDelegate(circle_animation);
        t.setText("John Doe","Ankit");
        t.setText("Welcome","Sorry");
        circle_animation.setTextDelegate(t);
        circle_animation.playAnimation();
        circle_animation1.updateBitmap("image_0",b);
        TextDelegate t1 = new TextDelegate(circle_animation);
        t1.setText("John Doe","Rohit");
        t1.setText("Welcome","Welcome");
        circle_animation1.setTextDelegate(t1);
        circle_animation1.playAnimation();
        boxOutIn();
        circle_animation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                circle_animation.setVisibility(View.VISIBLE);
                circle_animation1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                boxout();
                circle_animation.setVisibility(View.GONE);
                circle_animation1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }



    private Bitmap readFromAssets() {
        Bitmap bitmap;
        AssetManager asm=this.getAssets();
        try {
            InputStream is = asm.open("test.jpg");
            bitmap= BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            Log.e("MainActivity", "[*]failed to open " + "as.jpg");
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

}