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
import com.example.tvapp.api.TVUrl;
import com.example.tvapp.bean.Exam;
import com.lidroid.xutils.BitmapUtils;

public class PsyTestAdapter extends BaseAdapter {

	private List<Exam> Infos;

	private Context mContext;

	private int mVodNum;

	private int startEpios;
	private BitmapUtils bp;
	
	private LayoutInflater mInflater;

	public PsyTestAdapter(List<Exam> examList, Context mContext) {
		super();
		this.Infos = examList;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		bp = new BitmapUtils(mContext);

	}

	public void setmVodNum(int mVodNum) {
		this.mVodNum = mVodNum;
	}

	@Override
	public int getCount() {
		return Infos.size();
	}

	@Override
	public Object getItem(int arg0) {
		return Infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int postion, View convertView, ViewGroup group) {
		ViewHolder mHolder = null;
		Exam mInfos = Infos.get(postion);
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.gridview_item, null);
			mHolder = new ViewHolder();
			mHolder.ImageView = (ImageView) convertView.findViewById(R.id.ItemImage);
			mHolder.mText = (TextView) convertView.findViewById(R.id.texts);

			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		bp.display(mHolder.ImageView, TVUrl.DefUrl + Infos.get(postion).getImage());
		mHolder.mText.setText(mInfos.getTitle());

		return convertView;
	}

	class ViewHolder {
		ImageView ImageView;
		TextView mText;
	}
}
