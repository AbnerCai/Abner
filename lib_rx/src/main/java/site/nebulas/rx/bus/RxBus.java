package site.nebulas.rx.bus;

import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import site.nebulas.rx.constant.EventTag;

/**
 * @author Nebula
 * @version 1.1.0
 * @date 2018/7/10
 */
public class RxBus {

  private String TAG = RxBus.class.getSimpleName();

  /**
   *
   * */
  private ConcurrentHashMap<String, FlowableProcessor<Object>> mBusMaps;

  private RxBus() {
    mBusMaps = new ConcurrentHashMap<>();
    mBusMaps.put(EventTag.DEFAULT, getFlowable());
  }

  private static class Holder {
    private static final RxBus BUS = new RxBus();
  }

  public static RxBus getInstance() {
    return Holder.BUS;
  }

  /**
   * toSerialized method made bus thread safe
   * */
  private FlowableProcessor<Object> getFlowable() {
    return PublishProcessor.create().toSerialized();
  }

  /**
   * 发送消息，默认tag
   * */
  public boolean post(Object obj) {
    return post(EventTag.DEFAULT, obj);
  }

  /**
   * 根据tag,发送消息.
   * */
  public boolean post(String tag, Object obj) {
    FlowableProcessor<Object> tmpBus = mBusMaps.get(tag);
    if (null != tmpBus) {
      tmpBus.onNext(obj);
      return true;
    }
    return false;
  }

  /**
   * 订阅消息
   * */
  public <T> Flowable<T> toObservable(Class<T> tClass) {
    return toObservable(EventTag.DEFAULT, tClass);
  }

  /**
   * 根据tag，订阅消息
   * */
  public <T> Flowable<T> toObservable(String tag, Class<T> tClass) {
    FlowableProcessor<Object> tmpBus = mBusMaps.get(tag);
    if (null == tmpBus) {
      tmpBus = getFlowable();
      mBusMaps.put(tag, tmpBus);
    }
    return tmpBus.ofType(tClass);
  }

  /**
   * 接收消息,默认tag
   * */
  public Flowable<Object> toObservable() {
    return toObservable(EventTag.DEFAULT);
  }

  /**
   * 根据tag,接收消息.
   * */
  public Flowable<Object> toObservable(String tag) {
    FlowableProcessor<Object> tmpBus = mBusMaps.get(tag);
    if (null == tmpBus) {
      tmpBus = getFlowable();
      mBusMaps.put(tag, tmpBus);
    }
    return tmpBus;
  }

  /**
   *
   * */
  public boolean hasSubscribers() {
    return hasSubscribers(EventTag.DEFAULT);
  }

  /**
   *
   * */
  public boolean hasSubscribers(String tag) {
    FlowableProcessor<Object> tmpBus = mBusMaps.get(tag);
    if (null == tmpBus) {
      return false;
    }
    return tmpBus.hasSubscribers();
  }

  /**
   * 根据tag解除订阅
   * */
  public void unregisterByTag(String tag) {
    FlowableProcessor<Object> tmpBus = mBusMaps.get(tag);
    if (null != tmpBus) {
      tmpBus.onComplete();
      mBusMaps.remove(tag);
    }
  }

  /**
   * 解除所有订阅
   * */
  public void unregisterAll() {
    for (FlowableProcessor<Object> mBus : mBusMaps.values()) {
      mBus.onComplete();
    }
    mBusMaps.clear();
  }
}
