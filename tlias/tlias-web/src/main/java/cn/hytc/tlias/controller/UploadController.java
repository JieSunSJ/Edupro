package cn.hytc.tlias.controller;

import cn.hytc.tlias.config.AliyunOSSOperator;
import cn.hytc.tlias.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 文件上传控制器
 */
@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    /** 上传文件 */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        try {
            log.info("开始上传文件：{}", file.getOriginalFilename());

            if (file == null || file.isEmpty()) {
                log.warn("上传文件为空");
                return Result.error("上传文件不能为空");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                log.warn("文件格式错误：{}", originalFilename);
                return Result.error("文件格式不正确");
            }

            long fileSize = file.getSize();
            if (fileSize > 2 * 1024 * 1024) {
                log.warn("文件大小超出限制：{} bytes", fileSize);
                return Result.error("文件大小不能超过2MB");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                log.warn("文件类型不是图片：{}", contentType);
                return Result.error("只能上传图片文件");
            }

            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;

            log.info("正在上传到阿里云OSS：{}", uniqueFileName);
            String url = aliyunOSSOperator.upload(file.getBytes(), uniqueFileName);

            log.info("文件上传成功：{}", url);
            return Result.success(url);

        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error("上传失败：" + e.getMessage());
        }
    }
}