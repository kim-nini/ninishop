-- 모든 제약 조건 비활성화
-- SET REFERENTIAL_INTEGRITY FALSE;
-- truncate table user_tb;
-- truncate table product_tb;
-- truncate table order_tb;
-- truncate table option_tb;
-- truncate table item_tb;
-- truncate table wish_list_tb;
-- SET REFERENTIAL_INTEGRITY TRUE;
-- 모든 제약 조건 활성화

-- INSERT INTO user_tb (`id`,`email`,`password`,`username`, `roles`) VALUES ('1', 'admin@nate.com', '{bcrypt}$2a$10$8H0OT8wgtALJkig6fmypi.Y7jzI5Y7W9PGgRKqnVeS2cLWGifwHF2', 'admin', 'ROLE_ADMIN');
-- INSERT INTO user_tb (`id`,`email`,`password`,`username`, `roles`) VALUES ('2', 'ssar@nate.com', '{bcrypt}$2a$10$8H0OT8wgtALJkig6fmypi.Y7jzI5Y7W9PGgRKqnVeS2cLWGifwHF2', 'ssar', 'ROLE_USER');

INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('1', '[뮤지컬] 버지니아 울프', '충무아트센터 중극장 블랙', '/images/1.jpg', '44000');
INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('2', '[뮤지컬] 빨래', '대학로 유니플렉스 2관', '/images/2.jpg', '55000');
INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('3', '[뮤지컬] 웨스턴 스토리', '인터파크 유니플렉스 1관', '/images/3.jpg', '44000');
INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('4', '[뮤지컬] 베어 더 뮤지컬', '두산아트센터 연강홀', '/images/4.jpg', '66000');
INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('5', '[뮤지컬] 디아길레프', '예스24아트원 1관', '/images/5.jpg', '50000');
INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('6', '[뮤지컬] 매튜 본의 `로미오와 줄리엣`', 'LG아트센터 서울 LG SIGNATURE 홀', '/images/6.jpg', '30000');
-- INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('7', '[뮤지컬] 6시 퇴근', '대학로 SH아트홀', '/images/7.jpg', '26800');
-- INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('8', '[뮤지컬] 더 맨 얼라이브', '서울숲씨어터 2관', '/images/8.jpg', '25900');
-- INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('9', '[뮤지컬] 오즈', '대학로 TOM(티오엠) 2관', '/images/9.jpg', '797000');
-- INSERT INTO product_tb (`id`,`product_name`,`description`,`image`, `price`) VALUES ('10', '[뮤지컬] 진짜나쁜소녀', 'JTN 아트홀 1관', '/images/10.jpg', '8900');
--
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('1', '1', 'S석', '44000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('2', '1', 'R석', '66000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('3', '2', 'S석', '55000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('4', '2', 'R석', '66000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('5', '3', 'A석', '44000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('6', '3', 'S석', '66000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('7', '3', 'R석', '88000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('8', '4', 'S석', '66000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('9', '4', 'R석', '88000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('10', '5', 'S석', '50000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('11', '5', 'R석', '70000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('12', '6', 'B석', '30000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('13', '6', 'A석', '60000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('14', '6', 'S석', '90000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('15', '6', 'R석', '120000');
INSERT INTO option_tb (`id`,`product_id`,`option_name`,`price`) VALUES ('16', '6', 'VIP석', '140000');
--
--
-- INSERT INTO order_tb (`id`,`user_id`) VALUES ('1', '2');
--
-- INSERT INTO item_tb (`id`,`option_id`, `quantity`, `price`, `order_id`) VALUES ('1', '1', '5', '10000', '1');
-- INSERT INTO item_tb (`id`,`option_id`, `quantity`, `price`, `order_id`) VALUES ('2', '2', '5', '10000', '1');
-- INSERT INTO item_tb (`id`,`option_id`, `quantity`, `price`, `order_id`) VALUES ('3', '10', '5', '10000', '1');
--
-- INSERT INTO cart_tb (`id`,`user_id`, `option_id`, `quantity`, `price`) VALUES ('1', '2', '5', '3', '10000');
-- INSERT INTO cart_tb (`id`,`user_id`, `option_id`, `quantity`, `price`) VALUES ('2', '2', '10', '4', '10000');
-- INSERT INTO cart_tb (`id`,`user_id`, `option_id`, `quantity`, `price`) VALUES ('3', '2', '11', '5', '10000');




