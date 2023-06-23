package com.aiit.netty;


import com.aiit.controller.MessageController;
import com.aiit.netty.protocol.MessageProtocol;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;

// 这块代码写的有些问题，待后续更改
public class TcpHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private static class Type {
        private static final Integer CONNECT = 0;
        private static final Integer MESSAGE = 1;
        private static final Integer LOGOUT = 2;
    }

    private static final HashMap<String, ChannelHandlerContext> map = new HashMap<>();
    private final MessageController messageController;

    public TcpHandler(MessageController messageController) {
        this.messageController = messageController;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        final JSONObject jsonObject = JSONObject.parseObject(new String(msg.getContent(), StandardCharsets.UTF_8));
        if (jsonObject.get("type").toString().equals(Type.CONNECT.toString())) { //注册客户端信息
            addChannelToMap(jsonObject, ctx);
        } else if (jsonObject.get("type").toString().equals(Type.MESSAGE.toString())) { //对聊天信息进行转发
            final JSONObject dataObject = jsonObject.getJSONObject("data");
            dataObject.put("sendId", jsonObject.get("userId"));
            boolean isReceivedOnline = map.containsKey(dataObject.get("receivedId").toString())
                    && !map.get(dataObject.get("receivedId").toString()).isRemoved();
            final String result = messageController.sendMessage(dataObject, isReceivedOnline);
            if (result != null) {
                System.out.println(result);
                final ChannelHandlerContext receivedCtx = map.get(dataObject.get("receivedId").toString());
                receivedCtx.channel().writeAndFlush(new MessageProtocol(result.getBytes(StandardCharsets.UTF_8).length, result.getBytes(StandardCharsets.UTF_8)));
            }
        } else if (jsonObject.get("type").toString().equals(Type.LOGOUT.toString())) {
            System.out.println("退出登录");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        channelRead0(ctx, (MessageProtocol) msg);
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now()));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(new MessageProtocol(16, "success register".getBytes(StandardCharsets.UTF_8)));
        super.channelRegistered(ctx);
    }

    private void addChannelToMap(JSONObject jsonObject, ChannelHandlerContext ctx) {
        final String userId = (String) jsonObject.get("userId");
        map.put(userId, ctx);
    }

    private void clearChannel() {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler removed");
        super.handlerRemoved(ctx);
    }
}
