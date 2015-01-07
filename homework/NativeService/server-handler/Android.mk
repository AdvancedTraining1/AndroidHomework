LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES:= main.cpp
LOCAL_C_INCLUDES := $(JNI_H_INCLUDE) 
LOCAL_SHARED_LIBRARIES:= libstlport liblog libutils libService libbinder
LOCAL_MODULE:= server-handler
LOCAL_PRELINK_MODULE := false
LOCAL_MODULE_TAGS:=optional

include $(BUILD_EXECUTABLE)
