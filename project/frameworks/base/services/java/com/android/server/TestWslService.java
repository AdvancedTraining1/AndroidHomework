package com.android.server;

import android.content.Context;
import android.os.Handler;
import android.os.ITestWslService;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

public class TestWslService extends ITestWslService.Stub {
  private static final String TAG = "TestWslService";
  private TestWslWorkerThread mWorker;
  private TestWslWorkerHandler mHandler;
  private Context mContext;

  public TestWslService(Context context) {
    super();
    mContext = context;
    mWorker = new TestWslWorkerThread("TestWslServiceWorker");
    mWorker.start();
    Log.i(TAG, "Spawned worker thread");
  }

  public void setValue(int val)
  {
    Log.i(TAG, "setValue " + val);
    Message msg = Message.obtain();
    msg.what = TestWslWorkerHandler.MESSAGE_SET;
    msg.arg1 = val;
    mHandler.sendMessage(msg);
  }

  public int getValue()
  {
    return 222222;
  }

  private native String stringFromJNI();
  private native int getIpcapServer();
  private native String getResult1();
  private native String getResult2(String ip);

  public String test()
  {

    //int a = getIpcapServer();
    //String s = ""+a;
    return stringFromJNI();
  }

  public String getTCPResult1()
  {
    getIpcapServer();
    //int a = 0;
    //a = getResult1();
    String s = getResult1();
    return s;
  }
  public String getTCPResult2(String ip)
  {
    getIpcapServer();
    String s = getResult2(ip);
    return s;
  }

  private class TestWslWorkerThread extends Thread{
    public TestWslWorkerThread(String name) {
      super(name);
    }

    public void run() {
      Looper.prepare();
      mHandler = new TestWslWorkerHandler();
      Looper.loop();
    }
  }

  private class TestWslWorkerHandler extends Handler {
    private static final int MESSAGE_SET = 0;

    @Override
    public void handleMessage(Message msg) {
      try {
        if (msg.what == MESSAGE_SET) {
          Log.i(TAG, "set message received: " + msg.arg1);
        }
      } catch (Exception e) {
        // Log, don't crash!
        Log.e(TAG, "Exception in TestWslWorkerHandler.handleMessage:", e);
      }
    }
  }
}
