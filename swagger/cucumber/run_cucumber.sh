#!/bin/bash

java -cp "target/libs/*:target/classes:target/test-classes" \
    io.lozzikit.mail.api.spec.CliRunner

