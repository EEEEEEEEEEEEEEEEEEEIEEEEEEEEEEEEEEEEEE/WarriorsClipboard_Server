package top.thoxvi.WarriorsClipboardServer.Logger;

/**
 * 由 Thoxvi 在 2017/4/15编写.
 * 联系方式：Thoxvi@Gmail.com
 */
public class LoggerFactory {
    private static final LoggerFactory logger = new LoggerFactory();

    private LoggerFactory() {
    }

    public static LoggerFactory getInstance() {
        return logger;
    }

    private <T extends ILog> T getLogger(Class<T> c) {
        T logger;
        try {
            logger = (T) Class.forName(c.getName()).newInstance();
            return logger;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ILog getLogger() {
        return getLogger(PrintlnLogger.class);
    }
}
