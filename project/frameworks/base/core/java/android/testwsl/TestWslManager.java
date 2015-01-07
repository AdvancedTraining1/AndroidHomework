package android.testwsl;  
  
import android.os.ITestWslService;  
import android.os.RemoteException;  
import android.util.Log;  
  
public class TestWslManager {  
    ITestWslService service;  
     
    public TestWslManager(ITestWslService service) 
    {  
        this.service = service;  
    }  
     
    public void setValue(int val) 
    {  
        try {  
                service.setValue(val);  
        } catch (RemoteException e) {  
                     Log.d("anromwsl" , "setvalue fail");  
                e.printStackTrace();  
        }  
    }  

    public int getValue() { 

        int result = 0; 
        try {  
                result = service.getValue();  
        } catch (RemoteException e) {  
                     Log.d("anromwsl" , "setvalue fail");  
                e.printStackTrace();  
        }  
        return result;
    }

    public String test()
    {
        String result = "error result";
        try {  
                result = service.test();  
        } catch (RemoteException e) {  
                     Log.d("anromwsl" , "setvalue fail");  
                e.printStackTrace();  
        }  
        return result;
    }

    public String getTCPResult1()
    {
        String result = "error result";
        try {  
                result = service.getTCPResult1();  
        } catch (RemoteException e) {  
                     Log.d("anromwsl" , "setvalue fail");  
                e.printStackTrace();  
        }  
        return result;
    }

    public String getTCPResult2(String ip)
    {
        String result = "error result";
        try {  
                result = service.getTCPResult2(ip);  
        } catch (RemoteException e) {  
                     Log.d("anromwsl" , "setvalue fail");  
                e.printStackTrace();  
        }  
        return result;
    }  
}  
