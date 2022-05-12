INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('1','1','Brazil','','https://g1.globo.com/','','pt-br','','g1');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('1','https://g1.globo.com/','1');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('1','.mc-column.content-text','text','1');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('1','1');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('2','1','Brazil','','https://istoe.com.br/','','pt-br','','istoe');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('2','https://istoe.com.br/','2');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('2','.content-section.content > p','text','2');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('2','2');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('3','1','Brazil','','https://noticias.r7.com/','','pt-br','','r7');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('3','https://noticias.r7.com/','3');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('3','article.toolkit-media-content > p','text','3');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('3','3');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('4','1','Brazil','','https://noticias.uol.com.br/','','pt-br','','uol-noticias');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('4','https://noticias.uol.com.br/','4');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('4','div.text > p','text','4');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('4','4');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('5','1','Brazil','','https://www.bbc.com/portuguese/','','pt-br','','bbc-portuguese');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('5','https://www.bbc.com/portuguese/','5');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('5','main > div > p','text','5');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('5','5');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('6','1','Brazil','','https://www.band.uol.com.br/','','pt-br','','band-uol');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('6','https://www.band.uol.com.br/','6');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('6','.content .ng-star-inserted > div > p','text','6');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('6','6');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('7','1','Brazil','','https://www.metropoles.com/','','pt-br','','metropoles');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('7','https://www.metropoles.com/','7');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('7','.m-content > p','text','7');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('7','7');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('8','1','Brazil','','https://www.correiobraziliense.com.br/','','pt-br','','correiobraziliense');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('8','https://www.correiobraziliense.com.br/','8');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('8','.article > p','text','8');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('8','8');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('9','1','Brazil','','https://veja.abril.com.br/','','pt-br','','veja');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('9','https://veja.abril.com.br/','9');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('9','.content p','text','9');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('9','9');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('10','1','Brazil','','https://www.terra.com.br/','','pt-br','','terra');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('10','https://www.terra.com.br/','10');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('10','.article__content--body > p','text','10');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('10','10');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('11','1','Brazil','','https://www.cnnbrasil.com.br/','','pt-br','','cnn-brasil');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('11','https://www.cnnbrasil.com.br/','11');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('11','.post__content > p','text','11');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('11','11');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('12','1','Brazil','','https://www.em.com.br/','','pt-br','','estado-de-minas');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('12','https://www.em.com.br/','12');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('12','.article-content article #rs_read_this2 > div','text','12');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('12','12');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('13','1','Brazil','','https://www.otempo.com.br/','','pt-br','','otempo');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('13','https://www.otempo.com.br/','13');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('13','.texto-artigo > p','text','13');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('13','13');




INSERT INTO SITE_CONFIGURATION (id,scraping_type,country,description,domain,keywords,language, logo,name)
VALUES ('14','1','Brazil','','https://www1.folha.uol.com.br/','','pt-br','','folha-de-sao-paulo');
INSERT INTO REGISTERED_SITE (id,url,site_configuration_id) VALUES ('14','https://www1.folha.uol.com.br/','14');
INSERT INTO SELECTOR_QUERY (id,selector,attribute,site_configuration_id) VALUES ('14','.c-news__body > p','text','14');
INSERT INTO SITE_CONFIGURATION_SELECTOR_QUERIES  (site_configuration_id,selector_queries_id)
VALUES ('14','14');