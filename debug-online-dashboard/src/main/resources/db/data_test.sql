DELETE FROM machine_info;

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (1,'192.168.80.65', 'debug-online-dashboard', '本地测试机器','com.wufan.debug.online.test.method.*');


DELETE FROM method_info;


INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,2,'192.168.80.65', 'com.wufan.debug.online.test.method.TestException', 'testExe1',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (2,3,'192.168.80.65', 'com.wufan.debug.online.test.method.TestException', 'testThreadExe1',1);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,4,'192.168.80.65', 'com.wufan.debug.online.test.method.TestBigData', 'testThread',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,5,'192.168.80.65', 'com.wufan.debug.online.test.method.ApiTest', 'http_lt1',0);