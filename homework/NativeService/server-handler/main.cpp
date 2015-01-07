#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <grp.h>
#include <binder/IPCThreadState.h>
#include <binder/ProcessState.h>
#include <binder/IServiceManager.h>
#include <utils/Log.h>
#include "../service/service.h"
#include <utils/RefBase.h>
#include <binder/IInterface.h>
#include <binder/Parcel.h>
#include <android/log.h>
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "keymatch", __VA_ARGS__)

// namespace android
// {
// 	class Service : public BBinder
// 	{
// 	 public:
// 		Service();
// 		virtual ~Service();
// 		static int instance();//实例
// 		virtual status_t onTransact(uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags);

// 	private:
// 		//多线程安全
// 		//mutable Mutex mLock;
// 		static Service* m_instance;
// 	};
// }

// namespace android
// {
// static pthread_key_t sigbuskey;
// Service* Service::m_instance = NULL;
// Service::Service()
//  {
//         LOGD("Service created");
//          pthread_key_create(&sigbuskey,NULL);
// }


// Service::~Service()
// {
//         pthread_key_delete(sigbuskey);
//         LOGD("Service destroyed");
// }

// int Service::instance()
//  {
// 	if(m_instance == NULL)
// 	{
// 		m_instance = new Service();
// 	}
//         LOGD("  Service instantiate");
// 	printf("  Service instantiate");
//         int r = defaultServiceManager()->addService(String16("service.My"),m_instance);
//         LOGD("Service r = %d/n", r);
// 	printf("Service r = %d/n", r);
//         return r;
// }

// status_t Service::onTransact(uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags)
// {
//         switch(code)
//         {
//             case 0:
//             {
		
// 		pid_t pid = data.readInt32();
// 		String8 word = data.readString8();
//                 reply->writeString8(String8("Hello ") + word + "!\n");
//                 return NO_ERROR;
//             }break;
//             default:
//             return BBinder::onTransact(code, data, reply, flags);
//         }
//         return 0;
// }
// }

using namespace android;
int main(int argc,char *argv[])
{
	printf("server - ain() begin\n");
        sp<ProcessState> proc(ProcessState::self());
        sp<IServiceManager> sm = defaultServiceManager();
        //LOGD("ServiceManager: %p", sm.get());
	printf("ServiceManager: %p\n", sm.get());
        int a = Service::instance();
	printf("Service::instance");
        ProcessState::self()->startThreadPool();
	printf("ProcessState");
        IPCThreadState::self()->joinThreadPool();
        return 0;
}



