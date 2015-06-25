package com.example.tvapp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.api.TVUrl;
import com.example.tvapp.bean.Doctor;
import com.example.tvapp.utils.Tools;
import com.example.tvapp.view.JRoundRectImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

public class AskAdapter extends BaseAdapter {
	private List<Doctor> list;
	private BitmapUtils bp;
	private LayoutInflater mInflater;

	public AskAdapter(Context context, List<Doctor> list) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
		bp = new BitmapUtils(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.appinfo_item, viewgroup, false);
			viewHolder.icon = (JRoundRectImageView) convertView.findViewById(R.id.app_icon);
			viewHolder.name = (TextView) convertView.findViewById(R.id.packagename);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Doctor info = list.get(position);
		bp.display(viewHolder.icon, TVUrl.DefUrl + info.getImage());
		bp.display(viewHolder.icon, TVUrl.DefUrl + info.getImage());
		viewHolder.name.setText(info.getName());
		return convertView;
	}

	final class ViewHolder {
		JRoundRectImageView icon;
		TextView name;
	}

}
