package com.hx.steven.util;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

public class RxBusUtil {
    private static volatile RxBusUtil sRxBus;
    // 主题
    private final FlowableProcessor<Object> mBus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBusUtil() {
        mBus = PublishProcessor.create().toSerialized();
    }

    // 单例RxBus
    public static RxBusUtil getInstance() {
        if (sRxBus == null) {
            synchronized (RxBusUtil.class) {
                if (sRxBus == null) {
                    sRxBus = new RxBusUtil();
                }
            }
        }
        return sRxBus;
    }

    // 提供了一个新的事件
    public void post(Object o) {
        mBus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }
}
