//#include <stdio.h>
#include "../client/client.h"
#include <android/log.h>
#include <utils/Log.h>

using namespace android;

int main(int argc, char** argv)
{
    MyClient client;
    //String16 temp = String16("hello world");
    client.setN(String8("hello world"));
    //LOGD("setN return: %s\n", ret);
    return 0;
}
