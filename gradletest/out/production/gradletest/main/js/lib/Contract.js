'use strict';

const { Contract } = require('fabric-contract-api');

class Contract extends Contract {

    constructor(){
        super('Contract');
    }

    // Initialize the chaincode
    async InitLedger(ctx) {
        // TODO: write chaincode initiation here.
    }

    // TODO: provide CRUD and other APIs.

}

module.exports = Contract;