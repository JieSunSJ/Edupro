package cn.hytc.tlias.listener;

import cn.hytc.tlias.entity.EmpLog;
import cn.hytc.tlias.event.LogEvent;
import cn.hytc.tlias.service.EmpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 操作日志监听器 - 异步记录操作日志到数据库
 */
@Component
public class LogListener {

    @Autowired
    private EmpLogService empLogService;

    /**
     * 监听操作日志事件，异步写入数据库
     * 执行流程：
     * 1. 当有人发布 LogEvent 事件时，Spring 自动调用此方法
     * 2. @Async 让这个方法在独立线程中运行，不阻塞主业务
     * 3. 将事件中的信息拼接成一条日志字符串
     * 4. 保存到 emp_log 表中
     */
    @Async               // 异步执行，不阻塞主线程
    @EventListener       // 监听 LogEvent 事件（Spring 事件机制）
    public void handleLogEvent(LogEvent event) {
        // 把事件信息拼成一条可读的日志：格式如 "[员工管理] 新增员工 | 方法：save，参数：...，结果：成功 | 耗时：120ms"
        String info = String.format("[%s] %s | %s | 耗时：%dms",
                event.getModule(),    // 模块名，如"员工管理"
                event.getOperation(), // 操作名，如"新增员工"
                event.getDetail(),    // 详细描述，如方法名、参数、结果
                event.getCostTime()); // 执行耗时，单位毫秒

        // 创建日志实体并保存到数据库，id 自增所以传 null
        EmpLog empLog = new EmpLog(null, LocalDateTime.now(), info);
        empLogService.insertLog(empLog); // 写入 emp_log 表
    }
}