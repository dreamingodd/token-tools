package com.temp.token;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class HumanStandardToken extends Contract {
    // YWD
    private static final String BINARY = "60606040526040805190810160405280600481526020017f48302e3100000000000000000000000000000000000000000000000000000000815250600690805190602001906200005192919062000139565b5034156200005e57600080fd5b6040516200119f3803806200119f8339810160405280805190602001909190805182019190602001805190602001909190805182019190505083600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550836000819055508260039080519060200190620000fa92919062000139565b5081600460006101000a81548160ff021916908360ff16021790555080600590805190602001906200012e92919062000139565b5050505050620001e8565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200017c57805160ff1916838001178555620001ad565b82800160010185558215620001ad579182015b82811115620001ac5782518255916020019190600101906200018f565b5b509050620001bc9190620001c0565b5090565b620001e591905b80821115620001e1576000816000905550600101620001c7565b5090565b90565b610fa780620001f86000396000f3006060604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde03146100bf578063095ea7b31461014d57806318160ddd146101a757806323b872dd146101d0578063313ce5671461024957806354fd4d501461027857806370a082311461030657806395d89b4114610353578063a9059cbb146103e1578063cae9ca511461043b578063dd62ed3e146104d8575b34156100ba57600080fd5b600080fd5b34156100ca57600080fd5b6100d2610544565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101125780820151818401526020810190506100f7565b50505050905090810190601f16801561013f5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561015857600080fd5b61018d600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506105e2565b604051808215151515815260200191505060405180910390f35b34156101b257600080fd5b6101ba6106d4565b6040518082815260200191505060405180910390f35b34156101db57600080fd5b61022f600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506106da565b604051808215151515815260200191505060405180910390f35b341561025457600080fd5b61025c610956565b604051808260ff1660ff16815260200191505060405180910390f35b341561028357600080fd5b61028b610969565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102cb5780820151818401526020810190506102b0565b50505050905090810190601f1680156102f85780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561031157600080fd5b61033d600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610a07565b6040518082815260200191505060405180910390f35b341561035e57600080fd5b610366610a50565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103a657808201518184015260208101905061038b565b50505050905090810190601f1680156103d35780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156103ec57600080fd5b610421600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610aee565b604051808215151515815260200191505060405180910390f35b341561044657600080fd5b6104be600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001909190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610c57565b604051808215151515815260200191505060405180910390f35b34156104e357600080fd5b61052e600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610ef4565b6040518082815260200191505060405180910390f35b60038054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105da5780601f106105af576101008083540402835291602001916105da565b820191906000526020600020905b8154815290600101906020018083116105bd57829003601f168201915b505050505081565b600081600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a36001905092915050565b60005481565b600081600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101580156107a7575081600260008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410155b80156107b35750600082115b1561094a5781600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555081600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600260008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a36001905061094f565b600090505b9392505050565b600460009054906101000a900460ff1681565b60068054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109ff5780601f106109d4576101008083540402835291602001916109ff565b820191906000526020600020905b8154815290600101906020018083116109e257829003601f168201915b505050505081565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b60058054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ae65780601f10610abb57610100808354040283529160200191610ae6565b820191906000526020600020905b815481529060010190602001808311610ac957829003601f168201915b505050505081565b600081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410158015610b3f5750600082115b15610c4c5781600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a360019050610c51565b600090505b92915050565b600082600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925856040518082815260200191505060405180910390a38373ffffffffffffffffffffffffffffffffffffffff1660405180807f72656365697665417070726f76616c28616464726573732c75696e743235362c81526020017f616464726573732c627974657329000000000000000000000000000000000000815250602e01905060405180910390207c01000000000000000000000000000000000000000000000000000000009004338530866040518563ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828051906020019080838360005b83811015610e98578082015181840152602081019050610e7d565b50505050905090810190601f168015610ec55780820380516001836020036101000a031916815260200191505b509450505050506000604051808303816000875af1925050501515610ee957600080fd5b600190509392505050565b6000600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050929150505600a165627a7a7230582052a395fb68273af6db23c54c1a0307771a3f2f808bcefc81d072bfe26dc9f69b002900000000000000000000000000000000000000000000021e19e0c9bab23fffff0000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000001200000000000000000000000000000000000000000000000000000000000000c00000000000000000000000000000000000000000000000000000000000000007596577656e64610000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000035957440000000000000000000000000000000000000000000000000000000000";
    // MVC
//    private static final String BINARY = "60606040526040805190810160405280600481526020017f48302e3100000000000000000000000000000000000000000000000000000000815250600690805190602001906200005192919062000139565b5034156200005e57600080fd5b6040516200119f3803806200119f8339810160405280805190602001909190805182019190602001805190602001909190805182019190505083600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550836000819055508260039080519060200190620000fa92919062000139565b5081600460006101000a81548160ff021916908360ff16021790555080600590805190602001906200012e92919062000139565b5050505050620001e8565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200017c57805160ff1916838001178555620001ad565b82800160010185558215620001ad579182015b82811115620001ac5782518255916020019190600101906200018f565b5b509050620001bc9190620001c0565b5090565b620001e591905b80821115620001e1576000816000905550600101620001c7565b5090565b90565b610fa780620001f86000396000f3006060604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde03146100bf578063095ea7b31461014d57806318160ddd146101a757806323b872dd146101d0578063313ce5671461024957806354fd4d501461027857806370a082311461030657806395d89b4114610353578063a9059cbb146103e1578063cae9ca511461043b578063dd62ed3e146104d8575b34156100ba57600080fd5b600080fd5b34156100ca57600080fd5b6100d2610544565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101125780820151818401526020810190506100f7565b50505050905090810190601f16801561013f5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561015857600080fd5b61018d600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506105e2565b604051808215151515815260200191505060405180910390f35b34156101b257600080fd5b6101ba6106d4565b6040518082815260200191505060405180910390f35b34156101db57600080fd5b61022f600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506106da565b604051808215151515815260200191505060405180910390f35b341561025457600080fd5b61025c610956565b604051808260ff1660ff16815260200191505060405180910390f35b341561028357600080fd5b61028b610969565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102cb5780820151818401526020810190506102b0565b50505050905090810190601f1680156102f85780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561031157600080fd5b61033d600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610a07565b6040518082815260200191505060405180910390f35b341561035e57600080fd5b610366610a50565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103a657808201518184015260208101905061038b565b50505050905090810190601f1680156103d35780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156103ec57600080fd5b610421600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610aee565b604051808215151515815260200191505060405180910390f35b341561044657600080fd5b6104be600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001909190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610c57565b604051808215151515815260200191505060405180910390f35b34156104e357600080fd5b61052e600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610ef4565b6040518082815260200191505060405180910390f35b60038054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105da5780601f106105af576101008083540402835291602001916105da565b820191906000526020600020905b8154815290600101906020018083116105bd57829003601f168201915b505050505081565b600081600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a36001905092915050565b60005481565b600081600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101580156107a7575081600260008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410155b80156107b35750600082115b1561094a5781600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555081600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600260008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a36001905061094f565b600090505b9392505050565b600460009054906101000a900460ff1681565b60068054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109ff5780601f106109d4576101008083540402835291602001916109ff565b820191906000526020600020905b8154815290600101906020018083116109e257829003601f168201915b505050505081565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b60058054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ae65780601f10610abb57610100808354040283529160200191610ae6565b820191906000526020600020905b815481529060010190602001808311610ac957829003601f168201915b505050505081565b600081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410158015610b3f5750600082115b15610c4c5781600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a360019050610c51565b600090505b92915050565b600082600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925856040518082815260200191505060405180910390a38373ffffffffffffffffffffffffffffffffffffffff1660405180807f72656365697665417070726f76616c28616464726573732c75696e743235362c81526020017f616464726573732c627974657329000000000000000000000000000000000000815250602e01905060405180910390207c01000000000000000000000000000000000000000000000000000000009004338530866040518563ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828051906020019080838360005b83811015610e98578082015181840152602081019050610e7d565b50505050905090810190601f168015610ec55780820380516001836020036101000a031916815260200191505b509450505050506000604051808303816000875af1925050501515610ee957600080fd5b600190509392505050565b6000600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050929150505600a165627a7a7230582052a395fb68273af6db23c54c1a0307771a3f2f808bcefc81d072bfe26dc9f69b002900000000000000000000000000000000000000000000021e19e0c9bab23fffff0000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000001200000000000000000000000000000000000000000000000000000000000000c00000000000000000000000000000000000000000000000000000000000000007596577656e64610000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000035957440000000000000000000000000000000000000000000000000000000000";
    // default
//    private static final String BINARY = "606060405260408051908101604052600481527f48302e31000000000000000000000000000000000000000000000000000000006020820152600690805161004b9291602001906100e7565b50341561005757600080fd5b6040516109fa3803806109fa833981016040528080519190602001805182019190602001805191906020018051600160a060020a0333166000908152600160205260408120879055869055909101905060038380516100ba9291602001906100e7565b506004805460ff191660ff841617905560058180516100dd9291602001906100e7565b5050505050610182565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061012857805160ff1916838001178555610155565b82800160010185558215610155579182015b8281111561015557825182559160200191906001019061013a565b50610161929150610165565b5090565b61017f91905b80821115610161576000815560010161016b565b90565b610869806101916000396000f3006060604052600436106100955763ffffffff60e060020a60003504166306fdde0381146100a5578063095ea7b31461012f57806318160ddd1461016557806323b872dd1461018a578063313ce567146101b257806354fd4d50146101db57806370a08231146101ee57806395d89b411461020d578063a9059cbb14610220578063cae9ca5114610242578063dd62ed3e146102a7575b34156100a057600080fd5b600080fd5b34156100b057600080fd5b6100b86102cc565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156100f45780820151838201526020016100dc565b50505050905090810190601f1680156101215780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561013a57600080fd5b610151600160a060020a036004351660243561036a565b604051901515815260200160405180910390f35b341561017057600080fd5b6101786103d7565b60405190815260200160405180910390f35b341561019557600080fd5b610151600160a060020a03600435811690602435166044356103dd565b34156101bd57600080fd5b6101c56104d5565b60405160ff909116815260200160405180910390f35b34156101e657600080fd5b6100b86104de565b34156101f957600080fd5b610178600160a060020a0360043516610549565b341561021857600080fd5b6100b8610564565b341561022b57600080fd5b610151600160a060020a03600435166024356105cf565b341561024d57600080fd5b61015160048035600160a060020a03169060248035919060649060443590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965061067295505050505050565b34156102b257600080fd5b610178600160a060020a0360043581169060243516610812565b60038054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103625780601f1061033757610100808354040283529160200191610362565b820191906000526020600020905b81548152906001019060200180831161034557829003601f168201915b505050505081565b600160a060020a03338116600081815260026020908152604080832094871680845294909152808220859055909291907f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259085905190815260200160405180910390a35060015b92915050565b60005481565b600160a060020a03831660009081526001602052604081205482901080159061042d5750600160a060020a0380851660009081526002602090815260408083203390941683529290522054829010155b80156104395750600082115b156104ca57600160a060020a03808416600081815260016020908152604080832080548801905588851680845281842080548990039055600283528184203390961684529490915290819020805486900390559091907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9085905190815260200160405180910390a35060016104ce565b5060005b9392505050565b60045460ff1681565b60068054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103625780601f1061033757610100808354040283529160200191610362565b600160a060020a031660009081526001602052604090205490565b60058054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103625780601f1061033757610100808354040283529160200191610362565b600160a060020a0333166000908152600160205260408120548290108015906105f85750600082115b1561066a57600160a060020a033381166000818152600160205260408082208054879003905592861680825290839020805486019055917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9085905190815260200160405180910390a35060016103d1565b5060006103d1565b600160a060020a03338116600081815260026020908152604080832094881680845294909152808220869055909291907f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259086905190815260200160405180910390a383600160a060020a03166040517f72656365697665417070726f76616c28616464726573732c75696e743235362c81527f616464726573732c6279746573290000000000000000000000000000000000006020820152602e01604051809103902060e060020a9004338530866040518563ffffffff1660e060020a0281526004018085600160a060020a0316600160a060020a0316815260200184815260200183600160a060020a0316600160a060020a03168152602001828051906020019080838360005b838110156107b357808201518382015260200161079b565b50505050905090810190601f1680156107e05780820380516001836020036101000a031916815260200191505b5094505050505060006040518083038160008761646e5a03f192505050151561080857600080fd5b5060019392505050565b600160a060020a039182166000908152600260209081526040808320939094168252919091522054905600a165627a7a72305820157a6daf37d8e05e02039139656c677b7fa63b9f122b307e5d1c2af64f73f5dc0029";

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    protected HumanStandardToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HumanStandardToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventObservable(filter);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventObservable(filter);
    }

    public RemoteCall<String> name() {
        final Function function = new Function("name",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String _spender, BigInteger _value) {
        final Function function = new Function(
                "approve",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_spender),
                        new org.web3j.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function("totalSupply",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                "transferFrom",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from),
                        new org.web3j.abi.datatypes.Address(_to),
                        new org.web3j.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> decimals() {
        final Function function = new Function("decimals",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> version() {
        final Function function = new Function("version",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function("balanceOf",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function("symbol",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                "transfer",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to),
                        new org.web3j.abi.datatypes.generated.Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> approveAndCall(String _spender, BigInteger _value, byte[] _extraData) {
        final Function function = new Function(
                "approveAndCall",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_spender),
                        new org.web3j.abi.datatypes.generated.Uint256(_value),
                        new org.web3j.abi.datatypes.DynamicBytes(_extraData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> allowance(String _owner, String _spender) {
        final Function function = new Function("allowance",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner),
                        new org.web3j.abi.datatypes.Address(_spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<HumanStandardToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _initialAmount, String _tokenName, BigInteger _decimalUnits, String _tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_initialAmount),
                new org.web3j.abi.datatypes.Utf8String(_tokenName),
                new org.web3j.abi.datatypes.generated.Uint8(_decimalUnits),
                new org.web3j.abi.datatypes.Utf8String(_tokenSymbol)));
        return deployRemoteCall(HumanStandardToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<HumanStandardToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _initialAmount, String _tokenName, BigInteger _decimalUnits, String _tokenSymbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_initialAmount),
                new org.web3j.abi.datatypes.Utf8String(_tokenName),
                new org.web3j.abi.datatypes.generated.Uint8(_decimalUnits),
                new org.web3j.abi.datatypes.Utf8String(_tokenSymbol)));
        return deployRemoteCall(HumanStandardToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static HumanStandardToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HumanStandardToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static HumanStandardToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HumanStandardToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TransferEventResponse {
        public Log log;

        public String _from;

        public String _to;

        public BigInteger _value;
    }

    public static class ApprovalEventResponse {
        public Log log;

        public String _owner;

        public String _spender;

        public BigInteger _value;
    }
}
