con


const { Contract } = require('fabric-contract-api');

class SmartContract extends Contract {
    constructor() {
        super();
    }

    async queryCar(ctx, carNumber) {
        const carAsBytes = await ctx.stub.getState(carNumber); // get the car from chaincode state
        if (!carAsBytes || carAsBytes.length === 0) {
            throw new Error(`${carNumber} does not exist`);
        }
        stub
        console.log(carAsBytes.toString());
        return carAsBytes.toString();
    }
}