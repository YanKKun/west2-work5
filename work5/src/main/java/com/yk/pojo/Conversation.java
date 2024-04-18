package com.yk.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 12080
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer fromUserId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer toUserId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer toGroupId;
    private Integer lastSenderId;
    private String lastContent;
    private LocalDateTime lastTime;

}
