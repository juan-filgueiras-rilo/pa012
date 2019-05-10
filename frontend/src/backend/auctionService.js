import {config, appFetch} from './appFetch';

export const createBid = (productId, quantity, onSuccess, onErrors) =>
    appFetch(`/auction/bids`, config('POST',{productId,quantity}), onSuccess, onErrors);

export const getUserBids = (page, onSuccess) => 
    appFetch(`/auction/bids?page=${page}`, config('GET'), onSuccess);