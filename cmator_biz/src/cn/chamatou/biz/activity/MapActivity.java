package cn.chamatou.biz.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

@SuppressLint("NewApi")
public class MapActivity extends AppCompatActivity{
	private MapView mapView;
	private AMap aMap;
	private double lat;
	private double lng;
	LatLng old;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amap_activity);
		mapView=(MapView)findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 必须要写
	    aMap = mapView.getMap();
	    MarkerOptions opts=new MarkerOptions();
	    opts.draggable(true);
	    //获取当前坐标中心点
	    LatLng mTarget = aMap.getCameraPosition().target;
	    //CameraPosition cp=aMap.getCameraPosition();
	    //cp.target
	   // LatLng ll=new LatLng(39.9842d, 116.3053d);
	    opts.position(mTarget);
	    old=mTarget;
	    aMap.addMarker(opts);
	    aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
	    aMap.setMyLocationEnabled(true);
	    aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
			
			@Override
			public void onCameraChangeFinish(CameraPosition cp) {
				lat=cp.target.latitude;
				lng=cp.target.longitude;
			}
			
			@Override
			public void onCameraChange(CameraPosition cp) {
				lat=cp.target.latitude;
				lng=cp.target.longitude;
			}
		});
	    findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				float jl=AMapUtils.calculateLineDistance(new LatLng(lat, lng), old);
				Toast.makeText(MapActivity.this,jl+"M", Toast.LENGTH_LONG).show();
			}
		});
	}
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
