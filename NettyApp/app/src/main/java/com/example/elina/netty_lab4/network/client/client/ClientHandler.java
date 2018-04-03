package com.example.elina.netty_lab4.network.client.client;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Elina on 22.11.2017.
 */

public class ClientHandler extends ChannelInboundHandlerAdapter {

    /** All response messages */
    private List<String> messages = new ArrayList<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        messages.add((String) msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public List<String> getMessages() {
        return messages;
    }
}