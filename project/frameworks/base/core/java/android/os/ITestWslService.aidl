package android.os;  
interface ITestWslService {  
  /**  
  * {@hide}  
  */  
  void setValue(int val); 
  int getValue();

  String test();
  String getTCPResult1();
  String getTCPResult2(String ip);
}  
