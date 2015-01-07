#include <string.h>
#include <stdio.h>
//new header
#include <jni.h>
#include <android/log.h>
#define LOG_TAG "MYTEST"
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
#define LOGI(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
 
static char s_string[] = "My god, I did it!";
 /*
在c文件中，函数名这样定义：Java_com_jpf_myjni_MyJNI_stringFromJNI，有什么讲究么？这个是JNI的标准，定义需要按照如下格式：
Java_packagename_classname_methodname,
例如：Java_com_jpf_myjni_MyJNI_stringFromJNI
 */
jstring Java_com_homework_server_RemoteService_stringFromJNI( JNIEnv* env,jobject thiz )
{
       LOGI("MyJNI is called!");
       return (*env)->NewStringUTF(env, s_string);
}

jint Java_com_homework_server_RemoteService_NON( JNIEnv* env,jobject thiz,jint num )
{

		if(num == 0)
		{
			return 0;
		}
       if(num <= 2 && num>=1)
       {
    	   return 1;
       }
       jint n1 = 1,n2 = 1,sn =0;
       jint i = 0;
       for(i = 0; i<(num-2); i++)
       {
    	   sn = n1 + n2;
    	   n1 = n2;
    	   n2 = sn;
       }

       return sn;
}

jint Java_com_homework_server_RemoteService_RE( JNIEnv* env,jobject thiz,jint num )
{

		if(num == 0)
		{
			return 0;
		}
		else if(num <= 2 && num > 0)
		{
			return 1;
		}else
       {
    	   return Java_com_homework_server_RemoteService_RE(env,thiz, num-2 ) +Java_com_homework_server_RemoteService_RE(env,thiz, num-1 );
       }

}
