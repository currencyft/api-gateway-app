insert into route_data (id,date,path,method,uri,env)
values (10001, current_date(), '/get','GET','http://httpbin.org','');

insert into route_data (id,date,path,method,uri,env)
values (10002, current_date(), '/cardbacks','GET','https://omgvamp-hearthstone-v1.p.rapidapi.com','');

insert into route_data (id,date,path,method,uri,env)
values (10003, current_date(), '/get-routes','GET','http://localhost:8000','');

insert into route_data (id,date,path,method,uri,env)
values (10004, current_date(), '/invoke-route-service-feign','GET','http://localhost:8100','');
