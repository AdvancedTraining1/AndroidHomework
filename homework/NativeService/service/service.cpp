#include "service.h"
#include <binder/IServiceManager.h>
#include <binder/IPCThreadState.h>

namespace android
{
static pthread_key_t sigbuskey;
Service::Service()
 {
        LOGD("Service created");
         pthread_key_create(&sigbuskey,NULL);
}


Service::~Service()
{
        pthread_key_delete(sigbuskey);
        LOGD("Service destroyed");
}

int Service::instance()
 {
        LOGD("  Service instantiate");
	printf("  Service instantiate");
        int r = defaultServiceManager()->addService(String16("service.My"),new Service());
        LOGD("Service r = %d/n", r);
	printf("Service r = %d/n", r);
        return r;
}

status_t Service::onTransact(uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags)
{
        switch(code)
        {
            case 0:
            {
		String8 temp = data.readString8();
                reply->writeString8(String8("hI!") + temp);
                return NO_ERROR;
            }break;
            default:
            return BBinder::onTransact(code, data, reply, flags);
        }
        return 0;
}
}


