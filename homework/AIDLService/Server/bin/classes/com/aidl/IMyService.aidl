package com.aidl;

import com.aidl.DataItem; 

interface IMyService { 
        void saveDataInfo(in DataItem item); 
        int JN();
        int JR();
        int NN();
        int NR();
}