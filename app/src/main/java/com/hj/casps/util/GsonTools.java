package com.hj.casps.util;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hj.casps.R;
import com.hj.casps.adapter.payadapter.PublicAdapter;

public class GsonTools {

	public GsonTools() {
		// TODO Auto-generated constructor stub
	}

	public static String createGsonString(Object object) {
		Gson gson = new Gson();
		String gsonString = gson.toJson(object);
		return gsonString;
	}

	public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
		Gson gson = new Gson();
		T t = gson.fromJson(gsonString, cls);
		return t;
	}

	public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
		Gson gson = new Gson();
		List<T> list = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(gsonString).getAsJsonArray();
		for(final JsonElement elem : array){
			list.add(gson.fromJson(elem, cls));
		}
		return list;
	}

	

	public static <T> List<Map<String, T>> changeGsonToListMaps(
			String gsonString) {
		List<Map<String, T>> list = null;
		Gson gson = new Gson();
		list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
		}.getType());
		return list;
	}

	public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
		Map<String, T> map = null;
		Gson gson = new Gson();
		map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
		}.getType());
		return map;
	}

	public static void CreateDialog(final TextView textView, final List<String> entities,Context mContext) {
//        final List<ResponseQueryPayEntity.AccountlistBean> accountlist = entities.get(listpos).getAccountlist();
		// 动态加载一个listview的布局文件进来
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.layout_list_popup_public, null);
		// 给ListView绑定内容
		ListView listView = (ListView) v.findViewById(R.id.layout_list_popup_public);
		PublicAdapter adapter = new PublicAdapter(entities);
//         qadapter = new AddressAdapter(context, entities);
		listView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
		final AlertDialog dialog = new AlertDialog.Builder(mContext)
				.setView(v)//在这里把写好的这个listview的布局加载dialog中
				.create();
		dialog.show();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String accountno = entities.get(position);
				textView.setText(accountno);
				dialog.dismiss();
			}
		});
	}




}
