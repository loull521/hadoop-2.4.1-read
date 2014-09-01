package loull.hadoop.pb.rpc2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

import loull.hadoop.pb.rpc2.ProtobufRpcProtos.RequestHeaderProto;

import org.apache.hadoop.io.DataOutputOutputStream;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;

public abstract class ProtoUtil {

	interface Writable {
		/**
		 * Serialize the fields of this object to <code>out</code>.
		 * 
		 * @param out
		 *            <code>DataOuput</code> to serialize this object into.
		 * @throws IOException
		 */
		void write(DataOutput out) throws IOException;

		/**
		 * Deserialize the fields of this object from <code>in</code>.
		 * 
		 * <p>
		 * For efficiency, implementations should attempt to re-use storage in
		 * the existing object where possible.
		 * </p>
		 * 
		 * @param in
		 *            <code>DataInput</code> to deseriablize this object from.
		 * @throws IOException
		 */
		void readFields(DataInput in) throws IOException;
	}

	interface RpcWrapper extends Writable {
		int getLength();
	}

	static abstract class RpcMessageWithHeader<T extends GeneratedMessage>
			implements RpcWrapper {
		T requestHeader;
		Message theRequest; // for clientSide, the request is here
		byte[] theRequestRead; // for server side, the request is here

		public RpcMessageWithHeader() {
		}

		public RpcMessageWithHeader(T requestHeader, Message theRequest) {
			this.requestHeader = requestHeader;
			this.theRequest = theRequest;
		}

		@Override
		public void write(DataOutput out) throws IOException {
			OutputStream os = DataOutputOutputStream.constructOutputStream(out);

			((Message) requestHeader).writeDelimitedTo(os);
			theRequest.writeDelimitedTo(os);
		}

		@Override
		public void readFields(DataInput in) throws IOException {
			requestHeader = parseHeaderFrom(readVarintBytes(in));
			theRequestRead = readMessageRequest(in);
		}

		byte[] readMessageRequest(DataInput in) throws IOException {
			return readVarintBytes(in);
		}

		abstract T parseHeaderFrom(byte[] bytes) throws IOException;

		private static byte[] readVarintBytes(DataInput in) throws IOException {
			final int length = ProtoUtil.readRawVarint32(in);
			final byte[] bytes = new byte[length];
			in.readFully(bytes);
			return bytes;
		}

		public T getMessageHeader() {
			return requestHeader;
		}

		public byte[] getMessageBytes() {
			return theRequestRead;
		}

		@Override
		public int getLength() {
			int headerLen = requestHeader.getSerializedSize();
			int reqLen;
			if (theRequest != null) {
				reqLen = theRequest.getSerializedSize();
			} else if (theRequestRead != null) {
				reqLen = theRequestRead.length;
			} else {
				throw new IllegalArgumentException(
						"getLength on uninitialized RpcWrapper");
			}
			return CodedOutputStream.computeRawVarint32Size(headerLen)
					+ headerLen
					+ CodedOutputStream.computeRawVarint32Size(reqLen) + reqLen;
		}
	}//RpcMessageWithHeader
	
	static class RpcRequestWrapper extends RpcMessageWithHeader<RequestHeaderProto> {

		public RpcRequestWrapper() {
		}
		
		public RpcRequestWrapper (RequestHeaderProto requestHeader, Message theRequest) {
			super(requestHeader, theRequest);
		}
		
		@Override
		RequestHeaderProto parseHeaderFrom(byte[] bytes) throws IOException {
			return RequestHeaderProto.parseFrom(bytes);
		}
		
	}

	/**
	 * Read a variable length integer in the same format that ProtoBufs encodes.
	 * 
	 * @param in
	 *            the input stream to read from
	 * @return the integer
	 * @throws IOException
	 *             if it is malformed or EOF.
	 */
	public static int readRawVarint32(DataInput in) throws IOException {
		byte tmp = in.readByte();
		if (tmp >= 0) {
			return tmp;
		}
		int result = tmp & 0x7f;
		if ((tmp = in.readByte()) >= 0) {
			result |= tmp << 7;
		} else {
			result |= (tmp & 0x7f) << 7;
			if ((tmp = in.readByte()) >= 0) {
				result |= tmp << 14;
			} else {
				result |= (tmp & 0x7f) << 14;
				if ((tmp = in.readByte()) >= 0) {
					result |= tmp << 21;
				} else {
					result |= (tmp & 0x7f) << 21;
					result |= (tmp = in.readByte()) << 28;
					if (tmp < 0) {
						// Discard upper 32 bits.
						for (int i = 0; i < 5; i++) {
							if (in.readByte() >= 0) {
								return result;
							}
						}
						throw new IOException("Malformed varint");
					}
				}
			}
		}
		return result;
	}

}
