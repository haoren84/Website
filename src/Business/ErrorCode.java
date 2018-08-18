package Business;

public class ErrorCode 
{
	String msg;  
    int code;

    private ErrorCode(int code,String msg)  
    {  
        this.code=code;  
        this.msg=msg;  
    }  
      
    public String getMsg()  
    {  
        return this.msg;  
    }  
    public int getCode() 
    {  
      return this.code; 
    }
    
    public String GetString()
    {
    	return String.format("{\"result\" : \"%d\", \"errorMsg\":\"%s\"}", code, msg);
    }
    
    private static String GetString(int code, String msg)
    {
    	return String.format("{\"result\" : \"%d\", \"errorMsg\":\"%s\"}", code, msg);
    }
    
    public static String Success(String msg)
    {
    	int code = 1;
    	return String.format("{\"result\" : \"%d\", \"Msg\":\"%s\"}", code, msg);
    }
    
    public static String ReqParamError(String msg)
    {
    	int code = 1000;
    
    	return ErrorCode.GetString(code, msg);
    }
    
    public static String BusinessError(String msg)
    {
    	int code = 2000;
    
    	return ErrorCode.GetString(code, msg);
    }

    public static String DBError(String msg)
    {
    	int code = 3000;
        
    	return ErrorCode.GetString(code, msg);
    }
}