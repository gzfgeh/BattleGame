package com.gzfgeh.battlegame.socket;

import com.gzfgeh.battlegame.utils.LogUtils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.IllegalFormatCodePointException;
import java.util.jar.Attributes;

/**
 * Created by guzhenfu on 15/7/24.
 */
public class MyTextLineDecode implements ProtocolDecoder {
    private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
    private Charset charset;
    private int maxPackLength = 4000;

    public MyTextLineDecode(Charset charset){
        this.charset = charset;
    }

    public int getMaxPackLength(){
        return maxPackLength;
    }

    public void setMaxPackLength(int maxPackLength){
        if (maxPackLength <= 0)
            throw new IllegalArgumentException("maxPackLength: " + maxPackLength);

        this.maxPackLength = maxPackLength;
    }

    private Context getContext(IoSession session){
        Context ctx;
        ctx = (Context) session.getAttribute(CONTEXT);
        if (ctx == null){
            ctx = new Context();
            session.setAttribute(CONTEXT, ctx);
        }
        return ctx;
    }

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

        final int packHeadLength = 2;
        // 先获取上次的处理上下文，其中可能有未处理完的数据
        Context ctx = getContext(session);
        // 先把当前buffer中的数据追加到Context的buffer当中
        ctx.append(in);
        // 把position指向0位置，把limit指向原来的position位置
        IoBuffer buf = ctx.getBuffer();
        buf.flip();
        // 然后按数据包的协议进行读取
        while (buf.remaining() >= packHeadLength) {
            buf.mark();
            // 读取消息头部分
            int length = buf.getShort();

            // 检查读取是否正常，不正常的话清空buffer
            if (length < 0 || length > maxPackLength) {
                LogUtils.i(LogUtils.TAG, "长度[" + length + "] > maxPackLength or <0....");
                buf.clear();
                break;
            }
            // 读取正常的消息，并写入输出流中，以便IoHandler进行处理
            else if (length >= packHeadLength && length - packHeadLength <= buf.remaining()) {
                int oldLimit2 = buf.limit();
                buf.limit(buf.position() + length - packHeadLength);
                String content = buf.getString(ctx.getDecoder());
                buf.limit(oldLimit2);
                out.write(content);
            } else {
                // 如果消息包不完整
                // 将指针重新移动消息头的起始位置
                buf.reset();
                break;
            }
        }
        if (buf.hasRemaining()) {
            // 将数据移到buffer的最前面
            IoBuffer temp = IoBuffer.allocate(maxPackLength).setAutoExpand(true);
            temp.put(buf);
            temp.flip();
            buf.clear();
            buf.put(temp);

        } else {// 如果数据已经处理完毕，进行清空
            buf.clear();
        }

    }

    @Override
    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }

    @Override
    public void dispose(IoSession session) throws Exception {
        Context ctx = (Context) session.getAttribute(CONTEXT);
        if (ctx != null) {
            session.removeAttribute(CONTEXT);
        }
    }

    // 记录上下文，因为数据触发没有规模，很可能只收到数据包的一半
    // 所以，需要上下文拼起来才能完整的处理
    private class Context {
        private final CharsetDecoder decoder;
        private IoBuffer buf;
        private int matchCount = 0;
        private int overflowPosition = 0;

        private Context() {
            decoder = charset.newDecoder();
            buf = IoBuffer.allocate(3000).setAutoExpand(true);
        }

        public CharsetDecoder getDecoder() {
            return decoder;
        }

        public IoBuffer getBuffer() {
            return buf;
        }

        public int getOverflowPosition() {
            return overflowPosition;
        }

        public int getMatchCount() {
            return matchCount;
        }

        public void setMatchCount(int matchCount) {
            this.matchCount = matchCount;
        }

        public void reset() {
            overflowPosition = 0;
            matchCount = 0;
            decoder.reset();
        }

        public void append(IoBuffer in) {
            getBuffer().put(in);

        }

    }
}
