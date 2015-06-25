package com.example.tvapp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.bean.ExamQues;
import com.example.tvapp.utils.MyToast;

public class ExamTestAdapter extends BaseAdapter {
	private List<ExamQues> QuesList;
	private int count[];
	private LayoutInflater mInflater;
	private Context context;

	public ExamTestAdapter(Context context, List<ExamQues> list2) {
		this.mInflater=LayoutInflater.from(context);
		this.QuesList = list2;
		this.context=context;
		this.count = new int[QuesList.size()];
	}

	@Override
	public int getCount() {
		return QuesList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return QuesList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup viewgroup) {
		final ViewHolder viewHolder;
		
		if(convertview==null){
			viewHolder=new ViewHolder();
		convertview = mInflater.inflate( R.layout.psy_test_list_item, viewgroup,false);
		viewHolder.radioGroup = (RadioGroup) convertview.findViewById(R.id.radioGroup1);
		viewHolder.title = (TextView) convertview.findViewById(R.id.tv_title);
		convertview.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertview.getTag();
		}
		ExamQues examQues = QuesList.get(position);
		int size;
		if (examQues.getAnswerC() == null) {
			size = 2;
		} else {
			size = 3;
		}
		// 添加单选按钮
		for (int i = 0; i < size; i++) {
			RadioButton radio = new RadioButton(context);
			switch (i) {
			case 0:
				radio.setText(examQues.getAnswerA());
				break;
			case 1:
				radio.setText(examQues.getAnswerB());

				break;
			case 2:
				radio.setText(examQues.getAnswerC());

				break;

			default:
				break;
			}
			radio.setId(i);// 设置ID 要不然不认识了
			radio.setBackgroundResource(R.drawable.bg_backgroud);
			viewHolder.title.setText(examQues.getQuestion());
			radio.setButtonDrawable(null);
			radio.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			radio.setPadding(50, 20, 50, 20);
			radio.setTextSize(20);
			viewHolder.radioGroup.addView(radio);
		}

		viewHolder.radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 以下就可以对这个RadioButton进行处理了
				ExamQues examQues = QuesList.get(position);
				boolean onclick = examQues.isOnclick();

				if (onclick) {
					MyToast.showS(context, "被点击过");
					switch (checkedId) {
					case 0:
						int scoreA = Integer.parseInt(examQues.getScoreA());
						count[position] = scoreA;
						break;
					case 1:
						int scoreB = Integer.parseInt(examQues.getScoreB());
						count[position] = scoreB;
						break;
					case 2:
						int scoreC = Integer.parseInt(examQues.getScoreC());
						count[position] = scoreC;
						break;

					default:
						break;
					}

				} else {
					MyToast.showS(context, "没有被点击过");
					QuesList.get(position).setOnclick(true);
					switch (checkedId) {
					case 0:
						int scoreA = Integer.parseInt(examQues.getScoreA());
						count[position] = scoreA;
						break;
					case 1:
						int scoreB = Integer.parseInt(examQues.getScoreB());
						count[position] = scoreB;
						break;
					case 2:
						int scoreC = Integer.parseInt(examQues.getScoreC());
						count[position] = scoreC;
						break;
					default:
						break;
					}
				}

				// MyToast.showS(context, "checkedId=" + checkedId);
			}

		});
		return convertview;
	}
	
	final class ViewHolder{
		 RadioGroup radioGroup;
		 TextView title;
	}
	

}
