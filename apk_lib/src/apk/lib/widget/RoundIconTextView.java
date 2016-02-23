package apk.lib.widget;

import com.joanzapata.iconify.widget.IconTextView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import apk.lib.R;
/**
 * 带圆角矩形的IconTextView,设置padding属性来确定图标与背景的间距
 * 
 */
public class RoundIconTextView extends IconTextView{
	private int bgColor;
	private int radiusSize;
	public RoundIconTextView(Context context) {
		this(context,null);
	}
	
	public RoundIconTextView(Context context,AttributeSet attrs) {
		this(context,attrs,0);
	}
	public RoundIconTextView(Context context,AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.RoundIconTextView, defStyle, 0);
		bgColor=a.getColor(R.styleable.RoundIconTextView_backgroundColor, Color.TRANSPARENT);
		radiusSize=a.getColor(R.styleable.RoundIconTextView_radiusSize, 0);
		a.recycle();
		//setPadding(20, 20, 20, 20);
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		if(bgColor!=Color.TRANSPARENT){
			Paint bgpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
			bgpaint.setColor(bgColor);
			RectF rect=new RectF(0, 0,getMeasuredWidth(),getMeasuredHeight());
			canvas.drawRoundRect(rect, radiusSize, radiusSize, bgpaint);
		}
		super.onDraw(canvas);
	}
	
}	
