LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES:= service.cpp
LOCAL_C_INCLUDES:=$(JNI_H_INCLUDE)
LOCAL_SHARED_LIBRARIES:= liblog libutils libbinder
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE:= libService
LOCAL_PRELINK_MODULE:= false
include $(BUILD_SHARED_LIBRARY)
