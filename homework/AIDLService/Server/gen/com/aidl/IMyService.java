/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/hh/workspace/Server/src/com/aidl/IMyService.aidl
 */
package com.aidl;
public interface IMyService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.aidl.IMyService
{
private static final java.lang.String DESCRIPTOR = "com.aidl.IMyService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.aidl.IMyService interface,
 * generating a proxy if needed.
 */
public static com.aidl.IMyService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.aidl.IMyService))) {
return ((com.aidl.IMyService)iin);
}
return new com.aidl.IMyService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_saveDataInfo:
{
data.enforceInterface(DESCRIPTOR);
com.aidl.DataItem _arg0;
if ((0!=data.readInt())) {
_arg0 = com.aidl.DataItem.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.saveDataInfo(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_JN:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.JN();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_JR:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.JR();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_NN:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.NN();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_NR:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.NR();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.aidl.IMyService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void saveDataInfo(com.aidl.DataItem item) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((item!=null)) {
_data.writeInt(1);
item.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_saveDataInfo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int JN() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_JN, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int JR() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_JR, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int NN() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_NN, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int NR() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_NR, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_saveDataInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_JN = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_JR = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_NN = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_NR = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void saveDataInfo(com.aidl.DataItem item) throws android.os.RemoteException;
public int JN() throws android.os.RemoteException;
public int JR() throws android.os.RemoteException;
public int NN() throws android.os.RemoteException;
public int NR() throws android.os.RemoteException;
}
