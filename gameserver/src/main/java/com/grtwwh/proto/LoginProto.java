// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Login.proto

package com.grtwwh.proto;

public final class LoginProto {
  private LoginProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface AccountLoginReqOrBuilder extends
      // @@protoc_insertion_point(interface_extends:AccountLoginReq)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * 登录账号
     * </pre>
     *
     * <code>string account = 1;</code>
     * @return The account.
     */
    java.lang.String getAccount();
    /**
     * <pre>
     * 登录账号
     * </pre>
     *
     * <code>string account = 1;</code>
     * @return The bytes for account.
     */
    com.google.protobuf.ByteString
        getAccountBytes();
  }
  /**
   * Protobuf type {@code AccountLoginReq}
   */
  public static final class AccountLoginReq extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:AccountLoginReq)
      AccountLoginReqOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use AccountLoginReq.newBuilder() to construct.
    private AccountLoginReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private AccountLoginReq() {
      account_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new AccountLoginReq();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private AccountLoginReq(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              account_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.grtwwh.proto.LoginProto.internal_static_AccountLoginReq_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.grtwwh.proto.LoginProto.internal_static_AccountLoginReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.grtwwh.proto.LoginProto.AccountLoginReq.class, com.grtwwh.proto.LoginProto.AccountLoginReq.Builder.class);
    }

    public static final int ACCOUNT_FIELD_NUMBER = 1;
    private volatile java.lang.Object account_;
    /**
     * <pre>
     * 登录账号
     * </pre>
     *
     * <code>string account = 1;</code>
     * @return The account.
     */
    @java.lang.Override
    public java.lang.String getAccount() {
      java.lang.Object ref = account_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        account_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * 登录账号
     * </pre>
     *
     * <code>string account = 1;</code>
     * @return The bytes for account.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getAccountBytes() {
      java.lang.Object ref = account_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        account_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getAccountBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, account_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getAccountBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, account_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.grtwwh.proto.LoginProto.AccountLoginReq)) {
        return super.equals(obj);
      }
      com.grtwwh.proto.LoginProto.AccountLoginReq other = (com.grtwwh.proto.LoginProto.AccountLoginReq) obj;

      if (!getAccount()
          .equals(other.getAccount())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + ACCOUNT_FIELD_NUMBER;
      hash = (53 * hash) + getAccount().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.grtwwh.proto.LoginProto.AccountLoginReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.grtwwh.proto.LoginProto.AccountLoginReq prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code AccountLoginReq}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:AccountLoginReq)
        com.grtwwh.proto.LoginProto.AccountLoginReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.grtwwh.proto.LoginProto.internal_static_AccountLoginReq_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.grtwwh.proto.LoginProto.internal_static_AccountLoginReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.grtwwh.proto.LoginProto.AccountLoginReq.class, com.grtwwh.proto.LoginProto.AccountLoginReq.Builder.class);
      }

      // Construct using com.grtwwh.proto.LoginProto.AccountLoginReq.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        account_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.grtwwh.proto.LoginProto.internal_static_AccountLoginReq_descriptor;
      }

      @java.lang.Override
      public com.grtwwh.proto.LoginProto.AccountLoginReq getDefaultInstanceForType() {
        return com.grtwwh.proto.LoginProto.AccountLoginReq.getDefaultInstance();
      }

      @java.lang.Override
      public com.grtwwh.proto.LoginProto.AccountLoginReq build() {
        com.grtwwh.proto.LoginProto.AccountLoginReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.grtwwh.proto.LoginProto.AccountLoginReq buildPartial() {
        com.grtwwh.proto.LoginProto.AccountLoginReq result = new com.grtwwh.proto.LoginProto.AccountLoginReq(this);
        result.account_ = account_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.grtwwh.proto.LoginProto.AccountLoginReq) {
          return mergeFrom((com.grtwwh.proto.LoginProto.AccountLoginReq)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.grtwwh.proto.LoginProto.AccountLoginReq other) {
        if (other == com.grtwwh.proto.LoginProto.AccountLoginReq.getDefaultInstance()) return this;
        if (!other.getAccount().isEmpty()) {
          account_ = other.account_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.grtwwh.proto.LoginProto.AccountLoginReq parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.grtwwh.proto.LoginProto.AccountLoginReq) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object account_ = "";
      /**
       * <pre>
       * 登录账号
       * </pre>
       *
       * <code>string account = 1;</code>
       * @return The account.
       */
      public java.lang.String getAccount() {
        java.lang.Object ref = account_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          account_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * 登录账号
       * </pre>
       *
       * <code>string account = 1;</code>
       * @return The bytes for account.
       */
      public com.google.protobuf.ByteString
          getAccountBytes() {
        java.lang.Object ref = account_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          account_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * 登录账号
       * </pre>
       *
       * <code>string account = 1;</code>
       * @param value The account to set.
       * @return This builder for chaining.
       */
      public Builder setAccount(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        account_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 登录账号
       * </pre>
       *
       * <code>string account = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearAccount() {
        
        account_ = getDefaultInstance().getAccount();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 登录账号
       * </pre>
       *
       * <code>string account = 1;</code>
       * @param value The bytes for account to set.
       * @return This builder for chaining.
       */
      public Builder setAccountBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        account_ = value;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:AccountLoginReq)
    }

    // @@protoc_insertion_point(class_scope:AccountLoginReq)
    private static final com.grtwwh.proto.LoginProto.AccountLoginReq DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.grtwwh.proto.LoginProto.AccountLoginReq();
    }

    public static com.grtwwh.proto.LoginProto.AccountLoginReq getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<AccountLoginReq>
        PARSER = new com.google.protobuf.AbstractParser<AccountLoginReq>() {
      @java.lang.Override
      public AccountLoginReq parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AccountLoginReq(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<AccountLoginReq> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<AccountLoginReq> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.grtwwh.proto.LoginProto.AccountLoginReq getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AccountLoginReq_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AccountLoginReq_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013Login.proto\"\"\n\017AccountLoginReq\022\017\n\007acco" +
      "unt\030\001 \001(\tB \n\020com.grtwwh.protoB\nLoginProt" +
      "oH\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_AccountLoginReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AccountLoginReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AccountLoginReq_descriptor,
        new java.lang.String[] { "Account", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
