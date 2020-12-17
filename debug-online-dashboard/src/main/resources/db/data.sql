DELETE FROM machine_info;

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (1,'192.168.80.65', 'debug-online-dashboard', '本地测试机器','com.wufan.test.*');

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (2,'11.29.134.58', 'o2o-trade-search', '搜索网关第一套','com.jd.o2o.web.product.(web.controller|rpc|service).*');

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (3,'11.27.137.240', 'o2o-trade-search', '搜索网关第二套','com.jd.o2o.web.product.(web.controller|rpc|service).*');

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (4,'11.26.68.24', 'o2o-search-center-new', '搜索核心查询服务-第一套','com.jd.o2o.search.center.service.*');

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (5,'10.173.108.148', 'o2o-search-center-new', '搜索核心查询服务-第二套','com.jd.o2o.search.center.service.*');

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (6,'10.173.255.21', 'do2o-product-sale-web', 'o2o-product-sale-web','com.jd.o2o.pms.web.*');

DELETE FROM method_info;

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,1,'192.168.80.65', 'com.test.package', 'getData',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,2,'192.168.80.65', 'com.wufan.test.TestException', 'testExe1',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (2,3,'192.168.80.65', 'com.wufan.test.TestException', 'testThreadExe1',1);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,4,'192.168.80.65', 'com.wufan.test.TestBigData', 'testThread',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,5,'192.168.80.65', 'com.wufan.test.ApiTest', 'http_lt1',0);


INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,6,'11.29.134.58', 'com.jd.o2o.web.product.service.facade.homesearch.AbstractHomeSearchService', 'searchByStoreMulti',0);
INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (6,7,'11.29.134.58', 'com.jd.o2o.web.product.service.facade.homesearch.AbstractHomeSearchService', 'queryMultiStoreDimensionResult',1);
INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,8,'11.29.134.58', 'com.jd.o2o.web.product.service.facade.homesearch.AbstractHomeSearchService', 'searchStoreGoods',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,9,'11.27.137.240', 'com.jd.o2o.web.product.service.facade.homesearch.AbstractHomeSearchService', 'searchByStoreMulti',0);
INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (9,10,'11.27.137.240', 'com.jd.o2o.web.product.service.facade.homesearch.AbstractHomeSearchService', 'queryMultiStoreDimensionResult',1);
INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,11,'11.27.137.240', 'com.jd.o2o.web.product.service.facade.homesearch.AbstractHomeSearchService', 'searchStoreGoods',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,12,'10.173.108.148', 'com.jd.o2o.search.center.service.saf.SearchServiceFifteenImpl', 'searchByStoreMulti',0);
INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,13,'10.173.108.148', 'com.jd.o2o.search.center.service.saf.SearchServiceImpl', 'searchAllSkuListByCache',0);
INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (13,14,'10.173.108.148', 'com.jd.o2o.search.center.rpc.pms.impl.CategoryAttrRpcImpl', 'queryCategoryAttr',1);