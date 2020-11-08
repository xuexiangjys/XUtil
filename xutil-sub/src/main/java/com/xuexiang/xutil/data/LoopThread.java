package com.xuexiang.xutil.data;

/**
 * 循环线程
 *
 * @author xuexiang
 * @since 2018/11/26 上午10:13
 */
public abstract class LoopThread extends Thread {

    /**
     * 是否正在运行
     */
    private boolean mIsRunning = false;

    /**
     * 执行循环工作
     *
     * @throws Exception
     */
    protected abstract void doWork() throws Exception;

    /**
     * 获取循环执行的间期
     *
     * @return 循环执行的间期
     */
    protected abstract long getLoopInterval();

    @Override
    public synchronized void start() {
        mIsRunning = true;
        super.start();
    }

    @Override
    public void run() {
        super.run();
        while (mIsRunning) {
            try {
                doWork();

                Thread.sleep(getLoopInterval());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 关闭线程
     */
    public void close() {
        mIsRunning = false;
        interrupt();
    }

    /**
     * @return 是否在运行
     */
    public boolean isRunning() {
        return mIsRunning;
    }
}
