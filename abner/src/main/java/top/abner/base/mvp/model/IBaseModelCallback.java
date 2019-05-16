package top.abner.base.mvp.model;

/**
 * M 层处理结果回调，负责与 P 层交互
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/14 15:23
 */
public interface IBaseModelCallback<T> {

    /**
     * 成功回调.
     * @param data
     * */
    void onSuccess(T data);

    /**
     * 失败回调.
     * @param failure
     * */
    void onFailure(String failure);

    /**
     * 错误回调.
     * @param error
     * */
    void onError(String error);

    /**
     * 完成回调.
     * */
    void onComplete();
}
