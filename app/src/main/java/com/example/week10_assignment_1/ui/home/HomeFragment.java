package com.example.week10_assignment_1.ui.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.week10_assignment_1.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        ViewGroup root = binding.getRoot();

        MyGraphicView myGraphicView = new MyGraphicView(root.getContext());
        ((ViewGroup) root).addView(myGraphicView);


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private static class MyGraphicView extends View {
        int startX = -1, startY = -1, stopX = -1, stopY = -1;
        List<Position> positions = new ArrayList<Position>();

        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(@NonNull MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("hwang", "move:x=" + event.getX() + "  y=" + event.getY()+ "  RawX="+event.getRawX()+" RawY="+event.getRawY());
                    break;
                case MotionEvent.ACTION_UP:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    positions.add(new Position(startX, startY, stopX, stopY));
                    this.invalidate();
                    break;
            }
            return true;
        }

        Paint paint = new Paint();
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);

            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLUE);

            for (Position position : positions) {
                canvas.drawLine(position.startX, position.startY, position.stopX, position.stopY, paint);
            }
        }

        private static class Position {
            private int startX, startY, stopX, stopY;

            public Position(int startX, int startY, int stopX, int stopY) {
                this.startX = startX;
                this.startY = startY;
                this.stopX = stopX;
                this.stopY = stopY;
            }
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}