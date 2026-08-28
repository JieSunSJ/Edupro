package cn.hytc.tlias.interceptor;

import cn.hytc.tlias.entity.OperateLog;
import cn.hytc.tlias.entity.User;
import cn.hytc.tlias.mapper.UserMapper;
import cn.hytc.tlias.service.OperateLogService;
import cn.hytc.tlias.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Aspect
public class OperateLogAspect {

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private UserMapper userMapper;

    private static final Map<String, String> ACTION_MAP;
    private static final Map<String, String> MODULE_MAP;

    static {
        ACTION_MAP = new HashMap<>();
        ACTION_MAP.put("add", "新增");
        ACTION_MAP.put("addStudent", "新增学生");
        ACTION_MAP.put("update", "修改");
        ACTION_MAP.put("updateStudent", "修改学生");
        ACTION_MAP.put("updateProfile", "修改个人信息");
        ACTION_MAP.put("updatePassword", "修改密码");
        ACTION_MAP.put("updateImage", "修改头像");
        ACTION_MAP.put("delete", "删除");
        ACTION_MAP.put("deleteById", "删除");

        MODULE_MAP = new HashMap<>();
        MODULE_MAP.put("StudentController", "学生管理");
        MODULE_MAP.put("ClazzController", "班级管理");
        MODULE_MAP.put("UserController", "用户管理");
        MODULE_MAP.put("UploadController", "文件上传");
    }

    @Around("execution(* cn.hytc.tlias.controller.*.add*(..)) || " +
            "execution(* cn.hytc.tlias.controller.*.update*(..)) || " +
            "execution(* cn.hytc.tlias.controller.*.delete*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long costTime = System.currentTimeMillis() - startTime;

        try {
            String className = joinPoint.getTarget().getClass().getSimpleName();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String methodName = signature.getMethod().getName();

            OperateLog operateLog = new OperateLog();
            operateLog.setClassName(className);
            operateLog.setMethodName(methodName);
            operateLog.setOperateTime(LocalDateTime.now());
            operateLog.setCostTime(costTime);

            String action = ACTION_MAP.getOrDefault(methodName, methodName);
            String module = MODULE_MAP.getOrDefault(className, className);
            operateLog.setAction(module + " - " + action);

            Object[] args = joinPoint.getArgs();
            String params = Arrays.stream(args)
                    .filter(a -> a != null && !(a instanceof Map))
                    .map(a -> {
                        String s = a.toString();
                        return s.length() > 200 ? s.substring(0, 200) + "..." : s;
                    })
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            operateLog.setMethodParams(params.length() > 500 ? params.substring(0, 500) : params);

            if (result != null) {
                String retStr = result.toString();
                operateLog.setReturnValue(retStr.length() > 500 ? retStr.substring(0, 500) : retStr);
            }

            String detail = module + " - " + action;
            if (args.length > 0) {
                Object firstArg = args[0];
                if (firstArg instanceof cn.hytc.tlias.entity.Student) {
                    cn.hytc.tlias.entity.Student s = (cn.hytc.tlias.entity.Student) firstArg;
                    detail += ": " + s.getName();
                } else if (firstArg instanceof cn.hytc.tlias.entity.Clazz) {
                    cn.hytc.tlias.entity.Clazz c = (cn.hytc.tlias.entity.Clazz) firstArg;
                    detail += ": " + c.getName();
                } else if (firstArg instanceof Integer) {
                    detail += ", ID=" + firstArg;
                }
            }
            operateLog.setDetail(detail);

            Integer userId = CurrentHolder.getCurrentId();
            if (userId != null) {
                operateLog.setOperateUserId(userId);
                try {
                    User user = userMapper.getById(userId);
                    if (user != null) {
                        operateLog.setOperateUser(user.getName());
                    }
                } catch (Exception e) {
                    operateLog.setOperateUser("ID:" + userId);
                }
            }

            operateLogService.save(operateLog);
        } catch (Exception e) {
            log.error("记录操作日志失败: {}", e.getMessage());
        }

        return result;
    }
}