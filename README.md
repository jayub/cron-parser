# cron-parser

This is a simple cron-parser app. It parses the crontab string and prints out the timings and the command on the screen.

It can be called with the parameters in the following format:

```java -jar app_name <minute> <hour> <day_of_month> <month> <day_of_week> <command>```

For example: 
```
java -jar cron-parser-1.0-SNAPSHOT.jar */4 */7 1 3,4,7 * /user/bin/find
```
will print out
```
minute         0 4 8 12 16 20 24 28 32 36 40 44 48 52 56
hour           0 7 14 21
day of month   1
month          3 4 7
day of week    0 1 2 3 4 5 6
command        /user/bin/find
```

Note that you may need to escape the asterisk (*). e.g.
```
java -jar cron-parser-1.0-SNAPSHOT.jar \*/4 */7 1 3,4,7 \* /user/bin/find
```



