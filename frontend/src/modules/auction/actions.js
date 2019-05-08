import * as actionTypes from './actionTypes';
import * as selectors from './selectors';
import backend from '../../backend';

const bidCompleted = (remainingTime, currentPrice, minPrice) => ({
    type: actionTypes.BID_COMPLETED,
    remainingTime,
    currentPrice,
    minPrice
});

export const bid = (productId, quantity,
    onSuccess, onErrors) => dispatch =>
    backend.auctionService.createBid(productId, quantity,
        ({remainingTime, currentPrice, minPrice, bidStatus}) => 
        {
            dispatch(bidCompleted(remainingTime, currentPrice, minPrice));
            onSuccess(bidStatus);
        },
    onErrors
);
