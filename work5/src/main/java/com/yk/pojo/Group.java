package com.yk.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private Integer id;
    @Size(min = 3,max = 10,message = "群名不得小于3个字符大于10个字符")
    private String groupName;
    private Integer groupLeaderId;
    private Integer memberNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime deleteTime;
    @JsonIgnore
    private Integer isDeleted;
}
