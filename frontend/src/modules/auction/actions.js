import * as actionTypes from './actionTypes';
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

const getUserBidsCompleted = userBids => ({
    type: actionTypes.GET_USER_BIDS_COMPLETED,
    userBids
});

export const getUserBids = page => dispatch => {

    backend.auctionService.getUserBids(page,
        result => dispatch(getUserBidsCompleted({page, result})));
}

export const previousGetUserBidsPage = page =>
    getUserBids(page-1);

export const nextGetUserBidsPage = page =>
    getUserBids(page+1);
