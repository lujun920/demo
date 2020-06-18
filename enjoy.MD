## 常用性能排查命令
### top

### netstat
统计当前连接情况
netstat -nat|awk '{print $6}'|sort|uniq -c|sort -rn | head -10

sort:  对单词进行排序

uniq -c:  显示唯一的行，并在每行行首加上本行在文件中出现的次数

sort -k1,1nr:  按照第一个字段，数值排序，且为逆序

head -10:  取前10行数据

```aidl
>    181 TIME_WAIT
      35 ESTABLISHED
       9 LISTEN
       3 CLOSE_WAIT
       1 established)
       1 Foreign
```
### top
top -Hp 16147
```aidl
top - 20:31:18 up 358 days,  2:54,  1 user,  load average: 0.28, 0.37, 0.70
Threads: 389 total,   0 running, 389 sleeping,   0 stopped,   0 zombie

```

### jps
jps -mlvV
java启动jvm配置

### jinfo
系统启动配置参数

