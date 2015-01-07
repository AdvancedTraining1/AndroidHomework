LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:=Ipcapserver.cpp
LOCAL_C_INCLUDES:= external/stlport/stlport \
			bionic \
			bionic/libstdc++/include \
			external/libpcap
LOCAL_SHARED_LIBRARIES:=\
    liblog libutils libbinder libstlport libpcap
#LOCAL_STATIC_LIBRARIES += libpcap
LOCAL_LDLIBS := -llog
LOCAL_MODULE_TAGS:= optional  
LOCAL_PRELINK_MODULE:=false  
LOCAL_MODULE:=libIpcapServer
include $(BUILD_SHARED_LIBRARY)
