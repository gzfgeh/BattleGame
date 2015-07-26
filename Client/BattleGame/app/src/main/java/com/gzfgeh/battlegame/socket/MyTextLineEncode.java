package com.gzfgeh.battlegame.socket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Created by guzhenfu on 15/7/24.
 */
public class MyTextLineEncode implements ProtocolEncoder {
    private final Charset charset = Charset.forName("UTF-8");

    @Override
    public void encode(IoSession session, Object o, ProtocolEncoderOutput output) throws Exception {
        String s = null;
        if (o instanceof String){
            s = (String) o;
        }

        if (s != null){
//            CharsetEncoder encoder = (CharsetEncoder)session.getAttribute("encoder");
//            if (encoder == null){
//                encoder = Charset.defaultCharset().newEncoder();
//                session.setAttribute("encoder", encoder);
//            }
            CharsetEncoder encoder = charset.newEncoder();
            IoBuffer buffer = IoBuffer.allocate(100);
            buffer.setAutoExpand(true);
            buffer.putString(s, encoder);
            buffer.flip();
            output.write(buffer);
        }

    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
