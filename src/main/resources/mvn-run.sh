
# Build
mvn clean package

# Eth send (Attention: Win use ; - semicolon as separator while linux use : - colon)
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.SendEth "0x20771815237d62BA03737123C96f90678c3Ade46" "0xAd837b3C9C34295e797D94fB421c4b9280fC6d1F" "0.00989"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.SendEth "0x20771815237d62BA03737123C96f90678c3Ade46" "0xAd837b3C9C34295e797D94fB421c4b9280fC6d1F" "0.00989"

# west-1 0x6dc65fba23b862e813ab5bbc0ce10604c3f97741
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.TransferToken "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" 18 "0x6dc65fba23b862e813ab5bbc0ce10604c3f97741" "0x9DED1C6648F45Ca720cd0555744abace5CE6e5cB" "1"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.AirDrop "/opt/token-tools/src/test/resources/air-drop-FT-voting-1.csv" "0x6dc65fba23b862e813ab5bbc0ce10604c3f97741" "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" "1"

# east-1 0xe189559b51098a8faa2399911ec6e0f492b0db01 0x0ebe20df1c1170485b2bcbdbc6cb3b29f70423df
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.AirDropWithoutSign "/opt/token-tools/src/test/resources/air-drop-FT-voting-2.csv" "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" "0xe189559b51098a8faa2399911ec6e0f492b0db01" "1"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.TransferToken "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" 18 "0xe189559b51098a8faa2399911ec6e0f492b0db01" "0x9DED1C6648F45Ca720cd0555744abace5CE6e5cB" "1"

# jp-1 0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.AirDropWithoutSign "/opt/token-tools/src/test/resources/air-drop-FT-voting-3.csv" "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" "0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940" "1"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.AirdropByContract "/opt/token-tools/src/test/resources/air-drop-test.csv" "0x3f81686a83B277eC4F781019De0280b6d6e017D3" "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" 18 "0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940" "1"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.CreateContract "0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940" "/opt/token-tools/src/test/resources/contract/Airdrop.bin"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.CreateToken "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" 18 "0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940" "0x3f81686a83B277eC4F781019De0280b6d6e017D3" "500"
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.TransferToken "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" 18 "0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940" "0x3f81686a83B277eC4F781019De0280b6d6e017D3" "500"

# hk-1
java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.AirDropWithoutSign "/opt/token-tools/src/test/resources/air-drop-FT-voting-4.csv" "0xb17df9a3b09583a9bdcf757d6367171476d4d8a3" "0xe4e73673d3511430024fe8a3d9fae6499ed4eaa0" "1"


java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.AirdropByContract "/opt/token-tools/src/test/resources/kushen-airdrop.csv" "0x3f81686a83B277eC4F781019De0280b6d6e017D3" "0x4824a7b64e3966b0133f4f4ffb1b9d6beb75fff7" 18 "0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940" "1600"

java -classpath "target/token-tools.jar:target/lib/*" com.temp.eth.SendEth "0xE268D8C22739b9abDabdFB14763fF60d0dE5e3ba" "0xd3a79de5B70D788bFf9Cc455Af80fC4dE1B8F940" "0.005560956583540971"