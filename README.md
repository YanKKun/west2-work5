# 存放west2-work5作业的仓库

## 接口文档：

[apifox.com/apidoc/shared-db44b887-4451-423e-959b-dbae23f2f33c/api-165764468](https://apifox.com/apidoc/shared-db44b887-4451-423e-959b-dbae23f2f33c/api-165764468)

## 技术栈：

springboot，mysql，mybatis，mybatisplus，redis，druid，websocket

## 项目结构：

└─yk       

​	├─config                                   --配置类

​	├─controller     			--控制层

​	├─exception			     --异常类

​        ├─filter				      --过滤器

​        ├─mapper				--dao层

​        ├─pojo				       --实体类

​        │      ├─dto				--dto类

​	│      ├─vo				  --vo类

​        │      └─ws				  --websocket类

​       ├─service				    --业务层

​       │       └─impl				--业务实现类

​       └─utils					  --工具类

## 项目功能

- 实现了IM 即时通信系统 支持单聊，群聊，并且支持查找一定时间内的聊天记录
- 包含websocket模块，用户模块，联系人模块，会话模块，信息模块
- 使用redis实现了未读消息数量，近期未读历史消息的查看
- 实现了会话列表， 支持文字，图片交流， 屏蔽功能
- 通过spring security token认证加入聊天室

