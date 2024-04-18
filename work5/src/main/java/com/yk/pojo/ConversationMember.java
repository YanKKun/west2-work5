package com.yk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 12080
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationMember {
    private Integer id;
    private Integer conversationId;
    private Integer uid;
}
