package apk.lib.image;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
public class ImageUtils {
	/**
	 * drawable转bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitamp(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}
	/**
	 * 以最佳采样率从资源文件中加载图片
	 * @param pathName
	 * @param reqWidth 请求加载的图片宽度,即ImageView的宽度
	 * @param reqHeight请求加载的图片高度,即ImageView的高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmap(String pathName,int reqWidth,int reqHeight){
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(pathName,options);
		options.inSampleSize=computerInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds=false;
		return BitmapFactory.decodeFile(pathName,options);
	}
	/**
	 * 以最佳采样率从资源文件中加载图片
	 * @param res
	 * @param resId
	 * @param reqWidth 请求加载的图片宽度,即ImageView的宽度
	 * @param reqHeight请求加载的图片高度,即ImageView的高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmap(Resources res,int resId,int reqWidth,int reqHeight){
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		BitmapFactory.decodeResource(res, resId,options);
		options.inSampleSize=computerInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds=false;
		return BitmapFactory.decodeResource(res, resId,options);
	}
	
	/**
	 * 计算请求图片的最佳采样率
	 * @param options
	 * @param reqWidth 请求加载的图片宽度,即ImageView的宽度
	 * @param reqHeight请求加载的图片高度,即ImageView的高度
	 * @return
	 */
	public static final int computerInSampleSize(BitmapFactory.Options options,
			int reqWidth,int reqHeight){
		int inSampleSize=1;
		if(reqWidth<=0||reqHeight<=0){
			return inSampleSize;
		}
		final int width=options.outWidth;
		final int height=options.outHeight;
		if(height>reqHeight||width>reqWidth){
			final int halfHeight=height/2;
			final int halfWidth=width/2;
			while((halfHeight/inSampleSize)>=reqHeight
					&&(halfWidth/inSampleSize)>=reqWidth){
				inSampleSize*=2;
			}
		}
		return inSampleSize;
	}
}
