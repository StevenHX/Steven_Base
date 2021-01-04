package com.hx.steven.wx_ali_Pay.ali;

import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.app.BaseApplication;
import com.hx.steven.web.WebManager;
import com.hx.steven.wx_ali_Pay.IPay;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class AliPay implements IPay {
    private String data;

    @Override
    public void toPay() {
        // 被观察者
        Observable<String> observable = Observable.create(emitter -> {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(BaseActivity.getThis());
            // 调用支付接口，获取支付结果
            String result = alipay.pay(data, true);
            emitter.onNext(result);
            emitter.onComplete();
        });
        // 观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String string) {
                PayResult payResult = new PayResult(string);
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    if (WebManager.getInstance().getWebStrategyInterface() != null) {
                        WebManager.getInstance().getWebStrategyInterface().callJs("orderPayWithAppCallback", "1");
                    } else {
                        Toast.makeText(BaseApplication.getAppContext(), "支付成功,", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，
                    // 最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        Toast.makeText(BaseApplication.getAppContext(), "支付结果确认中,", Toast.LENGTH_SHORT).show();
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        if (WebManager.getInstance().getWebStrategyInterface() != null) {
                            WebManager.getInstance().getWebStrategyInterface().callJs("orderPayWithAppCallback", "0");
                        } else {
                            Toast.makeText(BaseApplication.getAppContext(), "支付失败,", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    @Override
    public void setDataMap(Map<String, Object> maps) {
        data = maps.get("data").toString();
    }
}
