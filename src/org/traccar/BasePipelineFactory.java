/*
 * Copyright 2012 - 2013 Anton Tananaev (anton.tananaev@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traccar;

import java.net.InetSocketAddress;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.logging.LoggingHandler;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.traccar.notify.NotificationManager;
import org.traccar.geocode.ReverseGeocoder;
import org.traccar.helper.Log;
import org.traccar.model.DataManager;

/**
 * Base pipeline factory
 */
public abstract class BasePipelineFactory implements ChannelPipelineFactory {

    private TrackerServer server;
    private DataManager dataManager;
    private Boolean loggerEnabled;
    private Integer resetDelay;
    private ReverseGeocoder reverseGeocoder;
    private NotificationManager notificationManager;

    // Handlers
    private IdleStateHandler idleStateHandler;
    private OpenChannelHandler openChannelHandler;
    private StandardLoggingHandler stdLoggingHandler;
    private ReverseGeocoderHandler reverseGeocoderHandler;
    private TrackerEventHandler trackerEventHandler;
    private NotificationHandler notificationHandler;

    /**
     * Open channel handler
     */
    protected class OpenChannelHandler extends SimpleChannelHandler {

        private TrackerServer server;

        public OpenChannelHandler(TrackerServer server) {
            this.server = server;
        }

        @Override
        public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
            server.getChannelGroup().add(e.getChannel());
        }
    }

    /**
     * Logging using global logger
     */
    protected class StandardLoggingHandler extends LoggingHandler {

        @Override
        public void log(ChannelEvent e) {
            if (e instanceof MessageEvent) {
                MessageEvent event = (MessageEvent) e;
                StringBuilder msg = new StringBuilder();

                msg.append("[").append(((InetSocketAddress) e.getChannel().getLocalAddress()).getPort());
                msg.append((e instanceof DownstreamMessageEvent) ? " -> " : " <- ");

                event.getRemoteAddress().hashCode();
                //InetSocketAddress a = (InetSocketAddress) event.getRemoteAddress();
                //InetAddress b = a.getAddress();
                //String s = b.getHostAddress();

                msg.append(((InetSocketAddress) event.getRemoteAddress()).getAddress().getHostAddress()).append("]");

                // Append hex message
                if (event.getMessage() instanceof ChannelBuffer) {
                    msg.append(" - HEX: ");
                    msg.append(ChannelBuffers.hexDump((ChannelBuffer) event.getMessage()));
                }

                Log.debug(msg.toString());
            } else if (e instanceof ExceptionEvent) {
                ExceptionEvent event = (ExceptionEvent) e;
                Log.warning(event.getCause());
            }
        }

    }

    public BasePipelineFactory(ServerManager serverManager, TrackerServer server, String protocol) {
        this.server = server;
        dataManager = serverManager.getDataManager();
        loggerEnabled = serverManager.isLoggerEnabled();
        reverseGeocoder = serverManager.getReverseGeocoder();
        notificationManager = serverManager.getNotificationManager();

        String resetDelayProperty = serverManager.getProperties().getProperty(protocol + ".resetDelay");
        if (resetDelayProperty != null) {
            resetDelay = Integer.valueOf(resetDelayProperty);
        }
        if (resetDelay != null) {
            idleStateHandler = new IdleStateHandler(GlobalTimer.getTimer(), resetDelay, 0, 0);
        }
        openChannelHandler = new OpenChannelHandler(server);
        if (loggerEnabled) {
            stdLoggingHandler = new StandardLoggingHandler();
        }
        if (reverseGeocoder != null) {
            reverseGeocoderHandler = new ReverseGeocoderHandler(reverseGeocoder);
        }
        if (notificationManager != null) {
            notificationHandler = new NotificationHandler(notificationManager);
        }
        trackerEventHandler = new TrackerEventHandler(dataManager);
    }

    protected DataManager getDataManager() {
        return dataManager;
    }

    protected abstract void addSpecificHandlers(ChannelPipeline pipeline);

    @Override
    public ChannelPipeline getPipeline() {
        ChannelPipeline pipeline = Channels.pipeline();
        if (idleStateHandler != null) {
            pipeline.addLast("idleHandler", idleStateHandler);
        }
        pipeline.addLast("openHandler", openChannelHandler);
        if (stdLoggingHandler != null) {
            pipeline.addLast("logger", stdLoggingHandler);
        }
        addSpecificHandlers(pipeline);
        if (reverseGeocoderHandler != null) {
            pipeline.addLast("geocoder", reverseGeocoderHandler);
        }
        if (notificationHandler != null) {
            pipeline.addLast("notification", notificationHandler);
        }
        pipeline.addLast("handler", trackerEventHandler);
        return pipeline;
    }

}
