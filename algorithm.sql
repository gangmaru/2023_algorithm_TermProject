
-- create database algorithm;
use algorithm;

create table account(
`ID` varchar(255) primary key,
`PW` INT
);

create table main(
`main_course` varchar(15) primary key
);

create table node_inf(
`n_id` INT primary key,
`node_name` varchar(45),
`node_cost` INT,
`node_security` INT,
`main_main_course` VARCHAR(15) references main(main_course), 
`node_x` INT,
`node_y` int
); 

create table distance(
`d_id` INT primary key,
`source` varchar(45) references node_inf(node_name),
`destination` varchar(45) references node_inf(node_name),
`result` INT,
`stopovers` varchar(320)
);

create table posts(
`post_id` varchar(15) primary key,
`text` TEXT
);

-- 여행지역 추가
INSERT INTO main (main_course)
VALUES ('Seoul'), ('Busan'), ('Gyeongju');

INSERT INTO node_inf (n_id, node_name, node_cost, node_security, main_main_course, node_x, node_y)
VALUES (0, 'Seoul Palace', 50000, 4, 'Seoul', 37000, 127000),
       (1, 'Myeongdong Shopping', 30000, 1, 'Seoul', 37034, 126789),
       (2, 'Namsan Tower', 40000, 3, 'Seoul', 37456, 126012),
       (3, 'Gyeongbokgung Palace', 44000, 2, 'Seoul', 37678, 125345),
       (4, 'Insadong Art Street', 35000, 7, 'Seoul', 37890, 124678),
       (5, 'Hongdae District', 40000, 5, 'Seoul', 37100, 127000),
       (6, 'Garosugil Street', 33000, 6, 'Seoul', 37234, 126789);

-- 부산 관광 코스 추가
INSERT INTO node_inf (n_id, node_name, node_cost, node_security, main_main_course, node_x, node_y)
VALUES (7, 'Haeundae Beach', 40000, 2, 'Busan', 35000, 129000),
       (8, 'Gamcheon Culture Village', 30000, 15, 'Busan', 35233, 128780),
       (9, 'Yongdusan Park', 35000, 1, 'Busan', 35456, 128012),
       (10, 'Taejongdae Park', 45000, 4, 'Busan', 35678, 127345),
       (11, 'Gukje Market', 32000, 3, 'Busan', 35890, 126678),
       (12, 'Dalmaji Hill', 42000, 5, 'Busan', 35210, 129000),
       (13, 'Songdo Beach', 37000, 14, 'Busan', 35234, 128709);

-- 경주 관광 코스 추가
INSERT INTO node_inf (n_id, node_name, node_cost, node_security, main_main_course, node_x, node_y)
VALUES (14, 'Bulguksa Temple', 50000, 22, 'Gyeongju', 35, 16),
       (15, 'Seokguram Grotto', 40000, 1, 'Gyeongju', 36, 9),
       (16, 'Anapji Pond', 35000, 3, 'Gyeongju', 45, 10),
       (17, 'Cheomseongdae Observatory', 45000, 4, 'Gyeongju', 65, 15),
       (18, 'Royal Tomb Complex', 30000, 2, 'Gyeongju', 25, 13),
       (19, 'Gyeongju National Museum', 42000, 15, 'Gyeongju', 31, 19),
       (20, 'Yangdong Folk Village', 32000, 14, 'Gyeongju', 39, 12);

select*from node_inf;

INSERT INTO distance (d_id, source, destination, result)
VALUES
  (0, 'Seoul Palace', 'Seoul Palace', 0),
  (1, 'Seoul Palace', 'Myeongdong Shopping', 0),
  (2, 'Seoul Palace', 'Namsan Tower', 0),
  (3, 'Seoul Palace', 'Gyeongbokgung Palace', 0),
  (4, 'Seoul Palace', 'Insadong Art Street', 0),
  (5, 'Seoul Palace', 'Hongdae District', 0),
  (6, 'Seoul Palace', 'Garosugil Street', 0),
  (7, 'Myeongdong Shopping', 'Seoul Palace', 0),
  (8, 'Myeongdong Shopping', 'Myeongdong Shopping', 0),
  (9, 'Myeongdong Shopping', 'Namsan Tower', 0),
  (10, 'Myeongdong Shopping', 'Gyeongbokgung Palace', 0),
  (11, 'Myeongdong Shopping', 'Insadong Art Street', 0),
  (12, 'Myeongdong Shopping', 'Hongdae District', 0),
  (13, 'Myeongdong Shopping', 'Garosugil Street', 0),
  (14, 'Namsan Tower', 'Seoul Palace', 0),
  (15, 'Namsan Tower', 'Myeongdong Shopping', 0),
  (16, 'Namsan Tower', 'Namsan Tower', 0),
  (17, 'Namsan Tower', 'Gyeongbokgung Palace', 0),
  (18, 'Namsan Tower', 'Insadong Art Street', 0),
  (19, 'Namsan Tower', 'Hongdae District', 0),
  (20, 'Namsan Tower', 'Garosugil Street', 0),
  (21, 'Gyeongbokgung Palace', 'Seoul Palace', 0),
  (22, 'Gyeongbokgung Palace', 'Myeongdong Shopping', 0),
  (23, 'Gyeongbokgung Palace', 'Namsan Tower', 0),
  (24, 'Gyeongbokgung Palace', 'Gyeongbokgung Palace', 0),
  (25, 'Gyeongbokgung Palace', 'Insadong Art Street', 0),
  (26, 'Gyeongbokgung Palace', 'Hongdae District', 0),
  (27, 'Gyeongbokgung Palace', 'Garosugil Street', 0),
  (28, 'Insadong Art Street', 'Seoul Palace', 0),
  (29, 'Insadong Art Street', 'Myeongdong Shopping', 0),
  (30, 'Insadong Art Street', 'Namsan Tower', 0),
  (31, 'Insadong Art Street', 'Gyeongbokgung Palace', 0),
  (32, 'Insadong Art Street', 'Insadong Art Street', 0),
  (33, 'Insadong Art Street', 'Hongdae District', 0),
  (34, 'Insadong Art Street', 'Garosugil Street', 0),
  (35, 'Hongdae District', 'Seoul Palace', 0),
  (36, 'Hongdae District', 'Myeongdong Shopping', 0),
  (37, 'Hongdae District', 'Namsan Tower', 0),
  (38, 'Hongdae District', 'Gyeongbokgung Palace', 0),
  (39, 'Hongdae District', 'Insadong Art Street', 0),
  (40, 'Hongdae District', 'Hongdae District', 0),
  (41, 'Hongdae District', 'Garosugil Street', 0),
  (42, 'Garosugil Street', 'Seoul Palace', 0),
  (43, 'Garosugil Street', 'Myeongdong Shopping', 0),
  (44, 'Garosugil Street', 'Namsan Tower', 0),
  (45, 'Garosugil Street', 'Gyeongbokgung Palace', 0),
  (46, 'Garosugil Street', 'Insadong Art Street', 0),
  (47, 'Garosugil Street', 'Hongdae District', 0),
  (48, 'Garosugil Street', 'Garosugil Street', 0);
  
  -- 부산에 대한 관광 코스 간의 거리 정보 추가
INSERT INTO distance (d_id, source, destination, result)
VALUES
  (49, 'Busan Beach', 'Busan Beach', 0),
  (50, 'Busan Beach', 'Haeundae Beach', 0),
  (51, 'Busan Beach', 'Jagalchi Market', 0),
  (52, 'Busan Beach', 'Gamcheon Culture Village', 0),
  (53, 'Busan Beach', 'Beomeosa Temple', 0),
  (54, 'Busan Beach', 'Nampodong Shopping Street', 0),
  (55, 'Busan Beach', 'Yonggungsa Temple', 0),
  (56, 'Haeundae Beach', 'Busan Beach', 0),
  (57, 'Haeundae Beach', 'Haeundae Beach', 0),
  (58, 'Haeundae Beach', 'Jagalchi Market', 0),
  (59, 'Haeundae Beach', 'Gamcheon Culture Village', 0),
  (60, 'Haeundae Beach', 'Beomeosa Temple', 0),
  (61, 'Haeundae Beach', 'Nampodong Shopping Street', 0),
  (62, 'Haeundae Beach', 'Yonggungsa Temple', 0),
  (63, 'Jagalchi Market', 'Busan Beach', 0),
  (64, 'Jagalchi Market', 'Haeundae Beach', 0),
  (65, 'Jagalchi Market', 'Jagalchi Market', 0),
  (66, 'Jagalchi Market', 'Gamcheon Culture Village', 0),
  (67, 'Jagalchi Market', 'Beomeosa Temple', 0),
  (68, 'Jagalchi Market', 'Nampodong Shopping Street', 0),
  (69, 'Jagalchi Market', 'Yonggungsa Temple', 0),
  (70, 'Gamcheon Culture Village', 'Busan Beach', 0),
  (71, 'Gamcheon Culture Village', 'Haeundae Beach', 0),
  (72, 'Gamcheon Culture Village', 'Jagalchi Market', 0),
  (73, 'Gamcheon Culture Village', 'Gamcheon Culture Village', 0),
  (74, 'Gamcheon Culture Village', 'Beomeosa Temple', 0),
  (75, 'Gamcheon Culture Village', 'Nampodong Shopping Street', 0),
  (76, 'Gamcheon Culture Village', 'Yonggungsa Temple', 0),
  (77, 'Beomeosa Temple', 'Busan Beach', 0),
  (78, 'Beomeosa Temple', 'Haeundae Beach', 0),
  (79, 'Beomeosa Temple', 'Jagalchi Market', 0),
  (80, 'Beomeosa Temple', 'Gamcheon Culture Village', 0),
  (81, 'Beomeosa Temple', 'Beomeosa Temple', 0),
  (82, 'Beomeosa Temple', 'Nampodong Shopping Street', 0),
  (83, 'Beomeosa Temple', 'Yonggungsa Temple', 0),
  (84, 'Nampodong Shopping Street', 'Busan Beach', 0),
  (85, 'Nampodong Shopping Street', 'Haeundae Beach', 0),
  (86, 'Nampodong Shopping Street', 'Jagalchi Market', 0),
  (87, 'Nampodong Shopping Street', 'Gamcheon Culture Village', 0),
  (88, 'Nampodong Shopping Street', 'Beomeosa Temple', 0),
  (89, 'Nampodong Shopping Street', 'Nampodong Shopping Street', 0),
  (90, 'Nampodong Shopping Street', 'Yonggungsa Temple', 0),
  (91, 'Yonggungsa Temple', 'Busan Beach', 0),
  (92, 'Yonggungsa Temple', 'Haeundae Beach', 0),
  (93, 'Yonggungsa Temple', 'Jagalchi Market', 0),
  (94, 'Yonggungsa Temple', 'Gamcheon Culture Village', 0),
  (95, 'Yonggungsa Temple', 'Beomeosa Temple', 0),
  (96, 'Yonggungsa Temple', 'Nampodong Shopping Street', 0),
  (97, 'Yonggungsa Temple', 'Yonggungsa Temple', 0);

-- 경주에 대한 관광 코스 간의 거리 정보 추가
INSERT INTO distance (d_id, source, destination, result)
VALUES
  (98, 'Bulguksa Temple', 'Bulguksa Temple', 0),
  (99, 'Bulguksa Temple', 'Seokguram Grotto', 0),
  (100, 'Bulguksa Temple', 'Anapji Pond', 0),
  (101, 'Bulguksa Temple', 'Gyeongju National Museum', 0),
  (102, 'Bulguksa Temple', 'Cheomseongdae Observatory', 0),
  (103, 'Bulguksa Temple', 'Donggung Palace and Wolji Pond', 0),
  (104, 'Bulguksa Temple', 'Tumuli Park', 0),
  (105, 'Seokguram Grotto', 'Bulguksa Temple', 0),
  (106, 'Seokguram Grotto', 'Seokguram Grotto', 0),
  (107, 'Seokguram Grotto', 'Anapji Pond', 0),
  (108, 'Seokguram Grotto', 'Gyeongju National Museum', 0),
  (109, 'Seokguram Grotto', 'Cheomseongdae Observatory', 0),
  (110, 'Seokguram Grotto', 'Donggung Palace and Wolji Pond', 0),
  (111, 'Seokguram Grotto', 'Tumuli Park', 0),
  (112, 'Anapji Pond', 'Bulguksa Temple', 0),
  (113, 'Anapji Pond', 'Seokguram Grotto', 0),
  (114, 'Anapji Pond', 'Anapji Pond', 0),
  (115, 'Anapji Pond', 'Gyeongju National Museum', 0),
  (116, 'Anapji Pond', 'Cheomseongdae Observatory', 0),
  (117, 'Anapji Pond', 'Donggung Palace and Wolji Pond', 0),
  (118, 'Anapji Pond', 'Tumuli Park', 0),
  (119, 'Gyeongju National Museum', 'Bulguksa Temple', 0),
  (120, 'Gyeongju National Museum', 'Seokguram Grotto', 0),
  (121, 'Gyeongju National Museum', 'Anapji Pond', 0),
  (122, 'Gyeongju National Museum', 'Gyeongju National Museum', 0),
  (123, 'Gyeongju National Museum', 'Cheomseongdae Observatory', 0),
  (124, 'Gyeongju National Museum', 'Donggung Palace and Wolji Pond', 0),
  (125, 'Gyeongju National Museum', 'Tumuli Park', 0),
  (126, 'Cheomseongdae Observatory', 'Bulguksa Temple', 0),
  (127, 'Cheomseongdae Observatory', 'Seokguram Grotto', 0),
  (128, 'Cheomseongdae Observatory', 'Anapji Pond', 0),
  (129, 'Cheomseongdae Observatory', 'Gyeongju National Museum', 0),
  (130, 'Cheomseongdae Observatory', 'Cheomseongdae Observatory', 0),
  (131, 'Cheomseongdae Observatory', 'Donggung Palace and Wolji Pond', 0),
  (132, 'Cheomseongdae Observatory', 'Tumuli Park', 0),
  (133, 'Donggung Palace and Wolji Pond', 'Bulguksa Temple', 0),
  (134, 'Donggung Palace and Wolji Pond', 'Seokguram Grotto', 0),
  (135, 'Donggung Palace and Wolji Pond', 'Anapji Pond', 0),
  (136, 'Donggung Palace and Wolji Pond', 'Gyeongju National Museum', 0),
  (137, 'Donggung Palace and Wolji Pond', 'Cheomseongdae Observatory', 0),
  (138, 'Donggung Palace and Wolji Pond', 'Donggung Palace and Wolji Pond', 0),
  (139, 'Donggung Palace and Wolji Pond', 'Tumuli Park', 0),
  (140, 'Tumuli Park', 'Bulguksa Temple', 0),
  (141, 'Tumuli Park', 'Seokguram Grotto', 0),
  (142, 'Tumuli Park', 'Anapji Pond', 0),
  (143, 'Tumuli Park', 'Gyeongju National Museum', 0),
  (144, 'Tumuli Park', 'Cheomseongdae Observatory', 0),
  (145, 'Tumuli Park', 'Donggung Palace and Wolji Pond', 0),
  (146, 'Tumuli Park', 'Tumuli Park', 0);
