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
package io.netty.util.concurrent;

/**
 * The EventExecutor is a special EventExecutorGroup which comes
 * with some handy methods to see if a  Thread is executed in a event loop.
 * Besides this, it also extends the EventExecutorGroup to allow for a generic
 * way to access methods.
 *
 * EventExecutor是一个特殊的EventExecutorGroup，它带有一些方便的方法来查看线程是否在事件循环中执行。
 * 除此之外，它还扩展了EventExecutorGroup以允许用一种通用方法来访问方法。
 *
 * 暂时理解为一个拥有一些特定方法的事件调度线程池
 */
public interface EventExecutor extends EventExecutorGroup {

    /**
     * Returns a reference to itself.
     */
    @Override
    EventExecutor next();

    /**
     * Return the {@link EventExecutorGroup} which is the parent of this {@link EventExecutor},
     * 返回{@link EventExecutorGroup}，它是此{@link EventExecutor}的父级，
     */
    EventExecutorGroup parent();

    /**
     * Calls {@link #inEventLoop(Thread)} with {@link Thread#currentThread()} as argument
     */
    boolean inEventLoop();

    /**
     * Return {@code true} if the given {@link Thread} is executed in the event loop,
     * {@code false} otherwise.
     */
    boolean inEventLoop(Thread thread);

    /**
     * Return a new {@link Promise}.
     */
    <V> Promise<V> newPromise();

    /**
     * Create a new {@link ProgressivePromise}.
     */
    <V> ProgressivePromise<V> newProgressivePromise();

    /**
     * Create a new Future which is marked as succeeded already. So Future#isSuccess()
     * will return  true. All FutureListener added to it will be notified directly. Also
     * every call of blocking methods will just return without blocking.
     * 创建一个已标记为成功的新Future。
     * 因此，Future＃isSuccess（）将返回true。
     * 所有添加到它里面的FutureListener将会被直接通知。
     * 同样，每次调用阻塞方法都将返回而不阻塞。
     */
    <V> Future<V> newSucceededFuture(V result);

    /**
     * Create a new {@link Future} which is marked as failed already. So {@link Future#isSuccess()}
     * will return {@code false}. All {@link FutureListener} added to it will be notified directly. Also
     * every call of blocking methods will just return without blocking.
     */
    <V> Future<V> newFailedFuture(Throwable cause);
}
