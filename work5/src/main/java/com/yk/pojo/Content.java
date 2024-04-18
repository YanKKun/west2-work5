package com.yk.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 12080
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer formUserId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer toUserId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer toGroupId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String content;
}
