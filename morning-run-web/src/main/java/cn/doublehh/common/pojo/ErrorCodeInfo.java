package cn.doublehh.common.pojo;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
 * @author 胡昊
 * Description:返回码
 * Date: 2018/12/1
 * Time: 16:35
 * Create: DoubleH
 */
public enum ErrorCodeInfo implements IErrorCode {
    FAILED(500L, "FAILED"),
    SUCCESS(200L, "SUCCESS"),
    CREATED(201L, "CREATED"),
    Accepted(202L, "Accepted"),
    Unauthorized(401L, "Unauthorized"),
    Forbidden(403L, "Forbidden");

    private final long code;
    private final String msg;

    private ErrorCodeInfo(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorCodeInfo fromCode(long code) {
        ErrorCodeInfo[] ecs = values();
        ErrorCodeInfo[] var3 = ecs;
        int var4 = ecs.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ErrorCodeInfo ec = var3[var5];
            if (ec.getCode() == code) {
                return ec;
            }
        }

        return SUCCESS;
    }

    public long getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String toString() {
        return String.format(" ErrorCode:{code=%s, msg=%s} ", this.code, this.msg);
    }
}
