DELETE FROM machine_info;

INSERT INTO machine_info (id,ip, name,desc) VALUES
  (1,'127.0.0.1', 'debug-online-dashboard', '本地测试机器');

DELETE FROM method_info;

INSERT INTO method_info (pid,id,ip, type_name,method_name,status) VALUES
  (-1,1,'127.0.0.1', 'com.test.package', 'getData',0);