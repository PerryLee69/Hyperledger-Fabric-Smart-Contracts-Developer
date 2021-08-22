const { ${contractName} } = require('../../main/js/lib/${contractName}');
const { ChaincodeMockStub, Transform } = require("@theledger/fabric-mock-stub")
const { expect } = require('chai')

// get our chaincode
const chaincode = new ${contractName}();

// unit test
describe('Test ${contractName}', function() {
    var mockStub;

    beforeEach(function(){
        mockStub = new ChaincodeMockStub("MyMockStub", chaincode);
        mockStub.mockTransactionStart();
    })

    <#list chaincodeApis as api>
    describe('Test ${contractName}.${api.name}', () => {
        it("Call api: ${api.name}", async () => {
            const result = await chaincode.${api.name}({stub:mockStub}, ${api.args});
        });
    });
    </#list>

});