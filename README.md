# TradeFlux

Install JDK and maven:
```shell
sudo apt install openjdk-21-jdk
sudo apt install maven
```
The protoc compiler uses a plugin architecture for generating language-specific code:

1. **Base protoc compiler**: Handles parsing `.proto` files and general Protocol Buffer functionality
2. **Language plugins**: Generate language-specific serialization code (like Java classes)
3. **gRPC plugins**: Generate additional RPC-related code for services defined in the `.proto` files

For Java development, you need two components:
- The Java plugin (built into `protoc`) for basic message classes (used with `--java_out`)
```
sudo apt install protobuf-compiler
```
- The gRPC Java plugin for service interfaces and stubs (used with `--grpc-java_out`)
```
sudo apt-get install protobuf-compiler-grpc-java
```

To generate Java files from the `.proto` run:
```
protoc --experimental_allow_proto3_optional --java_out=<your_java_project> --grpc-java_out=<your_java_project> <your_proto_file>
```

### Note
If you're encountering an error like this when running the above `protoc` command try to install the plugin manually instead of using the package manager.
```
 protoc --experimental_allow_proto3_optional \
  --java_out=java/src/main/java \
  --grpc-java_out=java/src/main/java \
  grpc/tradeflux.proto
protoc-gen-grpc-java: program not found or is not executable
Please specify a program using absolute path or make sure the program is available in your PATH system variable
--grpc-java_out: protoc-gen-grpc-java: Plugin failed with status code 1.
```
When you run the `protoc` command with the `--grpc-java_out` flag, the protoc compiler looks for an executable named `protoc-gen-grpc-java` in your PATH. This executable is the actual plugin that generates the gRPC code for Java.
```shell
mkdir -p ~/bin
# adjust for your system architecture
wget https://repo1.maven.org/maven2/io/grpc/protoc-gen-grpc-java/<grpc_version>/protoc-gen-grpc-java-<grpc_version>-linux-x86_64.exe -O ~/bin/protoc-gen-grpc-java
chmod +x ~/bin/protoc-gen-grpc-java
echo 'export PATH="$HOME/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc
```

```