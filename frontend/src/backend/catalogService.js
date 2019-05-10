import {config, appFetch} from './appFetch';

export const findAllCategories = (onSuccess) => 
    appFetch('/catalog/categories', config('GET'), onSuccess);

export const findProducts = ({categoryId, keywords, page}, 
    onSuccess) => {

    let path = `/catalog/products?page=${page}`;

    path += categoryId ? `&categoryId=${categoryId}` : "";
    path += keywords.length > 0 ? `&keywords=${keywords}` : "";

    appFetch(path, config('GET'), onSuccess);

}

export const findByProductId = (id, onSuccess) => 
    appFetch(`/catalog/products/${id}`, config('GET'), onSuccess);

export const addProduct = (name, description, duration, 
    initialPrice, shipmentInfo, categoryId, onSuccess, onErrors) =>
    appFetch(`/catalog/products`, config('POST',{name, description,
        duration, initialPrice, shipmentInfo, categoryId}), onSuccess, onErrors);

export const getUserProducts = (page, onSuccess) => 
        appFetch(`/catalog/userProducts?page=${page}`, config('GET'), onSuccess);
          