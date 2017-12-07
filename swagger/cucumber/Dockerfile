FROM maven:3-jdk-8

RUN mkdir cucumber

WORKDIR cucumber
RUN mkdir target

COPY target/ target/
COPY run_cucumber.sh .

ENTRYPOINT bash run_cucumber.sh
