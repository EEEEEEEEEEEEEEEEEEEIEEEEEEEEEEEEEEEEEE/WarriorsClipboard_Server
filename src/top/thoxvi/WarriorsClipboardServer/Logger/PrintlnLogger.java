package top.thoxvi.WarriorsClipboardServer.Logger;

/**
 * 由 Thoxvi 在 2017/4/15编写.
 * 联系方式：Thoxvi@Gmail.com
 */
public class PrintlnLogger implements ILog {
    @Override
    public void WTF(String tag, String info) {
        System.out.println(tag + ":" + info);
    }

    @Override
    public void Info(String tag, String info) {
        System.out.println(tag + ":" + info);
    }

    @Override
    public void Debug(String tag, String info) {
        System.out.println(tag + ":" + info);
    }

    @Override
    public void Erro(String tag, String info) {
        System.err.println(tag + ":" + info);
    }
}
