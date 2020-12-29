package com.example.yrh2048;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity{


    private static final int XWIDTH = 1000;
    private static final int YHEIGH = 1500;
    GameController gameController;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.isOver = false;
                gameController = new GameController();
                view.invalidate();

            }
        });
        gameController = new GameController();
        initView();
        view.setOnTouchListener(new View.OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) { // 加此判断是为了解决当用户向斜方向滑动时程序应如何判断的问题

                            if (offsetX < -5) {
                                gameController.swipeLeft();   //向左滑动的处理
                                boolean b = gameController.newBox();
                                if (!b) {
                                    gameController.isOver = true;
                                }
                            } else if (offsetX > 5) {
                                gameController.swipeRight();
                                boolean b = gameController.newBox();
                                if (!b) {
                                    gameController.isOver = true;
                                }
                            }

                        } else { // 判断向上向下

                            if (offsetY < -5) {
                                gameController.swipeUp();
                                boolean b = gameController.newBox();
                                if (!b) {
                                    gameController.isOver = true;
                                }
                            } else if (offsetY > 5) {
                                gameController.swipeDown();
                                boolean b = gameController.newBox();
                                if (!b) {
                                    gameController.isOver = true;
                                }
                            }

                        }
                        break;
                }
                view.invalidate();
                return true; // 此处必须返回true
            }
        });
    }

    //初始化视图
    public void initView() {


        //1.得到父容器
        FrameLayout layoutGame = (FrameLayout) findViewById(R.id.layoutGame);
        //2.实例化游戏区域
        view = new View(this) {
            //重写游戏区域 绘制
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                //绘制
                gameController.draw(canvas);
            }
        };
        //3.设置游戏区域大小
        view.setLayoutParams(new ViewGroup.LayoutParams(XWIDTH, YHEIGH));

        //设置背景颜色
        //view.setBackgroundColor(Color.RED);

        //4.添加到父容器中
        layoutGame.addView(view);
    }


}