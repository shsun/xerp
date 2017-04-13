package base.constant;

/**
 * 
 * Time: 13-12-20 上午10:32
 */
public class BaseConstant {
    public static int LENGTH_PER_PAGE = 20;
    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MSG = "操作成功";
    public static final String KEY_RAND_SESSION = "KEY_RAND_SESSION";
    public static final String KEY_RAND_REQUEST = "KEY_RAND_REQUEST";
    public static final String KEY_USER_SESSION = "KEY_USER_SESSION";
    public static final String KEY_MESSAGE = "KEY_MESSAGE";

    public static final String UnknownHostException = "UnknownHostException";
    public static final String ckfinderFile = "ckupload/images/";

    
    public static final String DATETIMEFORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    public static final String DATEFORMAT_PATTERN = "yyyy-MM-dd";
    
    public static final String NULL_CODE = "1000";
    public static final String FIELD_CODE = "1001";
    
    /** 检索模式-仅检索直属数据 */
    public static final int QUERY_MODE_DIRECTLY = 1;
    /** 检索模式-检索自身及其下级所属数据 */
    public static final int QUERY_MODE_ALL = 2;

    public static final String ERROR_PAGE = "error";

}
