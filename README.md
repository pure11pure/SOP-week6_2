# SOP-week6_2

เปิด Docker 
>ทำการ run
>![image](https://github.com/pure11pure/SOP-week6_2/assets/140476156/05ad06c2-4e44-4051-a39b-af2430ccd2b2)

เปิด mongoDB
>ทำการ connect
>![image](https://github.com/pure11pure/SOP-week6_2/assets/140476156/8f67441a-358e-4b1b-8a19-f2984c6ea399)

เปิด CMD
>docker exec -it my-redis redis-cli monitor
>*my-redis คือชื่อที่ตั้งในรูป
>![image](https://github.com/pure11pure/SOP-week6_2/assets/140476156/50b44018-5169-4ed2-a785-faf09f429c1f)

เช็ค application.properties
>spring.data.mongodb.host=localhost
>spring.data.mongodb.port=27017
>spring.data.mongodb.database=SOA

>spring.cache.type=redis
>spring.data.redis.host=localhost
>spring.data.redis.port=6379

เช็ค Week62Application.java
> @EnableCaching
