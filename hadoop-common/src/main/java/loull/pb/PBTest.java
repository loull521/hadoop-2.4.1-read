package loull.pb;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;

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
	
	private static void testDelimitRW() throws IOException {
		LogonReqMessage.Builder builder = LogonReqMessage.newBuilder();
		builder.setAcctID(10);
		builder.setPasswd("hello");
		LogonReqMessage m1 = builder.build();
		builder.setAcctID(20);
		builder.setPasswd("loull");
		LogonReqMessage m2 = builder.build();
		DataOutputBuffer dob = new DataOutputBuffer();
		m1.writeDelimitedTo(dob);
		m2.writeDelimitedTo(dob);
		byte[] data = dob.getData();
		
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
		LogonReqMessage m3 = LogonReqMessage.parseDelimitedFrom(dis);
		LogonReqMessage m4 = LogonReqMessage.parseDelimitedFrom(dis);
		System.out.println("acctId = " + m3.getAcctID() + "\tpasswd" + m3.getPasswd());
		System.out.println("acctId = " + m4.getAcctID() + "\tpasswd" + m4.getPasswd());
	}

	public static void main(String[] args) {
//		testSimpleMessage();
		try {
			testDelimitRW();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
