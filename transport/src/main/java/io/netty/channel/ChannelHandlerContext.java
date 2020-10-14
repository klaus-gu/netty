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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;
import io.netty.util.concurrent.EventExecutor;

import java.nio.channels.Channels;

/**
 * Enables a  ChannelHandler to interact with its ChannelPipeline and other handlers.
 * 使ChannelHandler与其ChannelPipeline和其他处理程序进行交互。
 * <p>
 * Among other things a handler can notify the next ChannelHandler in the ChannelPipeline as well as modify the ChannelPipeline it belongs to dynamically.
 * 除其他外，处理程序可以通知ChannelPipeline中的下一个ChannelHandler以及动态修改其所属的ChannelPipeline。
 *
 * <h3>Notify</h3>
 * <p>
 * You can notify the closest handler in the same ChannelPipeline by calling one of the various methods provided here.
 * 你可以通过调用此处提供的各种方法之一来通知同一个ChannelPipeline中最接近的处理程序。
 *
 * <p>
 * Please refer to {@link ChannelPipeline} to understand how an event flows.
 * 请参阅{@link ChannelPipeline}以了解事件的流向。
 *
 * <h3>Modifying a pipeline</h3>
 * <p>
 * You can get the {@link ChannelPipeline} your handler belongs to by calling {@link #pipeline()}.
 * 您可以通过调用{@link #pipeline（）}来获取处理程序所属的{@link ChannelPipeline}。
 * <p>
 * A non-trivial application could insert, remove, or replace handlers in the pipeline dynamically at runtime.
 * 一个不平凡的应用程序可以在运行时动态地在管道中插入，删除或替换处理程序。
 *
 * <h3>Retrieving for later use</h3>
 * <p>
 * You can keep the {@link ChannelHandlerContext} for later use, such as triggering an event outside the handler methods, even from a different thread.
 * 您可以保留{@link ChannelHandlerContext}供以后使用，例如在处理程序方法外部触发事件，甚至从其他线程触发事件。
 *
 * <pre>
 * public class MyHandler extends {@link ChannelDuplexHandler} {
 *
 *     <b>private {@link ChannelHandlerContext} ctx;</b>
 *
 *     public void beforeAdd({@link ChannelHandlerContext} ctx) {
 *         <b>this.ctx = ctx;</b>
 *     }
 *
 *     public void login(String username, password) {
 *         ctx.write(new LoginMessage(username, password));
 *     }
 *     ...
 * }
 * </pre>
 *
 * <h3>Storing stateful information</h3>
 * 存储状态信息
 * <p>
 * {@link #attr(AttributeKey)} allow you to store and access stateful information that is related with a {@link ChannelHandler} / {@link Channel} and its context.
 * {@link #attr（AttributeKey）}允许您存储和访问与{@link ChannelHandler} / {@link Channel}及其上下文相关的状态信息。
 * <p>
 * Please refer to {@link ChannelHandler} to learn various recommended ways to manage stateful information.
 * 请参阅{@link ChannelHandler}以了解各种推荐的管理状态信息的方法。
 *
 * <h3>A handler can have more than one {@link ChannelHandlerContext}</h3>
 * 一个处理程序可以具有多个{@link ChannelHandlerContext}
 * <p>
 * Please note that a {@link ChannelHandler} instance can be added to more than one {@link ChannelPipeline}.
 * 请注意，一个{@link ChannelHandler}实例可以添加到多个{@link ChannelPipeline}中。
 *
 * It means a single {@link ChannelHandler} instance can have more than one {@link ChannelHandlerContext}
 * and therefore the single instance can be invoked with different {@link ChannelHandlerContext}s
 * if it is added to one or more {@link ChannelPipeline}s more than once.
 *
 * 这意味着单个{@link ChannelHandler}实例可以具有多个{@link ChannelHandlerContext}，
 * 因此，如果将单个实例添加到一个或多个{@link ChannelPipeline}中，则可以通过不同的{@link ChannelHandlerContext}调用该实例
 * 不止一次。
 *
 *
 * Also note that a {@link ChannelHandler} that is supposed to be added to multiple {@link ChannelPipeline}s should be marked as {@link io.netty.channel.ChannelHandler.Sharable}.
 * 还要注意，应该添加到多个{@link ChannelPipeline}中的{@link ChannelHandler}
 * 应该标记为{@link io.netty.channel.ChannelHandler.Sharable}。
 *
 * <h3>Additional resources worth reading</h3>
 * <p>
 * Please refer to the {@link ChannelHandler}, and
 * {@link ChannelPipeline} to find out more about inbound and outbound operations,
 * what fundamental differences they have, how they flow in a  pipeline,  and how to handle
 * the operation in your application.
 */
public interface ChannelHandlerContext extends AttributeMap, ChannelInboundInvoker, ChannelOutboundInvoker {

    /**
     * Return the {@link Channel} which is bound to the {@link ChannelHandlerContext}.
     */
    Channel channel();

    /**
     * Returns the {@link EventExecutor} which is used to execute an arbitrary task.
     * 返回用于执行任意任务的{@link EventExecutor}。
     */
    EventExecutor executor();

    /**
     * The unique name of the {@link ChannelHandlerContext}.The name was used when then {@link ChannelHandler}
     * was added to the {@link ChannelPipeline}. This name can also be used to access the registered
     * {@link ChannelHandler} from the {@link ChannelPipeline}.
     */
    String name();

    /**
     * The {@link ChannelHandler} that is bound this {@link ChannelHandlerContext}.
     * 与此{@link ChannelHandlerContext}绑定的{@link ChannelHandler}。
     */
    ChannelHandler handler();

    /**
     * Return {@code true} if the {@link ChannelHandler} which belongs to this context was removed from the {@link ChannelPipeline}.
     * 如果属于该上下文的{@link ChannelHandler}已从{@link ChannelPipeline}中删除，则返回{@code true}。
     *
     * Note that this method is only meant to be called from with in the {@link EventLoop}.
     * 请注意，此方法只能在{@link EventLoop}中使用进行调用。
     */
    boolean isRemoved();

    @Override
    ChannelHandlerContext fireChannelRegistered();

    @Override
    ChannelHandlerContext fireChannelUnregistered();

    @Override
    ChannelHandlerContext fireChannelActive();

    @Override
    ChannelHandlerContext fireChannelInactive();

    @Override
    ChannelHandlerContext fireExceptionCaught(Throwable cause);

    @Override
    ChannelHandlerContext fireUserEventTriggered(Object evt);

    @Override
    ChannelHandlerContext fireChannelRead(Object msg);

    @Override
    ChannelHandlerContext fireChannelReadComplete();

    @Override
    ChannelHandlerContext fireChannelWritabilityChanged();

    @Override
    ChannelHandlerContext read();

    @Override
    ChannelHandlerContext flush();

    /**
     * Return the assigned {@link ChannelPipeline}
     */
    ChannelPipeline pipeline();

    /**
     * Return the assigned {@link ByteBufAllocator} which will be used to allocate {@link ByteBuf}s.
     */
    ByteBufAllocator alloc();

    /**
     * @deprecated Use {@link Channel#attr(AttributeKey)}
     */
    @Deprecated
    @Override
    <T> Attribute<T> attr(AttributeKey<T> key);

    /**
     * @deprecated Use {@link Channel#hasAttr(AttributeKey)}
     */
    @Deprecated
    @Override
    <T> boolean hasAttr(AttributeKey<T> key);
}
