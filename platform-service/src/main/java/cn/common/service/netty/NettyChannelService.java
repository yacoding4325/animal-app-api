package cn.common.service.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @packageName cn.common.service.netty

 */
@Service("nettyChannelService")
public class NettyChannelService {

    private  static ChannelGroup GlobalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private  static ConcurrentMap<String, ChannelId> ChannelMap = new ConcurrentHashMap();

    public void addChannel(Channel channel){
        GlobalGroup.add(channel);
        ChannelMap.put(channel.id().asShortText(),channel.id());
    }

    public void removeChannel(Channel channel){
        GlobalGroup.remove(channel);
        ChannelMap.remove(channel.id().asShortText());
    }

    public Channel getChannel(String id){
        return GlobalGroup.find(ChannelMap.get(id));
    }

    public void send2All(TextWebSocketFrame tws){
        GlobalGroup.writeAndFlush(tws);
    }

}
