import {config, appFetch} from './appFetch';

export const createBid = (productId, quantity, onSuccess, onErrors) =>
    appFetch(`/auction/bids`, config('POST',{productId,quantity}), onSuccess, onErrors);