package com.gzfgeh.battlegame.socket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Created by guzhenfu on 15/7/24.
 */
public class MyTextLineEncode extends ProtocolEncoderAdapter {
    private final Charset charset;

    public MyTextLineEncode(Charset charset){
        this.charset = charset;
    }

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput output) throws Exception {
        String value = (String) message;
        IoBuffer buf = IoBuffer.allocate(value.getBytes().length);
        buf.setAutoExpand(true);
        if (value != null)
            buf.put(value.trim().getBytes());
        buf.flip();
        output.write(buf);
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
