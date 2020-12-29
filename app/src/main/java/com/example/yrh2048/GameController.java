package com.example.yrh2048;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    public boolean isOver;
    List<List<Integer>> map = new ArrayList<>(4);//初始为0
    Paint mapPaint = new Paint();
    private int[] paintcolor = new int[]{
            Color.parseColor("#BBBB00"),
            Color.parseColor("#FFD633"),
            Color.parseColor("#FFAD33"),
            Color.parseColor("#FF8533"),
            Color.parseColor("#FF5C33"),
            Color.parseColor("#FF1111"),
            Color.parseColor("#FF1111"),
            Color.parseColor("#FF1111"),
            Color.parseColor("#FF1111"),
            Color.parseColor("#FF1111")
    };
    private int boxSize = 200;

    public GameController() {
        map.add(new ArrayList<>(4));
        map.add(new ArrayList<>(4));
        map.add(new ArrayList<>(4));
        map.add(new ArrayList<>(4));
        for (List<Integer> a : map
        ) {
            a.add(0);
            a.add(0);
            a.add(0);
            a.add(0);
        }
        map.get(0).set(0, 2);
        map.get(0).set(1, 2);
    }

    public void draw(Canvas canvas) {

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {

                int b = map.get(i).get(j);
                if (b == 0) {
                    continue;
                }
                mapPaint.setColor(paintcolor[(int) (Math.log10(b) / Math.log10(2))]);
                RectF rectF = new RectF(
                        j * boxSize, i * boxSize, j * boxSize + boxSize, i * boxSize + boxSize);

                canvas.drawRoundRect(rectF, 15, 15, mapPaint);

                mapPaint.setColor(Color.WHITE);
                if (b / 10 == 0) {

                    mapPaint.setTextSize(200);
                } else if (b /100 == 0) {

                    mapPaint.setTextSize(125);
                } else if (b /1000 == 0) {

                    mapPaint.setTextSize(90);
                }else if (b / 10000 == 0) {

                    mapPaint.setTextSize(50);
                }
                canvas.drawText(
                        "" + b, rectF.centerX() - boxSize / 4, rectF.centerY() + boxSize / 4, mapPaint);
            }
        }
    }

    public void swipeLeft() {

        for (int i = 0; i < map.size(); i++) {
            List<Integer> list = new ArrayList<>();
            List<Integer> integerList = map.get(i);
            //得到不包含0的列表
            for (Integer n : integerList
            ) {
                if (n != 0) {
                    list.add(n);
                }
            }


            //双指针
            int a = 0, b = 1;
            int length = list.size();
            if (length == 0) {
                continue;
            }
            if (length == 1) {
                setZero(integerList);
                integerList.set(0, list.get(0));
            }

            List<Integer> NEW = new ArrayList<>();

            while (b < length) {
                Integer integer_a = list.get(a);
                if (integer_a != list.get(b)) {
                    NEW.add(integer_a);
                    a = b;
                    b++;
                } else {
                    NEW.add(integer_a * 2);
                    a += 2;
                    b += 2;
                }
            }

            if (a < length) {
                NEW.add(list.get(a));
            }
            addListTo4ByZero(NEW);

            map.set(i, NEW);
        }


    }

    void addListTo4ByZero(List<Integer> NEW) {
        while (NEW.size() < 4) {
            NEW.add(0);
        }
    }
    void addFirstListTo4ByZero(List<Integer> NEW) {
        while (NEW.size() < 4) {
            NEW.add(0, 0);
        }
    }

    void setZero(List<Integer> integers){
        for (int i = 0; i < integers.size(); i++) {
            integers.set(i, 0);
        }
    }


    public void swipeRight() {
        for (int i = 0; i < map.size(); i++) {
            List<Integer> list = new ArrayList<>();
            List<Integer> integerList = map.get(i);
            //得到不包含0的列表
            for (Integer n : integerList
            ) {
                if (n != 0) {
                    list.add(n);
                }
            }


            //双指针
            int a = 0, b = 1;
            int length = list.size();
            if (length == 0) {
                continue;
            }
            if (length == 1) {
                setZero(integerList);
                integerList.set(length - 1 - 0, list.get(0));
            }

            List<Integer> NEW = new ArrayList<>();

            while (b < length) {
                Integer integer_a = list.get(length - 1 - a);
                if (integer_a != list.get(length - 1 - b)) {

                    NEW.add(0, integer_a);
                    a = b;
                    b++;
                } else {
                    NEW.add(0,integer_a * 2);
                    a += 2;
                    b += 2;
                }
            }

            if (a < length) {
                NEW.add(0, list.get(length - 1 - a));
            }
            addFirstListTo4ByZero(NEW);
            map.set(i, NEW);
        }
    }


    public void swipeUp() {
        for (int i = 0; i < map.get(0).size(); i++) {
            List<Integer> list = new ArrayList<>();
            List<Integer> integerList = getIColumn(map, i);
            //得到不包含0的列表
            for (Integer n : integerList
            ) {
                if (n != 0) {
                    list.add(n);
                }
            }


            //双指针
            int a = 0, b = 1;
            int length = list.size();
            if (length == 0) {
                continue;
            }
            if (length == 1) {
                setIColumnZero(map, i);
                map.get(0).set(i, list.get(0));
            }

            List<Integer> NEW = new ArrayList<>();

            while (b < length) {
                Integer integer_a = list.get(a);
                if (integer_a != list.get(b)) {
                    NEW.add(integer_a);
                    a = b;
                    b++;
                } else {
                    NEW.add(integer_a * 2);
                    a += 2;
                    b += 2;
                }
            }

            if (a < length) {
                NEW.add(list.get(a));
            }
            addListTo4ByZero(NEW);

            setIColumnDatas(map, i, NEW);
        }
    }

    private void setIColumnZero(List<List<Integer>> map, int i) {
        for (int j = 0; j < 4; j++) {

            map.get(j).set(i, 0);

        }
    }

    private void setIColumnDatas(List<List<Integer>> map, int i, List<Integer> aNew) {
        for (int j = 0; j < 4; j++) {

            map.get(j).set(i, aNew.get(j));
        }
    }

    private List<Integer> getIColumn(List<List<Integer>> map, int i) {
        List<Integer> list = new ArrayList<>();
        list.add(map.get(0).get(i));
        list.add(map.get(1).get(i));
        list.add(map.get(2).get(i));
        list.add(map.get(3).get(i));
        return list;
    }

    public void swipeDown() {
        for (int i = 0; i < map.get(0).size(); i++) {
            List<Integer> list = new ArrayList<>();
            List<Integer> integerList = getIColumn(map, i);
            //得到不包含0的列表
            for (Integer n : integerList
            ) {
                if (n != 0) {
                    list.add(n);
                }
            }


            //双指针
            int a = 0, b = 1;
            int length = list.size();
            if (length == 0) {
                continue;
            }
            if (length == 1) {
                setIColumnZero(map, i);
                map.get(3).set(i, list.get(0));
            }

            List<Integer> NEW = new ArrayList<>();

            while (b < length) {
                Integer integer_a = list.get(length - 1 - a);
                if (integer_a != list.get(length - 1 - b)) {
                    NEW.add(0,integer_a);
                    a = b;
                    b++;
                } else {
                    NEW.add(0,integer_a * 2);
                    a += 2;
                    b += 2;
                }
            }

            if (a < length) {
                NEW.add(0,list.get(length - 1 - a));
            }
            addFirstListTo4ByZero(NEW);

            setIColumnDatas(map, i, NEW);
        }
    }

    public boolean newBox() {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {
                if (map.get(i).get(j) == 0) {
                    pairs.add(new Pair<>(i, j));
                }
            }
        }
        if (pairs.size() == 0) {
            return false;
        }
        Random random = new Random(System.currentTimeMillis());
        int m = random.nextInt(pairs.size());
        Pair<Integer, Integer> pair = pairs.get(m);
        int n = random.nextInt(2);
        map.get(pair.first).set(pair.second, n == 1 ? 4 : 2);
        return true;
    }
}
