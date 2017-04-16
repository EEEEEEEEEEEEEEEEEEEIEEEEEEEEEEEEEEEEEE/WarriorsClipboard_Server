package top.thoxvi.WarriorsClipboardServer.Logger;

/**
 * 由 Thoxvi 在 2017/4/15编写.
 * 联系方式：Thoxvi@Gmail.com
 */
public interface ILog {
    void WTF(String tag, String info);

    void Info(String tag, String info);

    void Debug(String tag, String info);

    void Erro(String tag, String info);
}
