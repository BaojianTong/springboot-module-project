package com.biyouche.constants;


public class Constants {
    public static final String  EMPTY  = "";
    
    public static final String  CONFIG_PROPERTIES_KEY = "config.properties.file";

    public static final String  DEFAULT_CONFIG_PROPERTIES = "config";
    
    public static final String  UTF8 = "utf-8";
    
	public static String FORMAT_XML = "xml";
	
	public static String FORMAT_JSON = "json";
	


	public static String ENVIRONMENT = "";
	public static String ENVIRONMENT_PRODUCT="product";	
	

	//未知错误
	public static final String UNKNOWN = "unknown";

	public static final String FILE_SEPARATOR = "/";	
	

	public static final String SERVICETYPE_HTTP = "1";

	//编码方式
	public static final String ENCODING_GBK = "GBK";
	public static final String ENCODING_UTF8 = "UTF-8";
	public static final String ENCODING_ISO88591 = "ISO-8859-1";
	public static final String CHARSETNAME_DEFAULT = ENCODING_UTF8;	
	
	//API允许状态
	public static final String SERVICE_STATUS_RUNNING = "1";// 运行中
	public static final String SERVICE_STATUS_STOP = "2";// 服务已暂停

	// 请求格式
	public static final String XML = "xml";
	public static final String JSON = "json";

	//是否需要校验
	public static final String ACCESS_FLG_TRUE="1"; //需要通行证
	public static final String ACCESS_FLG_FALSE="0"; //不需要通行证
	
	public static final String UP_FLAG_TRUE="1";//有上传文件
	public static final String UP_FLAG_FALSE="2";//无上传文件

	//是否运行上传空数据
	public static final String UP_FILE_NULL_FLAG_TRUE="1";//允许上传空数据
	public static final String UP_FILE_NULL_FLAG_FALSE="2";//不允许上传空数据

	// 请求方式
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	
	//字节
	public static final long BYTE_HEX = 1024L;
	public static final long MBYTE = 1048576L;
	public static final long GBYTE = 1073741824L;
	
	public static final int CONTENTLENGTH = 102400; // 100KB
	public static final int CONTENTLENGTH_MAX = 1073741824; // 1G
	

	public static final String RESOURCEPATH = new Object() {
        public String getResourcePath() {
            return this.getClass().getClassLoader().getResource("").getPath();
        }
    }.getResourcePath();
	private static final String DATA = "data";
	private static final String DATAPATH = Constants.RESOURCEPATH + DATA
			+ Constants.FILE_SEPARATOR;
	public static final String DEFAULTRESPATH = DATAPATH
			+ Constants.FILE_SEPARATOR + "res" + Constants.FILE_SEPARATOR;	
	


}
