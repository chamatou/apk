package apk.lib.widget;

import com.joanzapata.iconify.widget.IconTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import apk.lib.WidgetAction;
import apk.lib.R;

public class ItemActionFontIconButton extends RippleView implements WidgetAction{
	private TextView name;
	private TextView detail;
	private View line;
	private IconTextView icon;
	private IconTextView action;
	private int trigger;
	private String actionData;
	public ItemActionFontIconButton(Context context) {
		this(context,null);
	}

	public ItemActionFontIconButton(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ItemActionFontIconButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWidget(context);
		setAttribute(context, attrs);
	}
	
	
	private void initWidget(Context context) {
		LayoutInflater.from(context).inflate(R.layout.item_action_font_icon_button,this,true);
		this.setRippleColor(0x7f080149);
		name=(TextView)findViewById(R.id.name);
		detail=(TextView)findViewById(R.id.detail);
		line=findViewById(R.id.line);
		icon=(IconTextView)findViewById(R.id.icon);
		action=(IconTextView)findViewById(R.id.action);
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
		String iconText=ta.getString(R.styleable.ItemActionButton_iab_fa_icon);
		if(iconText!=null){
			icon.setText(iconText);
			icon.setTextColor(ta.getColor(R.styleable.ItemActionButton_iab_fa_action_color,R.color.grey_400));
		}
		String actionText=ta.getString(R.styleable.ItemActionButton_iab_fa_action);
		if(actionText!=null){
			action.setText(actionText);
			action.setTextColor(ta.getColor(R.styleable.ItemActionButton_iab_fa_action_color,R.color.grey_400));
		}
		trigger=ta.getInt(R.styleable.ItemActionButton_iab_trigger, -1);
		actionData=ta.getString(R.styleable.ItemActionButton_iab_action_data);
		ta.recycle();
	}
	
	
	
	public void setTrigger(int trigger) {
		this.trigger = trigger;
	}

	public void setActionData(String actionData) {
		this.actionData = actionData;
	}

	@Override
	public int getActionIntent() {
		return this.trigger;
	}

	@Override
	public String getActionData() {
		return this.actionData;
	}
}
