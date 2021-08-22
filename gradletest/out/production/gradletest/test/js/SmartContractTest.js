const { SmartContract } = require('../../main/js/lib/SmartContract');
const { ChaincodeMockStub, Transform } = require("@theledger/fabric-mock-stub")
//const { expect } = require('chai')

// get our chaincode
const chaincode = new SmartContract();

// unit test
describe('Test SmartContract', function() {
    var mockStub;

    beforeEach(function(){
        mockStub = new ChaincodeMockStub("MyMockStub", chaincode);
        mockStub.mockTransactionStart();
    })

    describe('Test SmartContract.initLedger', () => {
        it("Call api: initLedger", async () => {
            const result = await chaincode.initLedger({stub:mockStub}, []);
        });
    });
    describe('Test SmartContract.createCar', () => {
        it("Call api: createCar", async () => {
            const result = await chaincode.createCar({stub:mockStub}, '123','Holden','Barina','brown');
        });
    });

});