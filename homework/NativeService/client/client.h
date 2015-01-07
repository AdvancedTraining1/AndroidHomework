#ifndef  CLIENT_HH
#define CLIENT_HH

#include <android/log.h>
#include <utils/Log.h>
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "keymatch", __VA_ARGS__)
#include <binder/IServiceManager.h>
#include <binder/IPCThreadState.h>

namespace android
{
    class MyClient
    {
    public:
             void setN(String8 str);
    private:
            static void getMyService();
    };
}


#endif // CLIENT_HH
