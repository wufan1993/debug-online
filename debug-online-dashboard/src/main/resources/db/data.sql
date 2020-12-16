DELETE FROM machine_info;

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (1,'49.232.171.225', 'debug-online-dashboard', '本地测试机器','com.wufan.test.*');

DELETE FROM method_info;

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,1,'49.232.171.225', 'com.test.package', 'getData',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,2,'49.232.171.225', 'com.wufan.test.TestException', 'testExe1',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (2,3,'49.232.171.225', 'com.wufan.test.TestException', 'testThreadExe1',1);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,4,'49.232.171.225', 'com.wufan.test.TestBigData', 'testThread',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,5,'49.232.171.225', 'com.wufan.test.ApiTest', 'http_lt1',0);