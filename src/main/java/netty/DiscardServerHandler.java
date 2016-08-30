package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        ctx.write(msg);
        ctx.write("asdfasdf");
        ctx.flush(); // (2)
//        ByteBuf in = (ByteBuf) msg;
//        String s = "";
//        try {
//            while (in.isReadable()) { // (1)
//                char c = (char) in.readByte();
//                s += c;
//            }
//            System.out.println(s);
//            System.out.flush();
//        } finally {
//            ReferenceCountUtil.release(msg); // (2)
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}

