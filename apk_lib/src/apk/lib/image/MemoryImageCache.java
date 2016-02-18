package apk.lib.image;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
/**
 * Volly内存图片缓存,基于LruCache
 * 
 */
public class MemoryImageCache implements ImageCache{
	private LruCache<String, Bitmap> cache;
	public MemoryImageCache(){
		//这个取单个应用最大使用内存的1/8
	    int maxSize=(int)Runtime.getRuntime().maxMemory()/8;
	    cache=new LruCache<String, Bitmap>(maxSize){
	      @Override
	      protected int sizeOf(String key, Bitmap value) {
	    //这个方法一定要重写，不然缓存没有效果
	        return value.getHeight()*value.getRowBytes();
	      }
	    };
	}
	@Override
	public Bitmap getBitmap(String key) {
		 return cache.get(key);
	}

	@Override
	public void putBitmap(String key, Bitmap bitmap) {
		cache.put(key, bitmap);
	}

}
