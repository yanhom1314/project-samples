#!/usr/bin/env bash
base=\$(cd "\$(dirname \$0)"; pwd)
CP='.'
for file in `find \$base -name "*.jar"`;do
    CP=\$CP:\$file
done
java -cp \$CP ${mainClass} \$*