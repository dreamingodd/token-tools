
# Build
mvn clean package

# Eth send (Attention: Win use ; - semicolon as separator while linux use : - colon)
java -classpath "target/token-tools.jar:target/lib/*" com.temp.SendEth "0x20771815237d62BA03737123C96f90678c3Ade46" "0xAd837b3C9C34295e797D94fB421c4b9280fC6d1F" "0.00989"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.SendEth "0x20771815237d62BA03737123C96f90678c3Ade46" "0xAd837b3C9C34295e797D94fB421c4b9280fC6d1F" "0.00989"

# west-1
java -classpath "target/token-tools.jar:target/lib/*" com.temp.TransferToken "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" 18 "0x6dc65fba23b862e813ab5bbc0ce10604c3f97741" "0x9DED1C6648F45Ca720cd0555744abace5CE6e5cB" "1"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.AirDrop "/opt/token-tools/src/test/resources/air-drop-FT-voting-1.csv" "0x6dc65fba23b862e813ab5bbc0ce10604c3f97741" "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" "1"
# east-1
java -classpath "target/token-tools.jar:target/lib/*" com.temp.AirDropWithoutSign "/opt/token-tools/src/test/resources/air-drop-FT-voting-2.csv" "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" "0xe189559b51098a8faa2399911ec6e0f492b0db01" "1"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.TransferToken "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" 18 "0xe189559b51098a8faa2399911ec6e0f492b0db01" "0x9DED1C6648F45Ca720cd0555744abace5CE6e5cB" "1"
# jp-1
java -classpath "target/token-tools.jar:target/lib/*" com.temp.AirDropWithoutSign "/opt/token-tools/src/test/resources/air-drop-FT-voting-3.csv" "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" "0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940" "1"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.TransferToken "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" 18 "0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940" "0x9DED1C6648F45Ca720cd0555744abace5CE6e5cB" "1"
# hk-1
java -classpath "target/token-tools.jar:target/lib/*" com.temp.AirDropWithoutSign "/opt/token-tools/src/test/resources/air-drop-FT-voting-4.csv" "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" "0xe4e73673d3511430024fe8a3d9fae6499ed4eaa0" "1"
