#include <sys/types.h>  
#include <unistd.h>  
#include <grp.h>  
#include <binder/IPCThreadState.h>  
#include <binder/ProcessState.h>  
#include <binder/IServiceManager.h>  
#include <utils/Log.h>  
#include <private/android_filesystem_config.h>  
#include "../Ipcapserver/Ipcapserver.h"  
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "keymatch", __VA_ARGS__)  
  
using namespace android;  
  
int main(int argc, char** argv) {  
    sp<ProcessState> proc(ProcessState::self());
    sp<IServiceManager> sm = defaultServiceManager();  
    LOGD("ServiceManager: %p", sm.get());  
    IpcapServer::instantiate();  
    ProcessState::self()->startThreadPool();  
    IPCThreadState::self()->joinThreadPool();  
    return 0;  
}  
