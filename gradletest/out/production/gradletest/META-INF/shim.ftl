'use strict';

const shim = require('fabric-shim');

const ${chaincodeName} = class {

    // Initialize the chaincode
    async Init(stub) {
        // TODO: write chaincode initiation here.
    }

    async Invoke(stub) {
        // TODO: write chaincode invocation logic here.
    }

    // TODO: provide CRUD and other APIs.

};

shim.start(new ${chaincodeName}());