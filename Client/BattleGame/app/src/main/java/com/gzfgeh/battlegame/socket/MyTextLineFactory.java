package com.gzfgeh.battlegame.socket;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import java.nio.charset.Charset;

/**
 * Created by guzhenfu on 15/7/24.
 */
public class MyTextLineFactory implements ProtocolCodecFactory{

    private MyTextLineEncode encode;
    private MyTextLineDecode decode;

    public MyTextLineFactory(Charset charset){
        encode = new MyTextLineEncode(charset);
        decode = new MyTextLineDecode(charset);
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encode;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decode;
    }
}
