### pre-requirement
1. install Kafka
2. Postgresql
      1. set username/password
  
           default username/password: root/123456
         
           or replace it in application.properties
      
      2. create a database flowable_demo_service
 
### api list

### kafka topic info



holiday request process

1. employee apply request with his info -> user task
2. send to kafak -> outbound event registry
3. oa service listen to kafka, send message to other topic
4. flowable app wait for another topic

休假申请
申请信息：
申请人
假期类型
休假开始，结束时间

流程：
1. 申请人填写申请 flowable
2. send http task, check meet holiday
3. listen mq (yes/no)
4. no, process finish
5. yes, send message to boss queue, and wait for response
6
user task(apply)

1. 申请人提交申请(flowable app, user task), 信息包含用户身份和假期开始-结束时间
2. flowable app 发送微服务请求，微服务判断申请人假期是否可用，将假期可用信息 发送到 kafka topic-employ-holiday-avaiable
3. flowable app 监听  topic-employ-holiday-avaiable, 如果消息是假期不够，则拒绝申请，否则 发送申请人信息到 kafka topic-holiday-need-boss-decision, 并等待mq信息返回
4. 微服务监听 topic-holiday-need-boss-decision，主管根据工期等各因素综合判断是否批准假期，将最终决定发送到kafka topic-holiday-boss-decision
5. flowable app 收到kafaka topic-holiday-boss-decision 信息后 结束整个申请（拒绝/同意）