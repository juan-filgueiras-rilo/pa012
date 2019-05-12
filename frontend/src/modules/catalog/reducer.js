import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';
import * as auctionActionTypes from '../auction/actionTypes';

const initialState = {
    categories: null,
    productSearch: null,
    product: null,
    addedProductId: null,
    userProducts: null,
};

const categories = (state = initialState.categories, action) => {

    switch (action.type) {

        case actionTypes.FIND_ALL_CATEGORIES_COMPLETED:
            return action.categories;

        default:
            return state;

    }

}

const productSearch = (state = initialState.productSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_PRODUCTS_COMPLETED:
            return action.productSearch;

        case actionTypes.CLEAR_PRODUCT_SEARCH:
            return initialState.productSearch;

        default:
            return state;

    }

}

const product = (state = initialState.product, action) => {

    switch (action.type) {

        case actionTypes.FIND_PRODUCT_BY_ID_COMPLETED:
            return action.product;

        case actionTypes.CLEAR_PRODUCT:
            return initialState.product;

        case auctionActionTypes.BID_COMPLETED:
//            return {...state, currentPrice: action.currentPrice}    
            return {...state, ...action};
        
        default:
            return state;

    }

}

const addedProductId = (state = initialState.addedProductId, action) => {

    switch (action.type) {
        case actionTypes.ADD_PRODUCT_COMPLETED:
            return action.productId;
        
        default:
            return state;
    }
}

const userProducts = (state = initialState.userProducts, action) => {

    switch (action.type) {

        case actionTypes.GET_USER_PRODUCTS_COMPLETED:
            return action.userProducts;

        default:
            return state;

    }

}



const reducer = combineReducers({
    categories,
    productSearch,
    product,
    addedProductId,
    userProducts,
});

export default reducer;