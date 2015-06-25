package com.example.tvapp.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.api.TVUrl;
import com.example.tvapp.bean.Video;
import com.example.tvapp.view.JRoundRectImageView;
import com.lidroid.xutils.BitmapUtils;

public class VideoAdapter extends BaseAdapter {
	private Context context;
	private List<Video> list;
	private BitmapUtils bp;

	public VideoAdapter(Context context, List<Video> list) {
		this.context = context;
		this.list = list;
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
	public View getView(int position, View view, ViewGroup viewgroup) {
		ViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = View.inflate(context, R.layout.appinfo_item, null);
			viewHolder.icon = (JRoundRectImageView) view.findViewById(R.id.app_icon);
			viewHolder.name = (TextView) view.findViewById(R.id.packagename);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		Video info = list.get(position);
		bp.display(viewHolder.icon, TVUrl.DefUrl + info.getImage());
		viewHolder.name.setText(info.getTitle());
		return view;
	}

	final class ViewHolder {
		public JRoundRectImageView icon;
		public TextView name;
	}
}
