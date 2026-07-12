-- ==========================================
-- CATEGORIES
-- ==========================================

-- Main Categories

INSERT INTO categories (id,name,parent_category_id) VALUES (1,'وسایل نقلیه',NULL);
INSERT INTO categories (id,name,parent_category_id) VALUES (2,'املاک',NULL);
INSERT INTO categories (id,name,parent_category_id) VALUES (3,'کالای دیجیتال',NULL);
INSERT INTO categories (id,name,parent_category_id) VALUES (4,'لوازم خانگی',NULL);
INSERT INTO categories (id,name,parent_category_id) VALUES (5,'وسایل شخصی',NULL);
INSERT INTO categories (id,name,parent_category_id) VALUES (6,'سرگرمی و فراغت',NULL);
INSERT INTO categories (id,name,parent_category_id) VALUES (7,'تجهیزات و صنعتی',NULL);
INSERT INTO categories (id,name,parent_category_id) VALUES (8,'استخدام و خدمات',NULL);


-- Vehicles

INSERT INTO categories (id,name,parent_category_id) VALUES (9,'خودرو',1);
INSERT INTO categories (id,name,parent_category_id) VALUES (10,'موتورسیکلت',1);
INSERT INTO categories (id,name,parent_category_id) VALUES (11,'دوچرخه',1);
INSERT INTO categories (id,name,parent_category_id) VALUES (12,'قطعات خودرو',1);
INSERT INTO categories (id,name,parent_category_id) VALUES (13,'لوازم جانبی خودرو',1);


-- Real Estate

INSERT INTO categories (id,name,parent_category_id) VALUES (14,'آپارتمان',2);
INSERT INTO categories (id,name,parent_category_id) VALUES (15,'خانه و ویلا',2);
INSERT INTO categories (id,name,parent_category_id) VALUES (16,'زمین',2);
INSERT INTO categories (id,name,parent_category_id) VALUES (17,'دفتر کار',2);
INSERT INTO categories (id,name,parent_category_id) VALUES (18,'مغازه',2);
INSERT INTO categories (id,name,parent_category_id) VALUES (19,'انبار',2);


-- Digital

INSERT INTO categories (id,name,parent_category_id) VALUES (20,'موبایل',3);
INSERT INTO categories (id,name,parent_category_id) VALUES (21,'لپ‌تاپ',3);
INSERT INTO categories (id,name,parent_category_id) VALUES (22,'کامپیوتر رومیزی',3);
INSERT INTO categories (id,name,parent_category_id) VALUES (23,'تبلت',3);
INSERT INTO categories (id,name,parent_category_id) VALUES (24,'ساعت هوشمند',3);
INSERT INTO categories (id,name,parent_category_id) VALUES (25,'کنسول بازی',3);
INSERT INTO categories (id,name,parent_category_id) VALUES (26,'دوربین',3);
INSERT INTO categories (id,name,parent_category_id) VALUES (27,'پرینتر',3);
INSERT INTO categories (id,name,parent_category_id) VALUES (28,'قطعات کامپیوتر',3);
INSERT INTO categories (id,name,parent_category_id) VALUES (29,'لوازم جانبی موبایل',3);


-- Home

INSERT INTO categories (id,name,parent_category_id) VALUES (30,'یخچال',4);
INSERT INTO categories (id,name,parent_category_id) VALUES (31,'ماشین لباسشویی',4);
INSERT INTO categories (id,name,parent_category_id) VALUES (32,'تلویزیون',4);
INSERT INTO categories (id,name,parent_category_id) VALUES (33,'مایکروویو',4);
INSERT INTO categories (id,name,parent_category_id) VALUES (34,'جاروبرقی',4);
INSERT INTO categories (id,name,parent_category_id) VALUES (35,'مبلمان',4);
INSERT INTO categories (id,name,parent_category_id) VALUES (36,'میز و صندلی',4);
INSERT INTO categories (id,name,parent_category_id) VALUES (37,'تخت خواب',4);


-- Personal

INSERT INTO categories (id,name,parent_category_id) VALUES (38,'پوشاک مردانه',5);
INSERT INTO categories (id,name,parent_category_id) VALUES (39,'پوشاک زنانه',5);
INSERT INTO categories (id,name,parent_category_id) VALUES (40,'کفش',5);
INSERT INTO categories (id,name,parent_category_id) VALUES (41,'کیف',5);
INSERT INTO categories (id,name,parent_category_id) VALUES (42,'ساعت',5);
INSERT INTO categories (id,name,parent_category_id) VALUES (43,'زیورآلات',5);


-- Entertainment

INSERT INTO categories (id,name,parent_category_id) VALUES (44,'کتاب',6);
INSERT INTO categories (id,name,parent_category_id) VALUES (45,'ساز موسیقی',6);
INSERT INTO categories (id,name,parent_category_id) VALUES (46,'دوچرخه ورزشی',6);
INSERT INTO categories (id,name,parent_category_id) VALUES (47,'لوازم کمپینگ',6);
INSERT INTO categories (id,name,parent_category_id) VALUES (48,'اسباب بازی',6);


-- Industrial

INSERT INTO categories (id,name,parent_category_id) VALUES (49,'ابزارآلات',7);
INSERT INTO categories (id,name,parent_category_id) VALUES (50,'ماشین آلات',7);
INSERT INTO categories (id,name,parent_category_id) VALUES (51,'تجهیزات فروشگاهی',7);
INSERT INTO categories (id,name,parent_category_id) VALUES (52,'تجهیزات پزشکی',7);


-- Services

INSERT INTO categories (id,name,parent_category_id) VALUES (53,'استخدام',8);
INSERT INTO categories (id,name,parent_category_id) VALUES (54,'خدمات نظافت',8);
INSERT INTO categories (id,name,parent_category_id) VALUES (55,'خدمات آموزشی',8);
INSERT INTO categories (id,name,parent_category_id) VALUES (56,'تعمیرات',8);
INSERT INTO categories (id,name,parent_category_id) VALUES (57,'حمل و نقل',8);


-- ==========================================
-- CITIES
-- ==========================================

INSERT INTO cities (id,name,province) VALUES (1,'تهران','تهران');
INSERT INTO cities (id,name,province) VALUES (2,'کرج','البرز');
INSERT INTO cities (id,name,province) VALUES (3,'مشهد','خراسان رضوی');
INSERT INTO cities (id,name,province) VALUES (4,'اصفهان','اصفهان');
INSERT INTO cities (id,name,province) VALUES (5,'شیراز','فارس');
INSERT INTO cities (id,name,province) VALUES (6,'تبریز','آذربایجان شرقی');
INSERT INTO cities (id,name,province) VALUES (7,'قم','قم');
INSERT INTO cities (id,name,province) VALUES (8,'اهواز','خوزستان');
INSERT INTO cities (id,name,province) VALUES (9,'کرمان','کرمان');
INSERT INTO cities (id,name,province) VALUES (10,'یزد','یزد');
INSERT INTO cities (id,name,province) VALUES (11,'ارومیه','آذربایجان غربی');
INSERT INTO cities (id,name,province) VALUES (12,'رشت','گیلان');
INSERT INTO cities (id,name,province) VALUES (13,'ساری','مازندران');
INSERT INTO cities (id,name,province) VALUES (14,'بندرعباس','هرمزگان');
INSERT INTO cities (id,name,province) VALUES (15,'بوشهر','بوشهر');
INSERT INTO cities (id,name,province) VALUES (16,'همدان','همدان');
INSERT INTO cities (id,name,province) VALUES (17,'سنندج','کردستان');
INSERT INTO cities (id,name,province) VALUES (18,'قزوین','قزوین');
INSERT INTO cities (id,name,province) VALUES (19,'زنجان','زنجان');
INSERT INTO cities (id,name,province) VALUES (20,'گرگان','گلستان');
INSERT INTO cities (id,name,province) VALUES (21,'اردبیل','اردبیل');
INSERT INTO cities (id,name,province) VALUES (22,'خرم‌آباد','لرستان');
INSERT INTO cities (id,name,province) VALUES (23,'اراک','مرکزی');
INSERT INTO cities (id,name,province) VALUES (24,'بیرجند','خراسان جنوبی');
INSERT INTO cities (id,name,province) VALUES (25,'زاهدان','سیستان و بلوچستان');
INSERT INTO cities (id,name,province) VALUES (26,'یاسوج','کهگیلویه و بویراحمد');
INSERT INTO cities (id,name,province) VALUES (27,'ایلام','ایلام');
INSERT INTO cities (id,name,province) VALUES (28,'شهرکرد','چهارمحال و بختیاری');
INSERT INTO cities (id,name,province) VALUES (29,'بجنورد','خراسان شمالی');
INSERT INTO cities (id,name,province) VALUES (30,'کیش','هرمزگان');

SELECT setval(pg_get_serial_sequence('categories','id'),
              (SELECT MAX(id) FROM categories));

SELECT setval(pg_get_serial_sequence('cities','id'),
              (SELECT MAX(id) FROM cities));