// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MyMessage.proto

package loull.pb;

public final class PhoneMessage {
	private PhoneMessage() {
	}

	public static void registerAllExtensions(
			com.google.protobuf.ExtensionRegistryLite registry) {
	}

	public interface LogonReqMessageOrBuilder extends
			com.google.protobuf.MessageLiteOrBuilder {

		// required int64 acctID = 1;
		/**
		 * <code>required int64 acctID = 1;</code>
		 */
		boolean hasAcctID();

		/**
		 * <code>required int64 acctID = 1;</code>
		 */
		long getAcctID();

		// required string passwd = 2;
		/**
		 * <code>required string passwd = 2;</code>
		 */
		boolean hasPasswd();

		/**
		 * <code>required string passwd = 2;</code>
		 */
		java.lang.String getPasswd();

		/**
		 * <code>required string passwd = 2;</code>
		 */
		com.google.protobuf.ByteString getPasswdBytes();
	}

	/**
	 * Protobuf type {@code LogonReqMessage}
	 */
	public static final class LogonReqMessage extends
			com.google.protobuf.GeneratedMessageLite implements
			LogonReqMessageOrBuilder {
		// Use LogonReqMessage.newBuilder() to construct.
		private LogonReqMessage(
				com.google.protobuf.GeneratedMessageLite.Builder builder) {
			super(builder);

		}

		private LogonReqMessage(boolean noInit) {
		}

		private static final LogonReqMessage defaultInstance;

		public static LogonReqMessage getDefaultInstance() {
			return defaultInstance;
		}

		public LogonReqMessage getDefaultInstanceForType() {
			return defaultInstance;
		}

		private LogonReqMessage(com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			initFields();
			int mutable_bitField0_ = 0;
			try {
				boolean done = false;
				while (!done) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						done = true;
						break;
					default: {
						if (!parseUnknownField(input, extensionRegistry, tag)) {
							done = true;
						}
						break;
					}
					case 8: {
						bitField0_ |= 0x00000001;
						acctID_ = input.readInt64();
						break;
					}
					case 18: {
						bitField0_ |= 0x00000002;
						passwd_ = input.readBytes();
						break;
					}
					}
				}
			} catch (com.google.protobuf.InvalidProtocolBufferException e) {
				throw e.setUnfinishedMessage(this);
			} catch (java.io.IOException e) {
				throw new com.google.protobuf.InvalidProtocolBufferException(
						e.getMessage()).setUnfinishedMessage(this);
			} finally {
				makeExtensionsImmutable();
			}
		}

		public static com.google.protobuf.Parser<LogonReqMessage> PARSER = new com.google.protobuf.AbstractParser<LogonReqMessage>() {
			public LogonReqMessage parsePartialFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
					throws com.google.protobuf.InvalidProtocolBufferException {
				return new LogonReqMessage(input, extensionRegistry);
			}
		};

		@java.lang.Override
		public com.google.protobuf.Parser<LogonReqMessage> getParserForType() {
			return PARSER;
		}

		private int bitField0_;
		// required int64 acctID = 1;
		public static final int ACCTID_FIELD_NUMBER = 1;
		private long acctID_;

		/**
		 * <code>required int64 acctID = 1;</code>
		 */
		public boolean hasAcctID() {
			return ((bitField0_ & 0x00000001) == 0x00000001);
		}

		/**
		 * <code>required int64 acctID = 1;</code>
		 */
		public long getAcctID() {
			return acctID_;
		}

		// required string passwd = 2;
		public static final int PASSWD_FIELD_NUMBER = 2;
		private java.lang.Object passwd_;

		/**
		 * <code>required string passwd = 2;</code>
		 */
		public boolean hasPasswd() {
			return ((bitField0_ & 0x00000002) == 0x00000002);
		}

		/**
		 * <code>required string passwd = 2;</code>
		 */
		public java.lang.String getPasswd() {
			java.lang.Object ref = passwd_;
			if (ref instanceof java.lang.String) {
				return (java.lang.String) ref;
			} else {
				com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
				java.lang.String s = bs.toStringUtf8();
				if (bs.isValidUtf8()) {
					passwd_ = s;
				}
				return s;
			}
		}

		/**
		 * <code>required string passwd = 2;</code>
		 */
		public com.google.protobuf.ByteString getPasswdBytes() {
			java.lang.Object ref = passwd_;
			if (ref instanceof java.lang.String) {
				com.google.protobuf.ByteString b = com.google.protobuf.ByteString
						.copyFromUtf8((java.lang.String) ref);
				passwd_ = b;
				return b;
			} else {
				return (com.google.protobuf.ByteString) ref;
			}
		}

		private void initFields() {
			acctID_ = 0L;
			passwd_ = "";
		}

		private byte memoizedIsInitialized = -1;

		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized != -1)
				return isInitialized == 1;

			if (!hasAcctID()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasPasswd()) {
				memoizedIsInitialized = 0;
				return false;
			}
			memoizedIsInitialized = 1;
			return true;
		}

		public void writeTo(com.google.protobuf.CodedOutputStream output)
				throws java.io.IOException {
			getSerializedSize();
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				output.writeInt64(1, acctID_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				output.writeBytes(2, getPasswdBytes());
			}
		}

		private int memoizedSerializedSize = -1;

		public int getSerializedSize() {
			int size = memoizedSerializedSize;
			if (size != -1)
				return size;

			size = 0;
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				size += com.google.protobuf.CodedOutputStream.computeInt64Size(
						1, acctID_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				size += com.google.protobuf.CodedOutputStream.computeBytesSize(
						2, getPasswdBytes());
			}
			memoizedSerializedSize = size;
			return size;
		}

		private static final long serialVersionUID = 0L;

		@java.lang.Override
		protected java.lang.Object writeReplace()
				throws java.io.ObjectStreamException {
			return super.writeReplace();
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseFrom(
				com.google.protobuf.ByteString data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseFrom(
				com.google.protobuf.ByteString data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseFrom(
				byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseFrom(
				byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseFrom(
				java.io.InputStream input) throws java.io.IOException {
			return PARSER.parseFrom(input);
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseDelimitedFrom(
				java.io.InputStream input) throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input);
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseDelimitedFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input, extensionRegistry);
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseFrom(
				com.google.protobuf.CodedInputStream input)
				throws java.io.IOException {
			return PARSER.parseFrom(input);
		}

		public static loull.pb.PhoneMessage.LogonReqMessage parseFrom(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}

		public static Builder newBuilder() {
			return Builder.create();
		}

		public Builder newBuilderForType() {
			return newBuilder();
		}

		public static Builder newBuilder(
				loull.pb.PhoneMessage.LogonReqMessage prototype) {
			return newBuilder().mergeFrom(prototype);
		}

		public Builder toBuilder() {
			return newBuilder(this);
		}

		/**
		 * Protobuf type {@code LogonReqMessage}
		 */
		public static final class Builder
				extends
				com.google.protobuf.GeneratedMessageLite.Builder<loull.pb.PhoneMessage.LogonReqMessage, Builder>
				implements loull.pb.PhoneMessage.LogonReqMessageOrBuilder {
			// Construct using
			// loull.pb.PhoneMessage.LogonReqMessage.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}

			private void maybeForceBuilderInitialization() {
			}

			private static Builder create() {
				return new Builder();
			}

			public Builder clear() {
				super.clear();
				acctID_ = 0L;
				bitField0_ = (bitField0_ & ~0x00000001);
				passwd_ = "";
				bitField0_ = (bitField0_ & ~0x00000002);
				return this;
			}

			public Builder clone() {
				return create().mergeFrom(buildPartial());
			}

			public loull.pb.PhoneMessage.LogonReqMessage getDefaultInstanceForType() {
				return loull.pb.PhoneMessage.LogonReqMessage
						.getDefaultInstance();
			}

			public loull.pb.PhoneMessage.LogonReqMessage build() {
				loull.pb.PhoneMessage.LogonReqMessage result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}

			public loull.pb.PhoneMessage.LogonReqMessage buildPartial() {
				loull.pb.PhoneMessage.LogonReqMessage result = new loull.pb.PhoneMessage.LogonReqMessage(
						this);
				int from_bitField0_ = bitField0_;
				int to_bitField0_ = 0;
				if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
					to_bitField0_ |= 0x00000001;
				}
				result.acctID_ = acctID_;
				if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
					to_bitField0_ |= 0x00000002;
				}
				result.passwd_ = passwd_;
				result.bitField0_ = to_bitField0_;
				return result;
			}

			public Builder mergeFrom(loull.pb.PhoneMessage.LogonReqMessage other) {
				if (other == loull.pb.PhoneMessage.LogonReqMessage
						.getDefaultInstance())
					return this;
				if (other.hasAcctID()) {
					setAcctID(other.getAcctID());
				}
				if (other.hasPasswd()) {
					bitField0_ |= 0x00000002;
					passwd_ = other.passwd_;

				}
				return this;
			}

			public final boolean isInitialized() {
				if (!hasAcctID()) {

					return false;
				}
				if (!hasPasswd()) {

					return false;
				}
				return true;
			}

			public Builder mergeFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
					throws java.io.IOException {
				loull.pb.PhoneMessage.LogonReqMessage parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input,
							extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (loull.pb.PhoneMessage.LogonReqMessage) e
							.getUnfinishedMessage();
					throw e;
				} finally {
					if (parsedMessage != null) {
						mergeFrom(parsedMessage);
					}
				}
				return this;
			}

			private int bitField0_;

			// required int64 acctID = 1;
			private long acctID_;

			/**
			 * <code>required int64 acctID = 1;</code>
			 */
			public boolean hasAcctID() {
				return ((bitField0_ & 0x00000001) == 0x00000001);
			}

			/**
			 * <code>required int64 acctID = 1;</code>
			 */
			public long getAcctID() {
				return acctID_;
			}

			/**
			 * <code>required int64 acctID = 1;</code>
			 */
			public Builder setAcctID(long value) {
				bitField0_ |= 0x00000001;
				acctID_ = value;

				return this;
			}

			/**
			 * <code>required int64 acctID = 1;</code>
			 */
			public Builder clearAcctID() {
				bitField0_ = (bitField0_ & ~0x00000001);
				acctID_ = 0L;

				return this;
			}

			// required string passwd = 2;
			private java.lang.Object passwd_ = "";

			/**
			 * <code>required string passwd = 2;</code>
			 */
			public boolean hasPasswd() {
				return ((bitField0_ & 0x00000002) == 0x00000002);
			}

			/**
			 * <code>required string passwd = 2;</code>
			 */
			public java.lang.String getPasswd() {
				java.lang.Object ref = passwd_;
				if (!(ref instanceof java.lang.String)) {
					java.lang.String s = ((com.google.protobuf.ByteString) ref)
							.toStringUtf8();
					passwd_ = s;
					return s;
				} else {
					return (java.lang.String) ref;
				}
			}

			/**
			 * <code>required string passwd = 2;</code>
			 */
			public com.google.protobuf.ByteString getPasswdBytes() {
				java.lang.Object ref = passwd_;
				if (ref instanceof String) {
					com.google.protobuf.ByteString b = com.google.protobuf.ByteString
							.copyFromUtf8((java.lang.String) ref);
					passwd_ = b;
					return b;
				} else {
					return (com.google.protobuf.ByteString) ref;
				}
			}

			/**
			 * <code>required string passwd = 2;</code>
			 */
			public Builder setPasswd(java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000002;
				passwd_ = value;

				return this;
			}

			/**
			 * <code>required string passwd = 2;</code>
			 */
			public Builder clearPasswd() {
				bitField0_ = (bitField0_ & ~0x00000002);
				passwd_ = getDefaultInstance().getPasswd();

				return this;
			}

			/**
			 * <code>required string passwd = 2;</code>
			 */
			public Builder setPasswdBytes(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000002;
				passwd_ = value;

				return this;
			}

			// @@protoc_insertion_point(builder_scope:LogonReqMessage)
		}

		static {
			defaultInstance = new LogonReqMessage(true);
			defaultInstance.initFields();
		}

		// @@protoc_insertion_point(class_scope:LogonReqMessage)
	}

	static {
	}

	// @@protoc_insertion_point(outer_class_scope)
}
