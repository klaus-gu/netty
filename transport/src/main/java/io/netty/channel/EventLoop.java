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

import io.netty.util.concurrent.OrderedEventExecutor;

/**
 * Will handle all the I/O operations for a  Channel once registered.
 *
 * One  EventLoop instance will usually handle more than one  Channel but this may depend on
 * implementation details and internals.
 *
 * 注册后将处理 Channel 的所有I/O操作。
 * 一个 EventLoop 实例通常将处理多个 Channel，但这可能取决于实现细节和内部。
 * 本质上就是用来处理网络读写事件的Reactor线程。
 * 在Netty中，它不仅仅用来处理网络事件，也可以用来执行定时任务和用户自定义NioTask等任务。
 */
public interface EventLoop extends OrderedEventExecutor, EventLoopGroup {
    @Override
    EventLoopGroup parent();
}
