package edu.ktu.labaratorywork1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class IndicateArrayCountView extends IndicatingView{
    private int number = 0;

    public IndicateArrayCountView (Context context) { super(context);}

    public IndicateArrayCountView(Context context, AttributeSet attrs) {super(context, attrs);}

    public IndicateArrayCountView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public void setNumber(int number){this.number = number;}
    public int getNumber(){return this.number;}

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paint;paint = new Paint();
        paint.setColor(Color.DKGRAY);
        canvas.drawCircle(width/2, height/2, 100, paint);
        Paint paint2 = new Paint();
        paint2.setColor(Color.LTGRAY);
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setTextSize(120);
        canvas.drawText(Integer.toString(number),width/2, height/2+40, paint2);}
}
