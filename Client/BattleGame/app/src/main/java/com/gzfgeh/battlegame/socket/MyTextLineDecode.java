package com.gzfgeh.battlegame.socket;

import com.gzfgeh.battlegame.utils.CmdUtils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;

/**
 * Created by guzhenfu on 15/7/24.
 */
public class MyTextLineDecode implements ProtocolDecoder {

    private Charset charset = Charset.forName("UTF-8");

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

        byte hight = in.get();
        byte low = in.get();
        int length = hight << 8 + low;
        IoBuffer buf = IoBuffer.allocate(length).setAutoExpand(true);

        while (in.hasRemaining()) {
            byte b = in.get();
            switch (b){
                case '\r':
                    buf.flip();
                    String msg = buf.getString(charset.newDecoder());
                    System.out.println("Msg:"+msg);
                    out.write(msg);
                    break;

                case '\n':
                    break;

                default:
                    buf.put(b);
            }

        }

    }

    @Override
    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
