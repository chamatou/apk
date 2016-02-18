package apk.lib.widget;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
/**
 * 带header和footer的RecyclerView.Adapter 
 *
 */
public class HTRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
	
	private boolean hasHeader;
	private boolean hasFooter;
	private DataHolder content;
	private DataHolder header;
	private DataHolder footer;
	private static final int ITEM_VIEW_TYPE_HEADER =1;
	private static final int ITEM_VIEW_TYPE_ITEM = 1<<1;
	private static final int ITEM_VIEW_TYPE_FOOTER=1<<2;
	public HTRecyclerViewAdapter(DataHolder content){
		this.content=content;
		hasFooter=false;
		hasHeader=false;
	}
	public void addHeader(DataHolder header){
		this.header=header;
		hasHeader=true;
	}
	public void addFooter(DataHolder footer){
		this.footer=header;
		hasFooter=true;
	}
	private boolean isHeader(int position){
		if(hasHeader&&position==0){
			return true;
		}
		return false;
	}
	private boolean isFooter(int position){
		int contentCount=content.getItemCount();
		if(hasFooter){
			contentCount+=1;
		}
		if(hasHeader){
			contentCount+=1;
		}
		return contentCount==position+1&&hasFooter;
	}
	public interface DataHolder{
		public ViewHolder onCreateViewHolder(ViewGroup parent);
		public void onBindViewHolder(ViewHolder holder, int position);
		public int getItemCount();
	}
	
	@Override
	public int getItemViewType(int position) {
		if(isHeader(position)){
			return ITEM_VIEW_TYPE_HEADER;
		}else if(isFooter(position)){
			return ITEM_VIEW_TYPE_FOOTER;
		}
		return ITEM_VIEW_TYPE_ITEM;
	}
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if(viewType==ITEM_VIEW_TYPE_HEADER){
			return header.onCreateViewHolder(parent);
		}else if(viewType==ITEM_VIEW_TYPE_FOOTER){
			return footer.onCreateViewHolder(parent);
		}
		return content.onCreateViewHolder(parent);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if(isHeader(position)){
			header.onBindViewHolder(holder,position);
		}else if(isFooter(position)){
			footer.onBindViewHolder(holder,position);
		}else{
			if(hasHeader)position-=1;
			content.onBindViewHolder(holder,position);			
		}
	}
	@Override
	public int getItemCount() {
		int size=content.getItemCount();
		if(hasHeader){
			size+=1;
		}
		if(hasFooter){
			size+=1;
		}
		return size;
	}
}
