import * as actionTypes from './actionTypes';
import * as selectors from './selectors';
import backend from '../../backend';

const findAllCategoriesCompleted = categories => ({
    type: actionTypes.FIND_ALL_CATEGORIES_COMPLETED,
    categories
}); 

export const findAllCategories = () => (dispatch, getState) => {

    const categories = selectors.getCategories(getState());

    if (!categories) {

        backend.catalogService.findAllCategories(
            categories => dispatch(findAllCategoriesCompleted(categories))
        );
        
    }

}

const findProductsCompleted = productSearch => ({
    type: actionTypes.FIND_PRODUCTS_COMPLETED,
    productSearch,
});

export const findProducts = criteria => dispatch => {

    dispatch(clearProductSearch());
    backend.catalogService.findProducts(criteria,
        result => dispatch(findProductsCompleted({criteria, result})));

}

const clearProductSearch = () => ({
    type: actionTypes.CLEAR_PRODUCT_SEARCH
});

const findProductByIdCompleted = product => ({
    type: actionTypes.FIND_PRODUCT_BY_ID_COMPLETED,
    product
});
    
export const findProductById = id => dispatch => {
    backend.catalogService.findByProductId(id,
        product => dispatch(findProductByIdCompleted(product)));
}

export const clearProduct = () => ({
    type: actionTypes.CLEAR_PRODUCT
});

const addProductCompleted = (productId) => ({
    type: actionTypes.ADD_PRODUCT_COMPLETED,
    productId
});

export const addProduct = (name, description, duration, 
    initialPrice, shipmentInfo, categoryId, onSuccess, onErrors) => dispatch =>
    
    backend.catalogService.addProduct(
        name,
        description,
        duration,
        initialPrice,
        shipmentInfo,
        categoryId,
        ({id}) => {
            dispatch(addProductCompleted(id));
            onSuccess();
        },
        onErrors
    );
    
const getUserProductsCompleted = userProducts => ({
    type: actionTypes.GET_USER_PRODUCTS_COMPLETED,
    userProducts
});

export const clearUserProducts = () => ({
    type: actionTypes.CLEAR_USER_PRODUCTS
});

export const getUserProducts = page => dispatch => {

    backend.catalogService.getUserProducts(page,
        result => dispatch(getUserProductsCompleted({page, result})));
}

export const previousGetUserProductsPage = page =>
    getUserProducts(page-1);

export const nextGetUserProductsPage = page =>
    getUserProducts(page+1);
