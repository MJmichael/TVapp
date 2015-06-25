package com.example.tvapp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.bean.Music;
import com.example.tvapp.utils.TVPlayer;

public class FMAdapter extends BaseAdapter {
	private Context context;
	private List<Music> list;
	private LayoutInflater mInflater;

	public FMAdapter(Context context, List<Music> list2) {
		this.context = context;
		this.list = list2;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fm_item, viewgroup, false);
			viewHolder.icon = (ImageView) convertView.findViewById(R.id.iv_state);
			viewHolder.name = (TextView) convertView.findViewById(R.id.mp_name);
			viewHolder.current = (TextView) convertView.findViewById(R.id.mp_current);
			viewHolder.total = (TextView) convertView.findViewById(R.id.mp_total);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Music info = list.get(position);

		viewHolder.name.setText(info.getTitle());
		if(TVPlayer.STATE_STOP==info.getPlay_state())
			viewHolder.current.setText("00:00");

		if (info.isPlay()) {
			viewHolder.icon.setBackgroundResource(R.drawable.iv_stop);

		} else {
			viewHolder.icon.setBackgroundResource(R.drawable.iv_music);
		}

		return convertView;
	}

	final class ViewHolder {
		ImageView icon;
		TextView name;
		TextView current;
		TextView total;
	}

}
