#!/bin/bash

docker-compose up --exit-code-from cucumber
exitcode=$?
docker-compose down
exit $exitcode
