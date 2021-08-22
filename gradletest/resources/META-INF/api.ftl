'use strict';

const { Contract } = require('fabric-contract-api');

class ${chaincodeName} extends Contract {

    constructor(){
        super('${chaincodeName}');
    }

    // Initialize the chaincode
    async InitLedger(ctx) {
        // TODO: write chaincode initiation here.
    }

    // TODO: provide CRUD and other APIs.

}

module.exports = ${chaincodeName};