##CSV file handler

|Technology|Badge|
|:---------:|:----|
|Travis CI|[![Build Status](https://travis-ci.org/vvoloshin/severgroup.svg?branch=master)](https://travis-ci.org/vvoloshin/severgroup)|
|jaCoCo|[![codecov](https://codecov.io/gh/vvoloshin/severgroup/branch/master/graph/badge.svg)](https://codecov.io/gh/vvoloshin/severgroup)|




1. Download "csvhandler.jar" and "config.properties", put both of them in the same folder

2. Run "runme.sh" in your local directory, it will create a directory tree (by default, can be changed inside runme.sh script):
```$xslt

/somefolder
            runme.sh
            csvhandler.jar [dont touch him, seriously]
            config.properties
            /csvparsing
                        --/inbox
                        --/outbox
                        --/parsed
                        --/log
                        --/error
```


3. If you still want to run the app, config the file "config.properties" (in accordance with paragraph 1) - in the same folder as the original jar and
 sh files, as default properties is (absolute path, not relative):

```$xslt
dir.read=/home/starlord/csvparsing/inbox/
dir.write=/home/starlord/csvparsing/outbox/
dir.error=/home/starlord/csvparsing/error/
dir.parsed=/home/starlord/csvparsing/parsed/
``` 

4. Put the "*.csv" file with header in dir ``inbox``, if the file is well converted, 
the new file will be located in the ``outbox`` folder with the prefix "avg_", 
the original file will be moved to the ``parsed`` folder. 
If an error occurs during the conversion (e.g. incorrect file structure, it happens to everyone), 
the source file will be moved to the ``error`` folder. 
Accordingly, application logs are written to the ``log`` folder.

5. For stop the application brutally and cold-bloodedly - kill his process (Ctr^C) and God help you


* in default, app run with UTC.Offset(+3 hour)
* for those who want to try to convert *.csv in the resulting file we - has two files (regulartestfile.csv,
onerecordtwodates.csv), place them in ``inbox``, maybe something happens
* in the process of work may occur "some" exceptions, 
just do not pay attention to them, everything is fine, don`t panic





         
        