LOCAL_PATH:=$(call my-dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES:=client.cpp
LOCAL_C_INCLUDES := external/stlport/stlport bionic bionic/libstdc++/include
LOCAL_SHARED_LIBRARIES:=libutils libbinder libstlport liblog
LOCAL_LDLIBS := -llog
LOCAL_MODULE_TAGS:=optional
LOCAL_MODULE:=libMyClient
LOCAL_PRELINK_MODULE:=false
include $(BUILD_SHARED_LIBRARY)
