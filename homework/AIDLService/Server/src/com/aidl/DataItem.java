package com.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class DataItem implements Parcelable { 

        private int m_num; 
        private int m_type; 

        public DataItem() {} 

        public DataItem(Parcel pl)
        { 
        	m_num = pl.readInt(); 
        	m_type = pl.readInt(); 
        } 

        public int getNum() { 
                return m_num; 
        } 

        public void setNum(int num) { 
        	m_num = num; 
        } 

        public void setType(int type) { 
            m_type = type;
        } 


        @Override 
        public int describeContents() { 
                return 0; 
        } 

        @Override 
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(m_num); 
                dest.writeInt(m_type); 
        } 

        public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() { 

                @Override 
                public DataItem createFromParcel(Parcel source) { 
                        return new DataItem(source); 
                } 

                @Override 
                public DataItem[] newArray(int size) { 
                        return new DataItem[size]; 
                } 

        };

}

