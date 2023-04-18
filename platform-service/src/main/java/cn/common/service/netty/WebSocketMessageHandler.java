package cn.common.service.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@ChannelHandler.Sharable
@ConditionalOnProperty(prefix = "netty",name = "initStatus",havingValue = "true")
public class WebSocketMessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Resource
    private NettyChannelService nettyChannelService;


    /**
     * Netty收到消息
     * @param ctx 上下文
     * @param ctx msg
     * @return
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            //发送过来的消息
            String text = textWebSocketFrame.text();
            // 业务层处理数据
           log.info(">>>>>>>>>>>>>>>>>>>>>>前端发送过来的消息:{}<<<<<<<<<<<<<<<<<<<<",text);
            String pushData = "";
            String data = JSON.toJSONString(pushData);
            log.info(">>>>>>>>>>>>>>>>>>>>>>建立连接后回复的消息:{}<<<<<<<<<<<<<<<<<<<<",data);
            // 响应客户端
            ctx.channel().writeAndFlush(new TextWebSocketFrame(data));
        } else {
            // 不接受文本以外的数据帧类型
            ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
      * Netty连接断开
      * @param ctx 上下文
      * @return
      */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        nettyChannelService.removeChannel(ctx.channel());
        log.info("链接断开：{}", ctx.channel().remoteAddress());
    }

    /**
     * Netty连接创建
     * @param ctx 上下文
     * @return
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        nettyChannelService.addChannel(ctx.channel());
        log.info("链接创建：{}", ctx.channel().remoteAddress());
    }

}
