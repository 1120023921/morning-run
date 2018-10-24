package cn.doublehh.common.pojo;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果集
 *
 * @author 右右君的小跟班
 * @create 2018-03-03 2:33
 **/
@Data
public class ErrorCode<T> implements IErrorCode {

    private static final long serialVersionUID = 1L;

    // OK
    public static final long OK = 200;
    public static final String OK_MSG = "OK";
    //CREATED
    public static final long CREATED = 201;
    public static final String CREATED_MSG = "CREATED";
    //Accepted
    public static final long Accepted = 202;
    public static final String Accepted_MSG = "Accepted";
    //NO CONTENT
    public static final long NO_CONTENT = 204;
    public static final String NO_CONTENT_MSG = "NO CONTENT";
    //INVALID REQUEST
    public static final long INVALID_REQUEST = 400;
    public static final String INVALID_REQUEST_MSG = "INVALID_REQUEST";
    //Unauthorized
    public static final long UNAUTHORIZED = 401;
    public static final String UNAUTHORIZED_MSG = "Unauthorized";
    //Forbidden
    public static final long FORBIDDEN = 403;
    public static final String FORBIDDEN_MSG = "Forbidden";
    //NOT FOUND
    public static final long NOT_FOUND = 404;
    public static final String NOT_FOUND_MSG = "NOT_FOUND";
    //Method Not Allowed
    public static final long METHOD_NOT_ALLOWED = 405;
    public static final String METHOD_NOT_ALLOWED_MSG = "Method Not Allowed";
    //Not Acceptable
    public static final long NOT_ACCEPTABLE = 406;
    public static final String NOT_ACCEPTABLE_MSG = "Accepted";
    //Gone
    public static final long GONE = 410;
    public static final String GONE_MSG = "Gone";
    //Unprocesable entity
    public static final long UNPROCESABLE_ENTITY = 422;
    public static final String UNPROCESABLE_ENTITY_MSG = "Unprocesable entity";
    //INTERNAL SERVER ERROR
    public static final long INTERNAL_SERVER_ERROR = 500;
    public static final String INTERNAL_SERVER_ERROR_MSG = "INTERNAL SERVER ERROR";

    public static final String UPDATE_FAIL = "更新失败";
    public static final String DELETE_FAIL = "删除失败";

    private long code;

    private String msg;

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
