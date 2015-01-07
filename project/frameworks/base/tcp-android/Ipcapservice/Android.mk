LOCAL_PATH:=$(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:=\
    Ipcapservice.cpp
LOCAL_C_INCLUDES:=$(JNI_H_INCLUDE)  
LOCAL_SHARED_LIBRARIES:=\
    liblog libutils libbinder libIpcapServer  
LOCAL_MODULE_TAGS := optional  
LOCAL_PRELINK_MODULE := false  
LOCAL_MODULE := Ipcapservice  
  
include $(BUILD_EXECUTABLE)  
