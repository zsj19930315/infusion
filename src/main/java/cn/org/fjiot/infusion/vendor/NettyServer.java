package cn.org.fjiot.infusion.vendor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

@Component
@Order(1)
public class NettyServer implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);
	
	private int port;

	@Override
	public void run(String... args) throws Exception {
		Thread deviceT = new Thread() {
			@Override
			public void run() {
				EventLoopGroup elg = new NioEventLoopGroup();
				try {
					Bootstrap b = new Bootstrap();
					b.group(elg)
						.channel(NioDatagramChannel.class)
						.option(ChannelOption.SO_BROADCAST, true)
						.handler(null);
					Channel c = b.bind(port).channel();
					c.closeFuture().await();
				} catch (Exception e) {
					LOGGER.error("netty初始化失败，" + e.getMessage());
				} finally {
					elg.shutdownGracefully();
				}
			}
		};
		deviceT.start();
	}

}
