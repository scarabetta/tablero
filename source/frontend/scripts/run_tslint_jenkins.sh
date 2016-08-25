#!/bin/sh

FILES=$(find src/ -iname *.ts)
TEST_FILES=$(find test/ -iname *.ts)

echo "<pmd version=\"tslint\">" 2>&1
for file in $FILES $TEST_FILES
do
    echo $(tslint -c tslint.json  $file -t pmd)| sed '/^\s*$/d' | sed -e "s/<pmd version=\"tslint\">//g" | sed -e "s|</pmd>||g" 2>&1
done
echo "</pmd>" 2>&1

