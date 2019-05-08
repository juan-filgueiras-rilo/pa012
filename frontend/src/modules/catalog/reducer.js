import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    categories: null,
    productSearch: null,
    product: null,
    addedProductId: null
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

const reducer = combineReducers({
    categories,
    productSearch,
    product,
    addedProductId
});

export default reducer;