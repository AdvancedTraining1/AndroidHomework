package com.example.testservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.testwsl.TestWslManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView mInfo;
	private Button mBtn1;
	private Button mBtn2;
	private Button mBtn3;
	private Button mBtn4;
	private Button mBtn5;
	private TestWslManager ad = null;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mInfo = (TextView) findViewById(R.id.commandText);
		mBtn1 = (Button) findViewById(R.id.btn1);
		mBtn2 = (Button) findViewById(R.id.btn2);
		mBtn3 = (Button) findViewById(R.id.btn3);
		mBtn4 = (Button) findViewById(R.id.btn4);
		mBtn5 = (Button) findViewById(R.id.btn5);
		
		
		
		//String status = "item ";
 		//mInfo.setText("item ");
		Toast.makeText(getApplicationContext(), "info is is:", 2000).show();
		   

		ad = (TestWslManager)getSystemService("testwsl");  
		ad.setValue(7);  
		
		
		//Toast.makeText(getApplicationContext(), "result is is:"+temp, 2000).show();
		
		mBtn1.setOnClickListener(new OnClickListener() 
		{
    		public void onClick(View v) 
    		{
				String temp = ad.test();
	       		Toast.makeText(getApplicationContext(), "result is is:"+temp, 2000).show();
    		}
        });

        mBtn2.setOnClickListener(new OnClickListener() 
		{
    		public void onClick(View v) 
    		{
				//String temp = ad.getTCPResult2("10.0.2.0");
				String temp = ad.getTCPResult1();
	       		Toast.makeText(getApplicationContext(), "result is is: "+temp, 2000).show();
	       		Toast.makeText(getApplicationContext(), "result is getTCPResult1: ", 2000).show();
    		}
        });

        mBtn3.setOnClickListener(new OnClickListener() 
		{
    		public void onClick(View v) 
    		{
				String temp = ad.getTCPResult2("192.168.0.15");
	       		Toast.makeText(getApplicationContext(), "result is is:"+temp, 2000).show();
    		}
        });

        mBtn4.setOnClickListener(new OnClickListener() 
		{
    		public void onClick(View v) 
    		{
				String temp = ad.getTCPResult2("10.0.20.0");
	       		Toast.makeText(getApplicationContext(), "result is is:"+temp, 2000).show();
    		}
        });

        mBtn5.setOnClickListener(new OnClickListener() 
		{
    		public void onClick(View v) 
    		{
				String temp = ad.getTCPResult2("192.168.0.15");
	       		Toast.makeText(getApplicationContext(), "result is is:"+temp, 2000).show();
    		}
        });


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
