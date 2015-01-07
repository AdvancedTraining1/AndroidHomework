LOCAL_PATH:=$(call my-dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES:=main.cpp
LOCAL_C_INCLUDES := $(JNI_H_INCLUDE) 
LOCAL_SHARED_LIBRARIES:=libMyClient libstlport liblog libutils libbinder
LOCAL_MODULE_TAGS:=optional
LOCAL_MODULE:=client-handler
LOCAL_PRELINK_MODULE := false
include $(BUILD_EXECUTABLE)
