'use strict';

const { Contract } = require('fabric-contract-api');

class MySmartContract extends Contract {

    constructor(){
        super('MySmartContract');
    }

    // Initialize the chaincode
    async InitLedger(ctx) {
        // TODO: write chaincode initiation here.
    }

    // TODO: provide CRUD and other APIs.

}

module.exports = MySmartContract;