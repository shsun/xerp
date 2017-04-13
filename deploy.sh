#!/bin/bash

echo "copy war to webapps"
cp -rfv ./target/*.war ${TOMCAT_HOME}/webapps;


#停tomcat并执行SQL
#service tomcat stop
bash ${TOMCAT_HOME}/bin/shutdown.sh;
sleep 2
killall -9 java


echo "start tomcat"
rm -rf ${TOMCAT_HOME}/work
#service tomcat start
bash ${TOMCAT_HOME}/bin/startup.sh;
sleep 2
echo "start tomcat done..........." 

tail -f ${TOMCAT_HOME}/logs/catalina.out;
