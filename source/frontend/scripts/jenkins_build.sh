#!/bin/sh

cd $WORKSPACE
rm -Rf $WORKSPACE/typings
npm install

$WORKSPACE/scripts/run_tslint_jenkins.sh  > $WORKSPACE/tslint.xml | true
sed -i 's|file name="|file name="/home/hat/CI/workspace/SolicitudesBA-frontend/|g' "$WORKSPACE/tslint.xml"
npm run citest
