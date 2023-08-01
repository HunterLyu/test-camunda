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
