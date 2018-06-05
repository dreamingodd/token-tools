#!/bin/bash
kill -9 `ps -ef | grep "geth --rpc"|egrep -v "grep"|awk '{print $2}'`
sleep 20s
nohup geth --rpc --rpcport 12421 --rpcaddr 0.0.0.0 --rpccorsdomain "*" --rpcapi "db,eth,net,web3,personal" >> /opt/log/geth/geth.log 2>&1 &