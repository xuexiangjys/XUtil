/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.xutil.system;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.xuexiang.xutil.system.ThreadPoolUtils.FixedThread;

/**
 * <pre>
 *     desc   : 线程池管理类
 *     author : xuexiang
 *     time   : 2018/4/27 下午8:38
 * </pre>
 */
public class ThreadPoolManager {

    private static ThreadPoolManager sInstance;

    /**
     * 线程池实例
     */
    private final ThreadPoolUtils mThreadPoolUtils;

    private ThreadPoolManager() {
        int num = Runtime.getRuntime().availableProcessors(); //默认使用固定大小的线程池，大小为可用进程数的两倍
        mThreadPoolUtils = new ThreadPoolUtils(FixedThread, num * 2);
    }

    /**
     * 获取线程管理实例
     * @return
     */
    public static ThreadPoolManager get() {
        if (sInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (sInstance == null) {
                    sInstance = new ThreadPoolManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 更新线程池
     * @param type
     * @param corePoolSize
     */
    public void updateThreadPool(final @ThreadPoolUtils.Type int type, final int corePoolSize) {
        if (!mThreadPoolUtils.isShutDown()) {
            mThreadPoolUtils.shutDownNow();
        }
        mThreadPoolUtils.initThreadPool(type, corePoolSize);
    }

    /**
     * 在未来某个时间执行给定的命令
     * <p>该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。</p>
     *
     * @param command 命令
     */
    public void execute(final Runnable command) {
        mThreadPoolUtils.execute(command);
    }

    /**
     * 在未来某个时间执行给定的命令链表
     * <p>该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。</p>
     *
     * @param commands 命令链表
     */
    public void execute(final List<Runnable> commands) {
        for (Runnable command : commands) {
            mThreadPoolUtils.execute(command);
        }
    }

    /**
     * 待以前提交的任务执行完毕后关闭线程池
     * <p>启动一次顺序关闭，执行以前提交的任务，但不接受新任务。
     * 如果已经关闭，则调用没有作用。</p>
     */
    public void shutDown() {
        mThreadPoolUtils.getExecutorService().shutdown();
    }

    /**
     * 试图停止所有正在执行的活动任务
     * <p>试图停止所有正在执行的活动任务，暂停处理正在等待的任务，并返回等待执行的任务列表。</p>
     * <p>无法保证能够停止正在处理的活动执行任务，但是会尽力尝试。</p>
     *
     * @return 等待执行的任务的列表
     */
    public List<Runnable> shutDownNow() {
        return mThreadPoolUtils.getExecutorService().shutdownNow();
    }

    /**
     * 判断线程池是否已关闭
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isShutDown() {
        return mThreadPoolUtils.getExecutorService().isShutdown();
    }

    /**
     * 关闭线程池后判断所有任务是否都已完成
     * <p>注意，除非首先调用 shutdown 或 shutdownNow，否则 isTerminated 永不为 true。</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isTerminated() {
        return mThreadPoolUtils.isTerminated();
    }


    /**
     * 请求关闭、发生超时或者当前线程中断
     * <p>无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行。</p>
     *
     * @param timeout 最长等待时间
     * @param unit    时间单位
     * @return {@code true}: 请求成功<br>{@code false}: 请求超时
     * @throws InterruptedException 终端异常
     */
    public boolean awaitTermination(final long timeout, final TimeUnit unit)
            throws InterruptedException {
        return mThreadPoolUtils.awaitTermination(timeout, unit);
    }

    /**
     * 提交一个Callable任务用于执行
     * <p>如果想立即阻塞任务的等待，则可以使用{@code result = exec.submit(aCallable).get();}形式的构造。</p>
     *
     * @param task 任务
     * @param <T>  泛型
     * @return 表示任务等待完成的Future, 该Future的{@code get}方法在成功完成时将会返回该任务的结果。
     */
    public <T> Future<T> addTask(final Callable<T> task) {
        return mThreadPoolUtils.addTask(task);
    }

    /**
     * 提交一个Runnable任务用于执行
     *
     * @param task   任务
     * @param result 返回的结果
     * @param <T>    泛型
     * @return 表示任务等待完成的Future, 该Future的{@code get}方法在成功完成时将会返回该任务的结果。
     */
    public <T> Future<T> addTask(final Runnable task, final T result) {
        return mThreadPoolUtils.addTask(task, result);
    }

    /**
     * 提交一个Runnable任务用于执行
     *
     * @param task 任务
     * @return 表示任务等待完成的Future, 该Future的{@code get}方法在成功完成时将会返回null结果。
     */
    public Future<?> addTask(final Runnable task) {
        return mThreadPoolUtils.addTask(task);
    }

    /**
     * 执行给定的任务
     * <p>当所有任务完成时，返回保持任务状态和结果的Future列表。
     * 返回列表的所有元素的{@link Future#isDone}为{@code true}。
     * 注意，可以正常地或通过抛出异常来终止已完成任务。
     * 如果正在进行此操作时修改了给定的 collection，则此方法的结果是不确定的。</p>
     *
     * @param tasks 任务集合
     * @param <T>   泛型
     * @return 表示任务的 Future 列表，列表顺序与给定任务列表的迭代器所生成的顺序相同，每个任务都已完成。
     * @throws InterruptedException 如果等待时发生中断，在这种情况下取消尚未完成的任务。
     */
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        return mThreadPoolUtils.invokeAll(tasks);
    }

    /**
     * 执行给定的任务
     * <p>当所有任务完成或超时期满时(无论哪个首先发生)，返回保持任务状态和结果的Future列表。
     * 返回列表的所有元素的{@link Future#isDone}为{@code true}。
     * 一旦返回后，即取消尚未完成的任务。
     * 注意，可以正常地或通过抛出异常来终止已完成任务。
     * 如果此操作正在进行时修改了给定的 collection，则此方法的结果是不确定的。</p>
     *
     * @param tasks   任务集合
     * @param timeout 最长等待时间
     * @param unit    时间单位
     * @param <T>     泛型
     * @return 表示任务的 Future 列表，列表顺序与给定任务列表的迭代器所生成的顺序相同。
     * 如果操作未超时，则已完成所有任务。如果确实超时了，则某些任务尚未完成。
     * @throws InterruptedException 如果等待时发生中断，在这种情况下取消尚未完成的任务
     */
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks,
                                         final long timeout,
                                         final TimeUnit unit)
            throws InterruptedException {
        return mThreadPoolUtils.invokeAll(tasks, timeout, unit);
    }

    /**
     * 执行给定的任务
     * <p>如果某个任务已成功完成（也就是未抛出异常），则返回其结果。
     * 一旦正常或异常返回后，则取消尚未完成的任务。
     * 如果此操作正在进行时修改了给定的collection，则此方法的结果是不确定的。</p>
     *
     * @param tasks 任务集合
     * @param <T>   泛型
     * @return 某个任务返回的结果
     * @throws InterruptedException 如果等待时发生中断
     * @throws ExecutionException   如果没有任务成功完成
     */
    public <T> T invokeAny(final Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        return mThreadPoolUtils.invokeAny(tasks);
    }

    /**
     * 执行给定的任务
     * <p>如果在给定的超时期满前某个任务已成功完成（也就是未抛出异常），则返回其结果。
     * 一旦正常或异常返回后，则取消尚未完成的任务。
     * 如果此操作正在进行时修改了给定的collection，则此方法的结果是不确定的。</p>
     *
     * @param tasks   任务集合
     * @param timeout 最长等待时间
     * @param unit    时间单位
     * @param <T>     泛型
     * @return 某个任务返回的结果
     * @throws InterruptedException 如果等待时发生中断
     * @throws ExecutionException   如果没有任务成功完成
     * @throws TimeoutException     如果在所有任务成功完成之前给定的超时期满
     */
    public <T> T invokeAny(final Collection<? extends Callable<T>> tasks,
                           final long timeout,
                           final TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return mThreadPoolUtils.invokeAny(tasks, timeout, unit);
    }

    /**
     * 延迟执行Runnable命令
     *
     * @param command 命令
     * @param delay   延迟时间
     * @param unit    单位
     * @return 表示挂起任务完成的ScheduledFuture，并且其{@code get()}方法在完成后将返回{@code null}
     */
    public ScheduledFuture<?> schedule(final Runnable command,
                                       final long delay,
                                       final TimeUnit unit) {
        return mThreadPoolUtils.schedule(command, delay, unit);
    }

    /**
     * 延迟执行Callable命令
     *
     * @param callable 命令
     * @param delay    延迟时间
     * @param unit     时间单位
     * @param <V>      泛型
     * @return 可用于提取结果或取消的ScheduledFuture
     */
    public <V> ScheduledFuture<V> schedule(final Callable<V> callable,
                                           final long delay,
                                           final TimeUnit unit) {
        return mThreadPoolUtils.schedule(callable, delay, unit);
    }

    /**
     * 延迟并循环执行命令
     *
     * @param command      命令
     * @param initialDelay 首次执行的延迟时间
     * @param period       连续执行之间的周期
     * @param unit         时间单位
     * @return 表示挂起任务完成的ScheduledFuture，并且其{@code get()}方法在取消后将抛出异常
     */
    public ScheduledFuture<?> scheduleWithFixedRate(final Runnable command,
                                                    final long initialDelay,
                                                    final long period,
                                                    final TimeUnit unit) {
        return mThreadPoolUtils.getScheduledExecutorService().scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    /**
     * 延迟并以固定休息时间循环执行命令
     *
     * @param command      命令
     * @param initialDelay 首次执行的延迟时间
     * @param delay        每一次执行终止和下一次执行开始之间的延迟
     * @param unit         时间单位
     * @return 表示挂起任务完成的ScheduledFuture，并且其{@code get()}方法在取消后将抛出异常
     */
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable command,
                                                     final long initialDelay,
                                                     final long delay,
                                                     final TimeUnit unit) {
        return mThreadPoolUtils.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }
}
