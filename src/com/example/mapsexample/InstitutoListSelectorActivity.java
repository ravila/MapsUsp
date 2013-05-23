package com.example.mapsexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class InstitutoListSelectorActivity extends ListActivity {
	
	private InstitutoDatabaseHandler instDB;
	private ArrayList<Integer> resultList;
	private Button bShowMap;
	private Button bShowAllInstitutos;
	private CheckBox checkAll;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instituto_list_selector);
		
		Intent intent = getIntent();
		resultList = intent.getIntegerArrayListExtra("list");
		
		lv = getListView();
		instDB = new InstitutoDatabaseHandler(this);
		bShowMap = (Button) findViewById(R.id.bShowMap);
		bShowAllInstitutos = (Button) findViewById(R.id.bShowAll);
		checkAll = (CheckBox) findViewById(R.id.checkAll);
		
		bShowMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putIntegerArrayListExtra("result", resultList);
				setResult(RESULT_OK, i);
				finish();
			}
		});
		
		bShowAllInstitutos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showList(instDB.getAllInstitutos());
			}
		});
		
		checkAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				selecetAll(isChecked);
			}
		});
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg) {
				CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
				TextView tvId = (TextView) view.findViewById(R.id.id);
				Integer id = Integer.parseInt(tvId.getText().toString());
				cb.setChecked(!cb.isChecked());
				if(cb.isChecked()) 
					resultList.add(id);
				else
					resultList.remove(id);
			}
		});
		
		showList(instDB.getAllInstitutos());
	}

	protected void selecetAll(boolean isChecked) {
		if(isChecked) {
			for (View view : lv.getTouchables()) {
				CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
				TextView tvId = (TextView) view.findViewById(R.id.id);
				Integer id = Integer.parseInt(tvId.getText().toString());
				if(!cb.isChecked()) {
					resultList.add(id);
					cb.setChecked(true);
				}
			}
		}
		else {
			for (View view : lv.getTouchables()) {
				CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
				TextView tvId = (TextView) view.findViewById(R.id.id);
				Integer id = Integer.parseInt(tvId.getText().toString());
				if(cb.isChecked()) {
					resultList.remove(id);
					cb.setChecked(false);
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.instituto_list_selector, menu);
		return true;
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			showToast(query);
			showList(instDB.getInstutosBySearch(query));
		}
	}

	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		i.putIntegerArrayListExtra("result", resultList);
		setResult(RESULT_OK, i);
		finish();
	}
	
	private void showList(List<Instituto> listInst) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		
		for (Instituto inst : listInst) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", inst.getName());
			map.put("category", ECategory.getStringName(inst.getCategory()));
			map.put("id", inst.getId());
			if(resultList.contains(inst.getId()))
				map.put("checkBox", true);
			else
				map.put("checkBox", false);
			
			list.add(map);
		}
		
		ListAdapter adapter = new SimpleAdapter(this, list, R.layout.instituto_row_list, 
				new String[] {"name", "category", "id", "checkBox"}, 
				new int[] {R.id.name, R.id.endereco, R.id.id, R.id.checkBox});
		
		setListAdapter(adapter);
	}

}
