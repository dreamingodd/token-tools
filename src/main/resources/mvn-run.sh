
# Build
mvn clean package

# Eth send
java -cp target/token-tools.jar com.temp.SendEth "0x20771815237d62BA03737123C96f90678c3Ade46" "0xAd837b3C9C34295e797D94fB421c4b9280fC6d1F" "mvc123$%^" "0.03"
