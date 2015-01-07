
#define LOG_TAG "TestWslService"

#include <binder/IServiceManager.h>  
#include <binder/IPCThreadState.h> 

#include "JNIHelp.h"
#include "jni.h"
#include <utils/Log.h>
#include <utils/misc.h>

#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>
#include <dirent.h>

#if HAVE_ANDROID_OS
#include <linux/ioctl.h>
#endif
 //在NDK中，printf()没法输出，所以我们需要借助log库来将我们c代码库中需要输出的内容
/*
最近在研究Android 2.3.3源代码的C/C++层，需要对代码进行一些调试，但是奇怪的是，直接添加LOGD("XXXXXXXX");使用logcat却看不到任何输出，换成LOGI、LOGV、LOGW、LOGE也没有效果。于是在网上查找解决方法，经过几次试验，终于找到了，现在贴到下面备忘：
第一步：在对应的mk文件中加入:LOCAL_LDLIBS := -llog
第二步：在要使用LOG的cpp文件中加入：
#include <android/log.h>
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "keymatch", __VA_ARGS__)
第三步：这样就可以使用了：LOGD("我要看到的调试信息^_^");
另外，有文章称此方法在编译动态库的时候可能会出问题，会提示cannot find -llog的错误。意思是找不到liblog.so这个库文件。
因此需要改成 LOCAL_LDLIBS:=  -L$(SYSROOT)/usr/lib -llog 才可以正常编译。但是我这边编译动态库的时候，好像不用这样改也行，没发现编译时提示“cannot find -llog”的错误
*/
//#define LOGI(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)


namespace android 
{
  static char s_string[] = "My god, I did it!";
  sp<IBinder> binder; 
  enum eventitem
  {
    EVENT_TYPE_NONE,
    EVENT_TYPE_ALLPACKET,
    EVENT_TYPE_FROMIP,
    EVENT_TYPE_TOIP,
    EVENT_TYPE_FROMIPANDPORT,
    EVENT_TYPE_FROMPORT,
    EVENT_TYPE_TOPORT,
    EVENT_TYPE_FROMIPTOIP,
    EVENT_TYPE_FROMMAC,
    EVENT_TYPE_TOMAC,
    EVENT_TYPE_FROMMACTOMAC,
    EVENT_TYPE_WITHPROTOCOL
  }; 
   /*
  在c文件中，函数名这样定义：Java_com_jpf_myjni_MyJNI_stringFromJNI，有什么讲究么？这个是JNI的标准，定义需要按照如下格式：
  Java_packagename_classname_methodname,
  例如：Java_com_jpf_myjni_MyJNI_stringFromJNI
   */
  //android_server_BatteryService_update
  jstring android_server_TestWslService_stringFromJNI( JNIEnv* env,jobject thiz )
  {
    LOGW("TestWslService is called!");
    String8 src = String8("TestWslService JNi is called!");
    //jstring result = ctojstring(env,src);
    jstring result;
    result = env->NewStringUTF(src.string());
    return result;
  }

  jint android_server_TestWslService_getIpcapServer( JNIEnv* env,jobject thiz )
  {  
    sp<IServiceManager> sm = defaultServiceManager();  
    binder = sm->getService(String16("IpcapServer"));  
    LOGW("Example::getExampleService %p/n",sm.get());  
    if (binder == 0) {  
      LOGW("ExampleService not published, waiting...");  
      return 0;  
    }  
    return 1;
  } 

  //jstring to char*
  char* jstringTostring(JNIEnv* env, jstring jstr)
  {        
    char* rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("utf-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr= (jbyteArray)env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0)
    {
      rtn = (char*)malloc(alen + 1);
      memcpy(rtn, ba, alen);
      rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
  }

  //char* to jstring
  jstring stoJstring(JNIEnv* env, const char* pat)
  {
    jclass strClass = env->FindClass("Ljava/lang/String;");
    jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    jbyteArray bytes = env->NewByteArray(strlen(pat));
    env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
    jstring encoding = env->NewStringUTF("utf-8");
    return (jstring)env->NewObject(strClass, ctorID, bytes, encoding);
  } 

  jstring android_server_TestWslService_getResult1( JNIEnv* env,jobject thiz )
  {  
    //getHelloWorldService();  
    Parcel data, reply;  
    String8 answer = String8("fuck");  
    data.writeInt32(getpid());
    //data.writeString8(name);
    jint num = 0;
    LOGW("android_server_TestWslService_getResult1/n"); 

    
    binder->transact(0, data, &reply);

    num = reply.readInt32();  
    answer = reply.readString8();  
    int i; 
    char temp[1024]; 
    for(i=0; i<num; ++i)  
    {  
      if(3*i >= 1024)
        break;

      sprintf(temp + 3*i," %02x", (answer.string())[i]);  
      if( (i + 1) % 16 == 0 )  
      {  
       printf("\n");  
      }  


    } 

    temp[3*i-1] = '\0';

    //printf("\n");
    //printf("%s\n", answer.string());
    //printf("%d\n", num); 
    // char* src = "android_server_TestWslService_getResult1 is called!";
    // jstring result1;
    // result1 = env->NewStringUTF(src);
    // return result1; 

    //jstring result =  stoJstring(env,answer.string());
    // char* temp = "fuck";
    //const char* temp = answer.string();
    // jstring result =  env->NewStringUTF(temp);
    return env->NewStringUTF(temp);
    //return result;  
  }

  jstring android_server_TestWslService_getResult2( JNIEnv* env,jobject thiz,jstring ip )
  {  
        //getHelloWorldService();  
    Parcel data, reply;  
    String8 answer;  
    int num = 0;
    data.writeInt32(getpid());
    char* p_ip = jstringTostring(env,ip);
    String8 m_ip = String8(p_ip);
    data.writeString8(m_ip);
    //LOGD("BpExampleService::create remote()->transact()/n");  
    binder->transact(EVENT_TYPE_ALLPACKET, data, &reply); 
    num = reply.readInt32();  
    answer = reply.readString8(); 

    int i; 
    char temp[1024]; 
    for(i=0; i<num; ++i)  
    {  
      if(3*i >= 1024)
        break;

      sprintf(temp + 3*i," %02x", (answer.string())[i]);  
      if( (i + 1) % 16 == 0 )  
      {  
       printf("\n");  
      }  


    } 

    temp[3*i-1] = '\0';

    // printf("\n");
    // printf("%s\n", answer.string());
    // printf("%d\n", num);      
    return env->NewStringUTF(temp);  
  } 


    static JNINativeMethod sMethods[] = 
    {
      {"stringFromJNI", "()Ljava/lang/String;", (void*)android_server_TestWslService_stringFromJNI},
      {"getIpcapServer", "()I", (void*)android_server_TestWslService_getIpcapServer},
      {"getResult1", "()Ljava/lang/String;", (void*)android_server_TestWslService_getResult1},
      {"getResult2", "(Ljava/lang/String;)Ljava/lang/String;", (void*)android_server_TestWslService_getResult2},

    };


    int register_android_server_TestWslService(JNIEnv* env)
    {
      jclass clazz = env->FindClass("com/android/server/TestWslService");

      if (clazz == NULL)
      {
          LOGW("Can't find com/android/server/TestWslService");
          return -1;
      }

      return jniRegisterNativeMethods(env, "com/android/server/TestWslService",
                                      sMethods, NELEM(sMethods));
    }

//jint Java_com_homework_server_RemoteService_NON( JNIEnv* env,jobject thiz,jint num )
}


