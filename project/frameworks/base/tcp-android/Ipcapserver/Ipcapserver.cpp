#include "Ipcapserver.h"
#include <binder/IServiceManager.h>
#include <binder/IPCThreadState.h>
#include <pcap.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <errno.h>  
#include <netinet/in.h>  
#include <string.h>
#include <arpa/inet.h>
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "keymatch", __VA_ARGS__)

namespace android {

    static struct sigaction oldact;
    static pthread_key_t sigbuskey;

    //save info
    u_char* m_packet;
    int m_num;
    
    //calllback function:get the packet
    void getPacket(u_char * arg, const struct pcap_pkthdr * pkthdr, const u_char * packet)  
	{  
	  int * id = (int *)arg;  
	    
	  printf("id: %d\n", ++(*id));  
	  printf("Packet length: %d\n", pkthdr->len);  
	  printf("Number of bytes: %d\n", pkthdr->caplen);  
	  printf("Recieved time: %s", ctime((const time_t *)&pkthdr->ts.tv_sec));   
	    
	  m_num = 0;
	  m_packet = NULL;
	  m_packet = (u_char*)packet;
	  m_num = pkthdr->len;
	  //print message
	  int i;  
	  for(i=0; i<pkthdr->len; ++i)  
	  {  
	    printf(" %02x", packet[i]);  
	    if( (i + 1) % 16 == 0 )  
	    {  
	      printf("\n");  
	    }  
	  }  
	    
	  printf("\n\n");  
	}  

	void showIpInfo(char* errBuf, char* devStr)
	{
		/* ask pcap for the network address and mask of the device */  
		bpf_u_int32 netp;   /* ip */  
		bpf_u_int32 maskp;  /* subnet mask */  
		int ret;            /* return code */  
		ret = pcap_lookupnet(devStr, &netp, &maskp, errBuf);  
	  
		if(ret == -1)  
		{  
			printf("pcap_lookupnet() error: %s\n", errBuf);  
			exit(1);  
		}  
		/* get the network address in a human readable form */  
		char * net;   /* dot notation of the network address */  
		char * mask;  /* dot notation of the network mask */  
		struct in_addr addr;  
	  
		addr.s_addr = netp;  
		net = inet_ntoa(addr);  
  
		if(!net)  
		{  
			perror("inet_ntoa() ip error: ");  
			exit(1);  
		}  

		printf("ip: %s\n", net);  
	  
		/* do the same as above for the device's mask */  
		addr.s_addr = maskp;  
		mask = inet_ntoa(addr);  
	    
		if(!mask)  
		{  
			perror("inet_ntoa() sub mask error: ");  
			exit(1);  
		}  
	    
		printf("sub mask: %s\n", mask);
	}

	//只要收到一个数据包后就会立即返回
    String8 getDataPacket()
    {
		char errBuf[PCAP_ERRBUF_SIZE], * devStr;  
    
		/* get a device */  
		devStr = pcap_lookupdev(errBuf);  

		if(devStr)  
		{  
			printf("success: device: %s\n", devStr);  
		}  
		else  
		{  
			printf("error: %s\n", errBuf);  
			return String8("device error");  
		}  
    
		/* open a device, wait until a packet arrives */  
		pcap_t * device = pcap_open_live(devStr, 65535, 1, 0, errBuf);  
		if(!device)  
		{  
			printf("error: pcap_open_live(): %s\n", errBuf);  
			return String8("pcap_open_live");   
		}  
  
		/* construct a filter */  
		//struct bpf_program filter;  
		//pcap_compile(device, &filter, "not tcp", 1, 0);  
		//pcap_setfilter(device, &filter);

		/* wait a packet to arrive */  
		struct pcap_pkthdr packet;  


		//printf("1\n");
		const u_char * pktStr = pcap_next(device, &packet); 
		//printf("2\n"); 
		//return String8("did not capture a packet!"); 
	  
		if(!pktStr)  
		{  
			printf("did not capture a packet!\n");  
			return String8("did not capture a packet!");  
		}  
    
		printf("Packet length: %d\n", packet.len);  
		printf("Number of bytes: %d\n", packet.caplen);  
		printf("Recieved time: %s\n", ctime((const time_t *)&packet.ts.tv_sec));   
		String8 word = String8((char*)pktStr);
		m_num = 0;
		m_num = packet.len;
		int i;  
		for(i=0; i<packet.len; ++i)  
		{  
			printf(" %02x", pktStr[i]);  
			if( (i + 1) % 16 == 0 )  
			{  
				printf("\n");  
			}  
		}  
		printf("\n\n");

		showIpInfo(errBuf,devStr);

		pcap_close(device);

		return word;
    }

    //是需要抓的数据包的个数，一旦抓到了cnt个数据包，pcap_loop立即返回。负数的cnt表示pcap_loop永远循环抓包，直到出现错误
    void getAllDataPacket()
    {
    	char errBuf[PCAP_ERRBUF_SIZE], * devStr;  
    
		/* get a device */  
		devStr = pcap_lookupdev(errBuf);  
    
		if(devStr)  
		{  
			printf("success: device: %s\n", devStr);  
		}  
		else  
		{  
			printf("error: %s\n", errBuf);  
			exit(1);  
		}  
    
		/* open a device, wait until a packet arrives */  
		pcap_t * device = pcap_open_live(devStr, 65535, 1, 0, errBuf);  

		if(!device)  
		{  
			printf("error: pcap_open_live(): %s\n", errBuf);  
			exit(1);  
		}  
    
    	/* construct a filter */  
		//struct bpf_program filter;  
		//pcap_compile(device, &filter, "dst host 192.168.80.129", 1, 0);  
		//pcap_setfilter(device, &filter);

		/* wait loop forever */  
		int id = 0;  
		pcap_loop(device, 10, getPacket, (u_char*)&id);  
    	//pcap_dispatch(device,10, getPacket, (u_char*)&id); 
    	showIpInfo(errBuf,devStr);
  		pcap_close(device);  
    }

    String8 getDataPacketFromIp(const char* ip)
    {
    	char* packet;
    	char temp[64]={0};
    	sprintf(temp,"host %s",ip);
    	//return String8(temp);
    	printf("getDataPacketFromIp: %s\n", temp);  


    	char errBuf[PCAP_ERRBUF_SIZE], * devStr;  
		/* get a device */  
		devStr = pcap_lookupdev(errBuf);  
    
		if(devStr)  
		{  
			printf("success: device: %s\n", devStr);  
		}  
		else  
		{  
			printf("error: %s\n", errBuf);  
			return String8("device error");  
		}  
    
		/* open a device, wait until a packet arrives 1000ms*/  
		pcap_t * device = pcap_open_live(devStr, 65535, 1,1000, errBuf);  

		if(!device)  
		{  
			printf("error: pcap_open_live(): %s\n", errBuf);  
			return String8("pcap_open_live error");      
		}  
    
    	/* construct a filter */  
		struct bpf_program filter;  
		pcap_compile(device, &filter, temp, 1, 0);  
		pcap_setfilter(device, &filter);

		/* wait loop forever */  
		int id = 0;  
		pcap_dispatch(device,0, getPacket, (u_char*)&id); 

    	
    	packet = (char*)m_packet; 
    	printf("packet info: %s\n", packet);
    	//printf("2220: %x\n", packet); 
    	//printf("2220: %c\n", packet[0]);
    	String8 word = String8(packet);
    	//printf("2221: %s\n", word.string()); 

    	
  //    	int i;  
		// for(i=0; i<m_num; ++i)  
		// {  
		// 	//(word.string())[i] = packet[i];
		// 	printf(" %02x", (word.string())[i]);  
		// 	if( (i + 1) % 16 == 0 )  
		// 	{  
		// 	  printf("\n");  
		// 	}  
		// }  
	    
	  	//printf("\n\n");  
    	showIpInfo(errBuf,devStr);
  		pcap_close(device);

  		return word;
    }

    int IpcapServer::instantiate(){
        LOGD("IpcapServer instantiate");
        int r = defaultServiceManager()->addService(String16("IpcapServer"), new IpcapServer());
        LOGD("IpcapServer r = %d/n", r);
        //testPcap();
        return r;
    }

    void IpcapServer::showInfo()
    {
        printf("This is a service runing native %s\n", "!!!");  
    }

    IpcapServer::IpcapServer(){ 
        LOGD("IpcapServer created");
        mNextConnId = 1;
        pthread_key_create(&sigbuskey, NULL);
    }

    IpcapServer::~IpcapServer(){
        pthread_key_delete(sigbuskey);
        LOGD("IpcapServer destroyed");
    }

    status_t IpcapServer::onTransact(uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags){
        switch(code){
            case 0: {
                pid_t pid = data.readInt32();
				//String8 ip = data.readString8();
                printf("EVENT_TYPE_NONE! %s\n", "!!!");
                //getDataPacket();
                //printf("EVENT_TYPE_NONE! %s\n", ip.string());  
				//getAllDataPacket();
				
				String8 temp = getDataPacket();
				//printf("!!!!! %s\n", temp.string());
				//reply->writeInt32(80); 
				//reply->writeString8(String8("temp"));

				reply->writeInt32(m_num); 
				reply->writeString8(temp);
				//reply->writeString8(String8("temp"));
                return NO_ERROR;
            }
            break;
	    case 1: {
                pid_t pid = data.readInt32();
				String8 ip = data.readString8();
                
                printf("EVENT_TYPE_ALLPACKET! %s\n", "!!!");
                printf("EVENT_TYPE_ALLPACKET! %s\n", ip.string());  
				//getAllDataPacket();
				
				String8 temp = getDataPacketFromIp(ip.string());
                //reply->writeInt32(98); 
				//reply->writeString8(ip);
				//printf("!!!!! %s\n", temp.string());
				reply->writeInt32(m_num); 
				reply->writeString8(temp);
				//const char* src = temp.string();
				//printf("!!!!! %s\n", m_info); 
				// int i;  
				// for(i=0; i<128; ++i)  
				// {  
				// 	printf(" %02x", src[i]);  
				// 	if( (i + 1) % 16 == 0 )  
				// 	{  
				// 	  printf("\n");  
				// 	}  
				// } 
				
				//getDataPacket();
                return NO_ERROR;
            }
            break;
            default:
                return BBinder::onTransact(code, data, reply, flags);
        }
    }
};

