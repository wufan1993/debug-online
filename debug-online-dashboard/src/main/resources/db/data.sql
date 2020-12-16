DELETE FROM machine_info;

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (1,'127.0.0.1', 'debug-online-dashboard', '本地测试机器','com.wufan.test.*');


DELETE FROM method_info;

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,1,'127.0.0.1', 'com.test.package', 'getData',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,2,'127.0.0.1', 'com.wufan.test.TestException', 'testExe1',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (2,3,'127.0.0.1', 'com.wufan.test.TestException', 'testThreadExe1',1);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,4,'127.0.0.1', 'com.wufan.test.TestBigData', 'testThread',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,5,'127.0.0.1', 'com.wufan.test.ApiTest', 'http_lt1',0);