package cn.hytc.edupro.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code; // 状态码
    private String msg;   // 提示信息
    private Object data;  // 返回数据

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static Result success(Object data) {
        return new Result(200, "success", data);
    }
    public static Result error(String msg) {
        return new Result(500, msg, null);
    }
}