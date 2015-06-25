package com.example.tvapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.bean.Info;

public class saMenuItemAdapter extends BaseAdapter {

	private ArrayList<Info> assetListInfos;

	private Context mContext;

	private int mVodNum;

	private int startEpios;
	
	private LayoutInflater mInflater;

	public saMenuItemAdapter(ArrayList<Info> assetListInfos, Context mContext) {
		super();
		this.mInflater = LayoutInflater.from(mContext);
		this.assetListInfos = assetListInfos;
		this.mContext = mContext;

	}

	public void setmVodNum(int mVodNum) {
		this.mVodNum = mVodNum;
	}

	@Override
	public int getCount() {
		return assetListInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		return assetListInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int postion, View convertView, ViewGroup group) {
		ViewHolder mHolder = null;
		Info mInfos = assetListInfos.get(postion);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.gridview_item, null);
			mHolder = new ViewHolder();
			mHolder.ImageView = (ImageView) convertView.findViewById(R.id.ItemImage);
			mHolder.mText = (TextView) convertView.findViewById(R.id.texts);
			mHolder.ImageView.setBackgroundResource(mInfos.getId());
			mHolder.mText.setText(mInfos.getName());
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	final class ViewHolder {
		ImageView ImageView;
		TextView mText;
	}
}
