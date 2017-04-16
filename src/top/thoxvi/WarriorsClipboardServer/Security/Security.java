package top.thoxvi.WarriorsClipboardServer.Security;

import top.thoxvi.WarriorsClipboardServer.Logger.ILog;
import top.thoxvi.WarriorsClipboardServer.Logger.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * 由 Thoxvi 在 2017/4/15编写.
 * 联系方式：Thoxvi@Gmail.com
 */
public class Security implements SecurityMode {
    private byte[] password;

    public Security(byte[] password) {
        setPassword(password);
    }

    private ILog logger = LoggerFactory.getInstance().getLogger();

    @Override
    public byte[] encrypt(byte[] data) {
        logger.WTF(this.getClass().getName(), "我加密了！");
        return encrypt(data, password);
//        return data;
    }

    @Override
    public byte[] decrypt(byte[] data) {
        logger.WTF(this.getClass().getName(), "我解密了！");
        return decrypt(data, password);
//        return data;
    }

    @Override
    public void setPassword(byte[] password) {
        logger.WTF(this.getClass().getName(), "我设置了密码！");
        this.password = password;
    }


    private byte[] encrypt(byte[] content, byte[] password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (Exception e) {
            logger.Erro(this.getClass().getName(), "加密失败！");
        }
        return null;
    }

    private byte[] decrypt(byte[] content, byte[] password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            logger.Erro(this.getClass().getName(), "解密失败！");
        }
        return null;
    }
}
