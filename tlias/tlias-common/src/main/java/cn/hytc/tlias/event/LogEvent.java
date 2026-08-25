package cn.hytc.tlias.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 操作日志事件
 */
@Getter
public class LogEvent extends ApplicationEvent {
    private final String module;    // 操作模块
    private final String operation; // 操作类型
    private final String detail;    // 操作详情
    private final long costTime;    // 耗时(毫秒)

    public LogEvent(Object source, String module, String operation, String detail, long costTime) {
        super(source);
        this.module = module;
        this.operation = operation;
        this.detail = detail;
        this.costTime = costTime;
    }
}