/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel;

import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Special EventExecutorGroup which allows registering Channels that get
 * processed for later selection during the event loop.
 *
 * 特殊的 EventExecutorGroup 允许注册通道在事件循环中得到处理，以供以后选择的通道。
 */
public interface EventLoopGroup extends EventExecutorGroup {
    /**
     * Return the next {@link EventLoop} to use
     */
    @Override
    EventLoop next();

    /**
     * Register a  Channel with this  EventLoop. The returned  ChannelFuture
     * will get notified once the registration was complete.
     *
     * 在此 EventLoop 中注册一个 Channel。
     * 一旦注册完成，返回的ChannelFuture将会被通知。
     */
    ChannelFuture register(Channel channel);

    /**
     * Register a {@link Channel} with this {@link EventLoop} using a {@link ChannelFuture}.
     * 使用{@link ChannelFuture}在此{@link EventLoop}中注册{@link Channel}。
     *
     * The passed {@link ChannelFuture} will get notified once the registration was complete and also will get returned.
     * 注册完成后，通过的{@link ChannelFuture}将收到通知，并且也将返回。
     */
    ChannelFuture register(ChannelPromise promise);

    /**
     * Register a {@link Channel} with this {@link EventLoop}. The passed {@link ChannelFuture}
     * will get notified once the registration was complete and also will get returned.
     *
     * @deprecated Use {@link #register(ChannelPromise)} instead.
     */
    @Deprecated
    ChannelFuture register(Channel channel, ChannelPromise promise);
}
