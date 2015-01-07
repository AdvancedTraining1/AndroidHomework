LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS)
 
LOCAL_MODULE := mytest
LOCAL_SRC_FILES := mytest.c
 
LOCAL_LDLIBS += -llog
 
include $(BUILD_SHARED_LIBRARY)