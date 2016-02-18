package apk.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import apk.lib.R;

public class ItemActionIconButton extends RippleView {
	private TextView name;
	private TextView detail;
	private View line;
	private ImageView icon;
	private ImageView action;
	public ItemActionIconButton(Context context) {
		this(context,null);
	}

	public ItemActionIconButton(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ItemActionIconButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWidget(context);
		setAttribute(context, attrs);
	}
	
	
	private void initWidget(Context context) {
		LayoutInflater.from(context).inflate(R.layout.item_action_icon_button,this,true);
		this.setRippleColor(0x7f080149);
		name=(TextView)findViewById(R.id.name);
		detail=(TextView)findViewById(R.id.detail);
		line=findViewById(R.id.line);
		icon=(ImageView)findViewById(R.id.icon);
		action=(ImageView)findViewById(R.id.action);
	}
	
	
	
	private void setAttribute(Context context, AttributeSet attrs){
		final TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.ItemActionButton);
		name.setText(ta.getString(R.styleable.ItemActionButton_iab_name));
		name.setTextColor(ta.getColor(R.styleable.ItemActionButton_iab_nameColor,R.color.grey_black));
		detail.setText(ta.getString(R.styleable.ItemActionButton_iab_detail));
		detail.setTextColor(ta.getColor(R.styleable.ItemActionButton_iab_detailColor,R.color.grey_400));
		boolean hasLine=ta.getBoolean(R.styleable.ItemActionButton_iab_hasLine,true);
		if(!hasLine){
			line.setVisibility(View.GONE);
		}else{
			line.setBackgroundColor(ta.getColor(R.styleable.ItemActionButton_iab_lineColor, R.color.grey_400));
		}
		Drawable d_icon=ta.getDrawable(R.styleable.ItemActionButton_iab_icon);
		if(d_icon!=null){
			icon.setBackgroundDrawable(d_icon);
		}
		Drawable d_action=ta.getDrawable(R.styleable.ItemActionButton_iab_action);
		if(d_action!=null){
			action.setBackgroundDrawable(d_action);
		}
		
	}
}
