#!/bin/sh
PRG="\$0"

APP_HOME=`dirname "\$PRG"`

APP_HOME=`cd "\$APP_HOME"/.. ; pwd`

APP_CP=`ls "\$APP_HOME"/lib/*.jar | sed ':a;N;\$!ba;s/\\n/:/g'`

java -cp \$APP_CP ${mainClass} \$*

#base=\$(cd "\$(dirname \$0)/.."; pwd)
#CP='.'
#for file in `find \$base -name "*.jar"`;do
#    CP=\$CP:\$file
#done
#java -cp \$CP ${mainClass} \$*