#ifndef  SERVICE_HH
#define SERVICE_HH

#include <utils/RefBase.h>
#include <binder/IInterface.h>
#include <binder/Parcel.h>

#include <android/log.h>
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "keymatch", __VA_ARGS__)

namespace android
{
	class Service : public BBinder
	{
	 public:
		Service();
		virtual ~Service();
		static int instance();//实例
		virtual status_t onTransact(uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags);

	private:
		//多线程安全
		//mutable Mutex mLock;
	};
}


#endif
