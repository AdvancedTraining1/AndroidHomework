#include "client.h"
#include <binder/IServiceManager.h>
#include <binder/IPCThreadState.h>
#include <stdlib.h>
#include <string>

using namespace std;


namespace android
{
        sp<IBinder> binder;
        void MyClient::setN(String8 str)
        {
            getMyService();
            Parcel data, reply;
            //android::String16 temp = "hello world";
           // data.writeString("hello world");
	    data.writeInt32(getpid());
            data.writeString8(String8(str));
            LOGD("client - binder->transact()\n");
            binder->transact(0, data, &reply);
            String8 r = reply.readString8();
	    printf(" %s\n", r.string());
            //return r;
        }

        void MyClient::getMyService()
        {
            sp<IServiceManager> sm = defaultServiceManager();
            binder = sm->getService(String16("service.My"));
            printf("client - Service: %p\n", sm.get());
            if(binder == 0)
            {
                LOGD("MyService not published, waiting...");
                return;
            }
        }
}


