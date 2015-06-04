package com.czm.contact;

import java.util.List;

import com.czm.pojo.ContactInfo;
import com.czm.util.ContactUtil;
import com.czm.view.QuickIndexView;
import com.czm.view.QuickIndexView.OnTouchChangeListener;

import android.R.integer;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity implements OnTouchChangeListener {
	private List<ContactInfo> data;
	private ListAdapter adapter;
	private QuickIndexView qiv_contact;
	private TextView tv_checked;
	private Handler handler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_checked=(TextView) findViewById(R.id.tv_checked);
		tv_checked.setVisibility(View.GONE);
		data = ContactUtil.getContactInfos(this);
		adapter=new ContactAdapter();
		setListAdapter(adapter);
		qiv_contact=(QuickIndexView) findViewById(R.id.qiv_contact);
		qiv_contact.setOnTouchChangeListener(this);
	}
	class ContactAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return data.size();
		}
		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			ContactInfo contactInfo=data.get(position);
			if (convertView == null) {
				convertView = View
						.inflate(getApplicationContext(), R.layout.item_contact, null);
				holder = new ViewHolder();
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
				holder.tv_item_word=(TextView) convertView.findViewById(R.id.tv_item_word);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_name.setText(contactInfo.getName());
			holder.tv_number.setText(contactInfo.getNumber());
			String firstPinyin = contactInfo.getPinyin().substring(0, 1);
			holder.tv_item_word.setText(firstPinyin);
			//设置可见性
			if(position==0){
				holder.tv_item_word.setVisibility(View.VISIBLE);
			}else {
				ContactInfo preContactInfo = data.get(position-1);
				String prefirString=preContactInfo.getPinyin().substring(0,1);
				if(firstPinyin.equals(prefirString)){
					holder.tv_item_word.setVisibility(View.GONE);
				}else {
					holder.tv_item_word.setVisibility(View.VISIBLE);
				}
			}
			return convertView;
		}
	}

	class ViewHolder {
		TextView tv_name;
		TextView tv_number;
		TextView tv_item_word;
	}

	@Override
	public void onTouchChage(String touchword) {
		locationWord(touchword);
		showWord(touchword);
	}
	private void showWord(final String touchword) {
		handler.removeCallbacksAndMessages(null);
		tv_checked.setText(touchword);
		tv_checked.setVisibility(View.VISIBLE);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				tv_checked.setVisibility(View.GONE);
			}
		}, 1000);
	}
	public void locationWord(String word){
		for(int i=0;i<data.size();i++){
			String pingyin=data.get(i).getPinyin();
			if(i<data.size()-1){
				String nextpingyin=data.get(i+1).getPinyin();
				if(pingyin.equals(word)){
					setSelection(i);
					return;
				}
				if(nextpingyin.compareTo(word)>0){
					setSelection(i);
					return;
				}
			}else {
				setSelection(i);
				return;
			}
		}
	}
}
