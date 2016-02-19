package apk.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View写法
 *
 */
public abstract class AbstractCustomeView extends View{

	public AbstractCustomeView(Context context) {
		super(context,null);
	}
	public AbstractCustomeView(Context context,AttributeSet attrs) {
		this(context,attrs,0);
	}
	public AbstractCustomeView(Context context,AttributeSet attrs,int defStyle) {
		super(context,attrs,defStyle);
	}
	/*
	 * 完成对控件的测量,处理warp_content时的情况
	 * mWidth,mHeight为指定为warp_content时的默认大小
	 */
	private int mWidth;
	private int mHeight;
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecMode=MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize=MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode=MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);
		if(widthSpecMode==MeasureSpec.AT_MOST&&heightSpecMode==MeasureSpec.AT_MOST){
			setMeasuredDimension(mWidth, mHeight);
		}else if(widthSpecMode==MeasureSpec.AT_MOST){
			setMeasuredDimension(mWidth, heightSpecSize);
		}else if(heightSpecMode==MeasureSpec.AT_MOST){
			setMeasuredDimension(widthSpecSize, mHeight);
		}
	}
	
}
