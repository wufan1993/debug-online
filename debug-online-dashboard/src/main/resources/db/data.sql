DELETE FROM machine_info;

INSERT INTO machine_info (id,ip, name,desc,regexp) VALUES
  (1,'192.168.80.65', 'debug-online-dashboard', '本地测试机器','com.wufan.test.*');

DELETE FROM method_info;

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,1,'192.168.80.65', 'com.test.package', 'getData',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,2,'192.168.80.65', 'com.wufan.test.TestException', 'testExe1',0);

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (2,3,'192.168.80.65', 'com.wufan.test.TestException', 'testThreadExe1',1);