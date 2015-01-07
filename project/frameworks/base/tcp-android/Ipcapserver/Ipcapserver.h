#ifndef ANDROID_IPCAPSERVER_H  
#define ANDROID_IPCAPSERVER_H  

#include <utils/threads.h>  
#include <utils/RefBase.h>  
#include <binder/IInterface.h>  
#include <binder/BpBinder.h>  
#include <binder/Parcel.h>  
  
namespace android {  
    class IpcapServer : public BBinder {  
        mutable Mutex mLock;  
        int32_t mNextConnId;  
        public:  
            static int instantiate();  
            void showInfo();
            IpcapServer();  
            virtual ~IpcapServer();  
            virtual status_t onTransact(uint32_t, const Parcel&, Parcel*, uint32_t);  
    };  
}; 
#endif  
