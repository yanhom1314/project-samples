#!/bin/sh
PRG="\$0"

APP_HOME=`dirname "\$PRG"`

APP_HOME=`cd "\$APP_HOME"/.. ; pwd`

APP_CP=`ls "\$APP_HOME"/lib/*.jar | sed ':a;N;\$!ba;s/\\n/:/g'`

java -cp .:\$APP_CP ${mainClass} \$*