## CSV/XML file handler

|Technology|Badge|
|:---------:|:----|
|Travis CI|[![Build Status](https://travis-ci.org/vvoloshin/severgroup.svg?branch=master)](https://travis-ci.org/vvoloshin/severgroup)|
|jaCoCo|[![codecov](https://codecov.io/gh/vvoloshin/severgroup/branch/master/graph/badge.svg)](https://codecov.io/gh/vvoloshin/severgroup)|




- Download "csvhandler.jar" and "config.properties", put both of them in the same folder

- Run "runme.sh" in your local directory, it will create a directory tree (by default, can be changed inside runme.sh script):
```$xslt

/somefolder
            runme.sh
            csvhandler.jar [dont touch him, seriously]
            config.properties
            /csvparsing
                        --/inbox
                        --/outboxcsv
                        --/outboxxml
                        --/parsed
                        --/log
                        --/error
```
- If you still want to run the app, config the file "config.properties" (in accordance with paragraph 1) - in the same folder as the original jar and
 sh files, as default properties is (absolute path, not relative):

```$xslt
dir.read=/home/starlord/csvparsing/inbox/
dir.writecsv=/home/starlord/csvparsing/outboxcsv/
dir.writexml=/home/starlord/csvparsing/outboxxml/
dir.error=/home/starlord/csvparsing/error/
dir.parsed=/home/starlord/csvparsing/parsed/
dir.xsd=/home/starlord/csvparsing/xsd/
``` 

- Put the "*.csv/*.xml" file with header in dir ``inbox``, if the file is well converted, 
the new file will be located in the ``outbox`` folder with the prefix "avg_", 
the original file will be moved to the ``parsed`` folder. 
If an error occurs during the conversion (e.g. incorrect file structure, it happens to everyone), 
the source file will be moved to the ``error`` folder. 
Accordingly, application logs are written to the ``log`` folder.

- All file processing results (average records) are stored in the H2 database (memory), which is installed while 
the application is running and then destroyed. Also, during the launch of the application, several test entries are
 added to the database to show that it somehow works.

- The database itself can be accessed at: ``http://localhost:8080/records``
 and then find the desired entry by name. All this is possible thanks to the black magic of Spring Boot

- For stop the application brutally and cold-bloodedly - kill his process (Ctr^C) and God help you

optional:
1. in default, app run with UTC.Offset(+3 hour)
2. for those who want to try to convert "*.csv/*.xml" in the resulting file we - has two files (regulartestfile.csv,
onerecordtwodates.csv), place them in ``inbox``, maybe something happens
3. in the process of work may occur "some" exceptions, 
just do not pay attention to them, everything is fine, don`t panic
4. when you run the application, it generates an xsd schema for the output files in the ``xsd`` directory 
(surprising, isn't it?)




         
        