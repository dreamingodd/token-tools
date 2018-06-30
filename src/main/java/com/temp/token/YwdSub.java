package com.temp.token;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author qyc
 */
public final class YwdSub extends Contract {
    private static final String BINARY = "60a0604052600460608190527f48302e3100000000000000000000000000000000000000000000000000000000608090815261003e91600691906100d7565b50341561004757fe5b604051610b8f380380610b8f833981016040908152815160208301519183015160608401519193928301929091015b600160a060020a033316600090815260016020908152604082208690559085905583516100a991600391908601906100d7565b506004805460ff191660ff841617905580516100cc9060059060208401906100d7565b505b50505050610177565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061011857805160ff1916838001178555610145565b82800160010185558215610145579182015b8281111561014557825182559160200191906001019061012a565b5b50610152929150610156565b5090565b61017491905b80821115610152576000815560010161015c565b5090565b90565b610a09806101866000396000f300606060405236156100935763ffffffff60e060020a60003504166306fdde0381146100a9578063095ea7b31461013957806318160ddd1461016c57806323b872dd1461018e578063313ce567146101c757806354fd4d50146101ed57806370a082311461027d57806395d89b41146102ab578063a9059cbb1461033b578063cae9ca511461036e578063dd62ed3e146103e5575b341561009b57fe5b6100a75b60006000fd5b565b005b34156100b157fe5b6100b9610419565b6040805160208082528351818301528351919283929083019185019080838382156100ff575b8051825260208311156100ff57601f1990920191602091820191016100df565b505050905090810190601f16801561012b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561014157fe5b610158600160a060020a03600435166024356104a7565b604080519115158252519081900360200190f35b341561017457fe5b61017c610512565b60408051918252519081900360200190f35b341561019657fe5b610158600160a060020a0360043581169060243516604435610518565b604080519115158252519081900360200190f35b34156101cf57fe5b6101d761060e565b6040805160ff9092168252519081900360200190f35b34156101f557fe5b6100b9610617565b6040805160208082528351818301528351919283929083019185019080838382156100ff575b8051825260208311156100ff57601f1990920191602091820191016100df565b505050905090810190601f16801561012b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561028557fe5b61017c600160a060020a03600435166106a5565b60408051918252519081900360200190f35b34156102b357fe5b6100b96106c4565b6040805160208082528351818301528351919283929083019185019080838382156100ff575b8051825260208311156100ff57601f1990920191602091820191016100df565b505050905090810190601f16801561012b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561034357fe5b610158600160a060020a0360043516602435610752565b604080519115158252519081900360200190f35b341561037657fe5b604080516020600460443581810135601f8101849004840285018401909552848452610158948235600160a060020a03169460248035956064949293919092019181908401838280828437509496506107fe95505050505050565b604080519115158252519081900360200190f35b34156103ed57fe5b61017c600160a060020a03600435811690602435166109b0565b60408051918252519081900360200190f35b6003805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561049f5780601f106104745761010080835404028352916020019161049f565b820191906000526020600020905b81548152906001019060200180831161048257829003601f168201915b505050505081565b600160a060020a03338116600081815260026020908152604080832094871680845294825280832086905580518681529051929493927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a35060015b92915050565b60005481565b600160a060020a0383166000908152600160205260408120548290108015906105685750600160a060020a0380851660009081526002602090815260408083203390941683529290522054829010155b80156105745750600082115b1561060257600160a060020a03808416600081815260016020908152604080832080548801905588851680845281842080548990039055600283528184203390961684529482529182902080548790039055815186815291519293927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a3506001610606565b5060005b5b9392505050565b60045460ff1681565b6006805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561049f5780601f106104745761010080835404028352916020019161049f565b820191906000526020600020905b81548152906001019060200180831161048257829003601f168201915b505050505081565b600160a060020a0381166000908152600160205260409020545b919050565b6005805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561049f5780601f106104745761010080835404028352916020019161049f565b820191906000526020600020905b81548152906001019060200180831161048257829003601f168201915b505050505081565b600160a060020a03331660009081526001602052604081205482901080159061077b5750600082115b156107ef57600160a060020a03338116600081815260016020908152604080832080548890039055938716808352918490208054870190558351868152935191937fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929081900390910190a350600161050c565b50600061050c565b5b92915050565b600160a060020a03338116600081815260026020908152604080832094881680845294825280832087905580518781529051929493927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a383600160a060020a031660405180807f72656365697665417070726f76616c28616464726573732c75696e743235362c81526020017f616464726573732c627974657329000000000000000000000000000000000000815250602e019050604051809103902060e060020a9004338530866040518563ffffffff1660e060020a0281526004018085600160a060020a0316600160a060020a0316815260200184815260200183600160a060020a0316600160a060020a03168152602001828051906020019080838360008314610950575b80518252602083111561095057601f199092019160209182019101610930565b505050905090810190601f16801561097c5780820380516001836020036101000a031916815260200191505b509450505050506000604051808303816000876161da5a03f19250505015156109a55760006000fd5b5060015b9392505050565b600160a060020a038083166000908152600260209081526040808320938516835292905220545b929150505600a165627a7a7230582095fa82c5fd259e30fb0a7f8bfebb52ec799ca55b26bdbbea3153c6cebdb2fd080029";
//    private static final String BINARY = "0x60606040526103e86003556040805190810160405280600481526020017f49595744000000000000000000000000000000000000000000000000000000008152506040805190810160405280601581526020017f496365206f66205957442073756273746974757465000000000000000000000081525060126a52b7d2dcc80cd2e400000083600090805190602001906200009c929190620000e2565b508260019080519060200190620000b5929190620000e2565b5081600260006101000a81548160ff021916908360ff160217905550806003819055505050505062000191565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200012557805160ff191683800117855562000156565b8280016001018555821562000156579182015b828111156200015557825182559160200191906001019062000138565b5b50905062000165919062000169565b5090565b6200018e91905b808211156200018a57600081600090555060010162000170565b5090565b90565b61130780620001a16000396000f3006060604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde03146100b4578063095ea7b31461014257806318160ddd1461019c57806323b872dd146101c5578063313ce5671461023e57806354afc9a91461026d57806370a082311461028257806395d89b41146102cf578063a9059cbb1461035d578063be45fd62146103b7578063dd62ed3e14610454575b600080fd5b34156100bf57600080fd5b6100c76104c0565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101075780820151818401526020810190506100ec565b50505050905090810190601f1680156101345780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561014d57600080fd5b610182600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610568565b604051808215151515815260200191505060405180910390f35b34156101a757600080fd5b6101af6106e8565b6040518082815260200191505060405180910390f35b34156101d057600080fd5b610224600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506106f2565b604051808215151515815260200191505060405180910390f35b341561024957600080fd5b610251610b0a565b604051808260ff1660ff16815260200191505060405180910390f35b341561027857600080fd5b610280610b21565b005b341561028d57600080fd5b6102b9600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610b69565b6040518082815260200191505060405180910390f35b34156102da57600080fd5b6102e2610bb2565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610322578082015181840152602081019050610307565b50505050905090810190601f16801561034f5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561036857600080fd5b61039d600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610c5a565b604051808215151515815260200191505060405180910390f35b34156103c257600080fd5b61043a600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001909190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610e64565b604051808215151515815260200191505060405180910390f35b341561045f57600080fd5b6104aa600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506111f6565b6040518082815260200191505060405180910390f35b6104c86112c7565b60018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561055e5780601f106105335761010080835404028352916020019161055e565b820191906000526020600020905b81548152906001019060200180831161054157829003601f168201915b5050505050905090565b60006105f982600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461127d90919063ffffffff16565b600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a36001905092915050565b6000600354905090565b600080600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205411801561077f5750600082115b8015610807575081600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410155b8015610852575081600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410155b15610afe576108a982600460008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461129b90919063ffffffff16565b600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555061093e82600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461127d90919063ffffffff16565b600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550610a1082600560008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461129b90919063ffffffff16565b600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a360019050610b03565b600090505b9392505050565b6000600260009054906101000a900460ff16905090565b600354600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550565b6000600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b610bba6112c7565b60008054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c505780601f10610c2557610100808354040283529160200191610c50565b820191906000526020600020905b815481529060010190602001808311610c3357829003601f168201915b5050505050905090565b60008082118015610caa5750600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548211155b8015610cbc5750610cba836112b4565b155b15610e5957610d1382600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461129b90919063ffffffff16565b600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550610da882600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461127d90919063ffffffff16565b600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a360019050610e5e565b600090505b92915050565b600080600084118015610eb65750600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548411155b8015610ec75750610ec6856112b4565b5b156111e957610f1e84600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461129b90919063ffffffff16565b600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550610fb384600460008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205461127d90919063ffffffff16565b600460008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508490508073ffffffffffffffffffffffffffffffffffffffff1663c0ee0b8a3386866040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b838110156110bb5780820151818401526020810190506110a0565b50505050905090810190601f1680156110e85780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b151561110857600080fd5b5af1151561111557600080fd5b505050826040518082805190602001908083835b60208310151561114e5780518252602082019150602081019050602083039250611129565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390208573ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fe19260aff97b920c7df27010903aeb9c8d2be5d310a2c67824cf3f15396e4c16876040518082815260200191505060405180910390a4600191506111ee565b600091505b509392505050565b6000600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905092915050565b600080828401905083811015151561129157fe5b8091505092915050565b60008282111515156112a957fe5b818303905092915050565b600080823b905060008111915050919050565b6020604051908101604052806000815250905600a165627a7a72305820b1a1ca628f04ff8f6912778680b1cdfec3e48e61f586baed684e50f07604ac9a0029";

    private YwdSub(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private YwdSub(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Approval",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Approval",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Future<Utf8String> name() throws IOException {
        Function function = new Function("name",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeCallSingleValueReturn(function);
    }

    public TransactionReceipt approve(Address spender, Uint256 value) throws IOException, TransactionException {
        Function function = new Function("approve", Arrays.<Type>asList(spender, value), Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public Future<Uint256> totalSupply() throws IOException {
        Function function = new Function("totalSupply",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeCallSingleValueReturn(function);
    }

    public TransactionReceipt transferFrom(Address from, Address to, Uint256 value) throws IOException, TransactionException {
        Function function = new Function("transferFrom", Arrays.<Type>asList(from, to, value), Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public Future<Uint8> decimals() throws IOException {
        Function function = new Function("decimals",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }));
        return executeCallSingleValueReturn(function);
    }

    public Future<Utf8String> version() throws IOException {
        Function function = new Function("version",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeCallSingleValueReturn(function);
    }

    public Uint256 balanceOf(Address owner) throws IOException {
        Function function = new Function("balanceOf",
                Arrays.<Type>asList(owner),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeCallSingleValueReturn(function);
    }

    public Future<Utf8String> symbol() throws IOException {
        Function function = new Function("symbol",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeCallSingleValueReturn(function);
    }

    public TransactionReceipt transfer(Address to, Uint256 value) throws IOException, TransactionException {
        Function function = new Function("transfer", Arrays.<Type>asList(to, value), Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public TransactionReceipt approveAndCall(Address spender, Uint256 value, DynamicBytes extraData) throws IOException, TransactionException {
        Function function = new Function("approveAndCall", Arrays.<Type>asList(spender, value, extraData), Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public Future<Uint256> allowance(Address owner, Address spender) throws IOException {
        Function function = new Function("allowance",
                Arrays.<Type>asList(owner, spender),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeCallSingleValueReturn(function);
    }

    public static RemoteCall<YwdSub> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList());
        return deployRemoteCall(YwdSub.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static YwdSub load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new YwdSub(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static YwdSub load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new YwdSub(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TransferEventResponse {
        public Address from;

        public Address to;

        public Uint256 value;
    }

    public static class ApprovalEventResponse {
        public Address owner;

        public Address spender;

        public Uint256 value;
    }

}
