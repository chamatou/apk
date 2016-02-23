package apk.lib.widget;

import com.joanzapata.iconify.widget.IconTextView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import apk.lib.R;
/**
 * 圆形背景的IconTextView,设置padding属性来确定图标与背景的间距
 * 
 */
public class CircleIconTextView extends IconTextView{
	private int bgColor;
	public CircleIconTextView(Context context) {
		this(context,null);
	}
	
	public CircleIconTextView(Context context,AttributeSet attrs) {
		this(context,attrs,0);
	}
	public CircleIconTextView(Context context,AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.RoundIconTextView, defStyle, 0);
		bgColor=a.getColor(R.styleable.RoundIconTextView_backgroundColor, Color.TRANSPARENT);
		a.recycle();
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		if(bgColor!=Color.TRANSPARENT){
			Paint bgpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
			bgpaint.setColor(bgColor);
			int cx=getMeasuredWidth()/2;
			int cy=getMeasuredHeight()/2;
			canvas.drawCircle(cx,cy,cx-1, bgpaint);
		}
		super.onDraw(canvas);
	}
	
}	
