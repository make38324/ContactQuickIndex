package com.czm.view;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/*
 * project:ContactQuickIndex
 * package:com.czm.view
 * author:曹智民
 * data:2015年6月4日 下午11:00:02
 */
public class QuickIndexView extends View{
	private String[] indexArr = {"#","A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private int touchIndex=-1;
	private float cellH;
	private float cellW;
	private float viewH;
	private int textColor=Color.WHITE;
	private int touchColor=Color.GREEN;
	private float textSize;
	private float touchSize;
	private Paint paint;
	private OnTouchChangeListener onTouchChangeListener;
	public void setOnTouchChangeListener(OnTouchChangeListener onTouchChangeListener) {
		this.onTouchChangeListener = onTouchChangeListener;
	}
	public QuickIndexView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint=new Paint();
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewH=getMeasuredHeight();
		cellH=viewH/indexArr.length;
		cellW=getMeasuredWidth();
		//指定字体大小
		textSize=cellH-5;
		touchSize=cellH+5;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < indexArr.length; i++) {
			String  indexchar= indexArr[i];
			//设置画笔
			paint.setTextSize(i==touchIndex?touchSize:textSize);
			paint.setColor(i==touchIndex?touchColor:textColor);
			//计算偏移
			float x=cellW/2-paint.measureText(indexchar)/2;
			FontMetrics fontMetrics = paint.getFontMetrics();
			float y=i*cellH+cellH/2-(fontMetrics.descent+fontMetrics.ascent)/2;
			canvas.drawText(indexchar, x, y, paint);
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			int currentToucheIndex=(int) (y/cellH);
			if(touchIndex!=currentToucheIndex&&onTouchChangeListener!=null){
				onTouchChangeListener.onTouchChage(getTouchWord(currentToucheIndex));
			}
			touchIndex=currentToucheIndex;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touchIndex=-1;
			invalidate();
			break;
		default:
			break;
		}
		return true;
	}
	public interface OnTouchChangeListener{
		public void onTouchChage(String touchword);
	}
	private String getTouchWord(int touchIndex){
	     if(touchIndex>0&&touchIndex<indexArr.length){
	    	 return indexArr[touchIndex];
	     }else {
			return "#";
		}
	}
}
