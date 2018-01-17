#!/bin/bash

docker-compose --file docker-compose-test.yml up --exit-code-from cucumber
exitcode=$?
docker-compose --file docker-compose-test.yml down
exit $exitcode
