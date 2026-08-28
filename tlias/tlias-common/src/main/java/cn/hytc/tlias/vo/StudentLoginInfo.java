package cn.hytc.tlias.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentLoginInfo {
    private Integer id;
    private String name;
    private String no;
    private String token;
    private String image;
    private Integer clazzId;
    private String clazzName;
    private String clazzRoom;
    private String clazzMaster;
    private String clazzSubject;
    private String clazzBegin;
    private String clazzEnd;
}