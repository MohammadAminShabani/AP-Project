-- ==========================================
-- CATEGORIES
-- ==========================================

-- Main Categories
INSERT INTO categories (id,name,parent_category_id) VALUES (1,'وسایل نقلیه',NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (2,'املاک',NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (3,'کالای دیجیتال',NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (4,'لوازم خانگی',NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (5,'وسایل شخصی',NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (6,'سرگرمی و فراغت',NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (7,'تجهیزات و صنعتی',NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (8,'استخدام و خدمات',NULL) ON CONFLICT (id) DO NOTHING;

-- Vehicles
INSERT INTO categories (id,name,parent_category_id) VALUES (9,'خودرو',1) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (10,'موتورسیکلت',1) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (11,'دوچرخه',1) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (12,'قطعات خودرو',1) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (13,'لوازم جانبی خودرو',1) ON CONFLICT (id) DO NOTHING;

-- Real Estate
INSERT INTO categories (id,name,parent_category_id) VALUES (14,'آپارتمان',2) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (15,'خانه و ویلا',2) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (16,'زمین',2) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (17,'دفتر کار',2) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (18,'مغازه',2) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (19,'انبار',2) ON CONFLICT (id) DO NOTHING;

-- Digital
INSERT INTO categories (id,name,parent_category_id) VALUES (20,'موبایل',3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (21,'لپ‌تاپ',3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (22,'کامپیوتر رومیزی',3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (23,'تبلت',3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (24,'ساعت هوشمند',3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (25,'کنسول بازی',3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (26,'دوربین',3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (27,'پرینتر',3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (28,'قطعات کامپیوتر',3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (29,'لوازم جانبی موبایل',3) ON CONFLICT (id) DO NOTHING;

-- Home
INSERT INTO categories (id,name,parent_category_id) VALUES (30,'یخچال',4) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (31,'ماشین لباسشویی',4) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (32,'تلویزیون',4) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (33,'مایکروویو',4) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (34,'جاروبرقی',4) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (35,'مبلمان',4) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (36,'میز و صندلی',4) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (37,'تخت خواب',4) ON CONFLICT (id) DO NOTHING;

-- Personal
INSERT INTO categories (id,name,parent_category_id) VALUES (38,'پوشاک مردانه',5) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (39,'پوشاک زنانه',5) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (40,'کفش',5) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (41,'کیف',5) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (42,'ساعت',5) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (43,'زیورآلات',5) ON CONFLICT (id) DO NOTHING;

-- Entertainment
INSERT INTO categories (id,name,parent_category_id) VALUES (44,'کتاب',6) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (45,'ساز موسیقی',6) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (46,'دوچرخه ورزشی',6) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (47,'لوازم کمپینگ',6) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (48,'اسباب بازی',6) ON CONFLICT (id) DO NOTHING;

-- Industrial
INSERT INTO categories (id,name,parent_category_id) VALUES (49,'ابزارآلات',7) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (50,'ماشین آلات',7) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (51,'تجهیزات فروشگاهی',7) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (52,'تجهیزات پزشکی',7) ON CONFLICT (id) DO NOTHING;

-- Services
INSERT INTO categories (id,name,parent_category_id) VALUES (53,'استخدام',8) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (54,'خدمات نظافت',8) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (55,'خدمات آموزشی',8) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (56,'تعمیرات',8) ON CONFLICT (id) DO NOTHING;
INSERT INTO categories (id,name,parent_category_id) VALUES (57,'حمل و نقل',8) ON CONFLICT (id) DO NOTHING;


-- ==========================================
-- CITIES
-- ==========================================

INSERT INTO cities (id,name,province) VALUES (1,'تهران','تهران') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (2,'کرج','البرز') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (3,'مشهد','خراسان رضوی') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (4,'اصفهان','اصفهان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (5,'شیراز','فارس') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (6,'تبریز','آذربایجان شرقی') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (7,'قم','قم') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (8,'اهواز','خوزستان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (9,'کرمان','کرمان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (10,'یزد','یزد') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (11,'ارومیه','آذربایجان غربی') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (12,'رشت','گیلان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (13,'ساری','مازندران') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (14,'بندرعباس','هرمزگان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (15,'بوشهر','بوشهر') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (16,'همدان','همدان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (17,'سنندج','کردستان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (18,'قزوین','قزوین') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (19,'زنجان','زنجان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (20,'گرگان','گلستان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (21,'اردبیل','اردبیل') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (22,'خرم‌آباد','لرستان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (23,'اراک','مرکزی') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (24,'بیرجند','خراسان جنوبی') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (25,'زاهدان','سیستان و بلوچستان') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (26,'یاسوج','کهگیلویه و بویراحمد') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (27,'ایلام','ایلام') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (28,'شهرکرد','چهارمحال و بختیاری') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (29,'بجنورد','خراسان شمالی') ON CONFLICT (id) DO NOTHING;
INSERT INTO cities (id,name,province) VALUES (30,'کیش','هرمزگان') ON CONFLICT (id) DO NOTHING;

-- Resetting sequences
SELECT setval(pg_get_serial_sequence('categories','id'), COALESCE((SELECT MAX(id) FROM categories), 1));
SELECT setval(pg_get_serial_sequence('cities','id'), COALESCE((SELECT MAX(id) FROM cities), 1));