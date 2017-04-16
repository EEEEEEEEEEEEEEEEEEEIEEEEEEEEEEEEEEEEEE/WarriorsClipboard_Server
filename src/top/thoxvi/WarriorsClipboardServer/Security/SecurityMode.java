package top.thoxvi.WarriorsClipboardServer.Security;

/**
 * 由 Thoxvi 在 2017/4/15编写.
 * 联系方式：Thoxvi@Gmail.com
 */
public interface SecurityMode {
    byte[] encrypt(byte[] data);

    byte[] decrypt(byte[] data);

    void setPassword(byte[] password);
}
