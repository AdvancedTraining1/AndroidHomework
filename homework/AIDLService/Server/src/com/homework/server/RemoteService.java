package com.homework.server;

import com.aidl.DataItem;
import com.aidl.IMyService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteService extends Service { 

    private DataItem m_item = new DataItem(); 
     

    private final IMyService.Stub mBinder = new IMyService.Stub(){ 


			@Override
			public void saveDataInfo(DataItem item) throws RemoteException {
				// TODO Auto-generated method stub
				m_item = item;
			}

			@Override
			public int JN() throws RemoteException {
				// TODO Auto-generated method stub
				//stringFromJNI();
				return NON(m_item.getNum());
			}

			@Override
			public int JR() throws RemoteException {
				// TODO Auto-generated method stub
				return RE(m_item.getNum());
			}

			@Override
			public int NN() throws RemoteException {
				// 非递归
				return fibona(m_item.getNum());
			}

			@Override
			public int NR() throws RemoteException {
				// 递归
				return fibo(m_item.getNum());
			} 
    };
    
    @Override 
    public IBinder onBind(Intent intent) { 
            return mBinder; 
    } 
    
    public int fibo(int num)
    {
    	if(num == 0)
    	{
    		return 0;
    	}
    	if(num <= 2 && num > 0)
    	{
    		return 1;
    	}else
    	{
    		return fibo(num - 2)+fibo(num - 1);
    	}
    }
    
    public int fibona(int num)
    {
    	if(num < 2)
    	{
    		return 1;
    	}
    	int n1 = 1,n2 = 1,sn = 0; 
    	for(int i = 0;i< num-2; ++i)
    	{
    		sn = n1+n2;
    		n1 = n2;
    		n2 = sn;
    	}
    	
    	return sn;
    }

    public native String stringFromJNI();
    
    public native int NON(int num);
    
    public native int RE(int num);
    //  System.loadLibrary("myjni");这句就是用来加载我们的c动态库的
    static {
        System.loadLibrary("mytest");
    }
}
