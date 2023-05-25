# Use a base image with GraalVM and Maven pre-installed
FROM ghcr.io/graalvm/graalvm-ce:ol9-java17-22.3.2 as builder

# Set the working directory
WORKDIR /app

# Copy the source code into the container
COPY ./src /app/src
COPY ./pom.xml /app

ARG MAVEN_VERSION=3.6.3
ARG MAVEN_SHA=c35a1803a6e70a126e80b2b3ae33eed961f83ed74d18fcd16909b2d44d7dada3203f1ffe726c17ef8dcca2dcaa9fca676987befeadc9b9f759967a8cb77181c0
ARG MAVEN_BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${MAVEN_BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${MAVEN_SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

# Build the Maven project and generate the native image binary
RUN mvn clean package -Pnative

# Use a base image for the final runtime
FROM ghcr.io/graalvm/graalvm-ce:ol9-java17-22.3.2

# Set the working directory
WORKDIR /app

# Copy the native image binary from the builder stage
COPY --from=builder /app/target/demo /app

# Set the entry point for the container
CMD ["./demo"]
