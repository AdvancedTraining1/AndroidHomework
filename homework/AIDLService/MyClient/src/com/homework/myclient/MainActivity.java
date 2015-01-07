package com.homework.myclient;

import com.aidl.DataItem;
import com.aidl.IMyService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button bindBtn;  
	private IMyService mRemoteService; 
	private EditText edit;
	private TextView text;
	private String str;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private DataItem item;
	private int m_type = 0;
	
	private int getType()
	{
		return m_type;
	}
	
	private void setType(int temp)
	{
		m_type = temp;
	}
	
	private ServiceConnection mRemoteConnection = new ServiceConnection() {  
		  
        @Override  
        public void onServiceConnected(ComponentName name, IBinder service) {  
            Log.i("ServiceConnection", "onServiceConnected() called");  
            mRemoteService = IMyService.Stub.asInterface(service);  
        }  
  
        @Override  
        public void onServiceDisconnected(ComponentName name) {  
        	//android.intent.action.RemoteService
            Intent intent = new Intent("android.intent.action.RemoteService"); 
            bindService(intent, mRemoteConnection, Context.BIND_AUTO_CREATE);//This is called when the connection with the service has been unexpectedly disconnected,  
            //that is, its process crashed. Because it is running in our same process, we should never see this happen.  
            Log.i("ServiceConnection", "onServiceDisconnected() called");  
        }

    };  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edit = (EditText) findViewById(R.id.edit1);
		bindBtn = (Button)this.findViewById(R.id.button1);  
		text = (TextView)this.findViewById(R.id.text1);  
		spinner = (Spinner) findViewById(R.id.Spinner1);
		
		item = new DataItem();
		
		
		// 建立数据源
		String[] mItems = getResources().getStringArray(R.array.spinnername);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
		//绑定 Adapter到控件
		spinner.setAdapter(_Adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view,
		            int position, long id) {
		        String str=parent.getItemAtPosition(position).toString();
		        Toast.makeText(getApplicationContext(), "你点击的是:"+str, 2000).show();
		        
		        if(str.equals("JN"))
            	{
            		setType(0);
            		//item.setType(0); 
            	}else if(str.equals("JR"))
            	{
            		setType(1);
            	}else if(str.equals("NN"))
            	{
            		setType(2);
            	}else if(str.equals("NR"))
            	{
            		setType(3);
            	}
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
		        // TODO Auto-generated method stub
		    }
		});

		
		edit.setOnEditorActionListener(new OnEditorActionListener() {  

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				//str =(String) v.getText(); 
				// TODO Auto-generated method stub
				return false;
			}  
        }); 
		
		bindBtn.setOnClickListener(new View.OnClickListener() 
		{  
			int m_type = 0;
            @Override  
            public void onClick(View v) 
            {  
            	
            	//String str = spinner.getContext().toString();
            	
            	
            	int temp = 0;
            	if(edit.getText().toString().length() == 0)
            	{
            		temp = 0;
            	}else 
            	{
            		temp = Integer.parseInt(edit.getText().toString());
            	}
            	
            	//Toast.makeText(getApplicationContext(), "是:"+ getType(), 2000).show();
            	item.setNum(temp);
            	item.setType(getType());
   
	            try {
					mRemoteService.saveDataInfo(item);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
         
            	 
                //text.setText(edit.getText().toString());
                //spinner.getContext().toString();
	            int a = 0;
				try {
					switch(getType())
					{
					case 0:
						a = mRemoteService.JN();
						break;
					case 1:
						a = mRemoteService.JR();
						break;
					case 2:
						a = mRemoteService.NN();
						break;
					case 3:
						a = mRemoteService.NR();
						break;
					}
					//a = mRemoteService.JN();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                //Toast.makeText(getApplicationContext(), "你选择的是:"+spinner.getSelectedItem().toString(), 2000).show();
                Toast.makeText(getApplicationContext(), "结果是:"+ a, 2000).show();
                //bindBtn.setEnabled(false);  
            }  
        });
		
		//android.intent.action.RemoteService
        Intent intent = new Intent("android.intent.action.RemoteService"); 
        bindService(intent, mRemoteConnection, Context.BIND_AUTO_CREATE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
