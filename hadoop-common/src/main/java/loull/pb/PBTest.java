package loull.pb;

import com.google.protobuf.InvalidProtocolBufferException;

import loull.pb.PhoneMessage.LogonReqMessage;

public class PBTest {
	
	private static void testSimpleMessage() {
		System.out.println("========================");
		LogonReqMessage.Builder logonBuilder = LogonReqMessage.newBuilder();
		logonBuilder.setAcctID(20);
		logonBuilder.setPasswd("hello loull");
		LogonReqMessage logonReq = logonBuilder.build();
		int length = logonReq.getSerializedSize();
		System.out.println("the result length is :" + length);
		byte[] buf = logonReq.toByteArray();
		
		try {
			LogonReqMessage logonReq2 = LogonReqMessage.parseFrom(buf);
			System.out.println("acctID = " + logonReq2.getAcctID() + "\tpasswd = " + logonReq2.getPasswd());
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		testSimpleMessage();
	}
}
