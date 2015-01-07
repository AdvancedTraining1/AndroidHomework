#include <binder/IServiceManager.h>  
#include <binder/IPCThreadState.h>  
#include "HelloWorld.h"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "keymatch", __VA_ARGS__)  
  
namespace android{  
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
    void HelloWorld::printResult(){  
        printf("111\n");
        getHelloWorldService();  
        Parcel data, reply;  
        String8 answer;  
        printf("client");  
        data.writeInt32(getpid());
        int num = 0;
        // LOGD("BpExampleService::create remote()->transact()/n");  
        binder->transact(EVENT_TYPE_NONE, data, &reply);
        num = reply.readInt32();  
        answer = reply.readString8();  
        int i;  
        for(i=0; i<num; ++i)  
        {  
         printf(" %02x", answer[i]);  
         if( (i + 1) % 16 == 0 )  
         {  
           printf("\n");  
         }  
        } 

        printf("\n");
        printf("%s\n", answer.string());
        printf("%d\n", num);    
        return;  
    }  

    void HelloWorld::testFunction(String8 ip){  
        getHelloWorldService();  
        Parcel data, reply;  
        String8 answer;  
        int num = 0;
        data.writeInt32(getpid());
	    data.writeString8(ip);
        //LOGD("BpExampleService::create remote()->transact()/n");  
        binder->transact(EVENT_TYPE_ALLPACKET, data, &reply); 
        num = reply.readInt32();  
        answer = reply.readString8();  
        int i;  
        for(i=0; i<num; ++i)  
        {  
         printf(" %02x", answer[i]);  
         if( (i + 1) % 16 == 0 )  
         {  
           printf("\n");  
         }  
        } 

        printf("\n");
        printf("%s\n", answer.string());
        printf("%d\n", num);      
        return;  
    }  
  
    const void HelloWorld::getHelloWorldService(){  
        sp<IServiceManager> sm = defaultServiceManager();  
        binder = sm->getService(String16("IpcapServer"));  
        LOGD("Example::getExampleService %p/n",sm.get());  
        if (binder == 0) {  
            printf("ExampleService not published, waiting...");  
        return;  
        }  
    }  
};
  
using namespace android;  
  
int main(int argc, char** argv)  {  
    printf("233%s\n","111");
    HelloWorld* p = new HelloWorld(); 
    String8 ip = String8("10.0.20.0");
    if(argc > 1){
	ip = String8(argv[1]);
    } 
    printf("222\n");
    //p->printResult();  
    p->testFunction(ip);  
    return 0;  
}  
