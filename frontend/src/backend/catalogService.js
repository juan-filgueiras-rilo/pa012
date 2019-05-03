import {config, appFetch} from './appFetch';

export const findAllCategories = (onSuccess) => 
    appFetch('/catalog/categories', config('GET', null), onSuccess);

export const findProducts = ({categoryId, keywords, page}, 
    onSuccess) => 
    appFetch(`/catalog/products?categoryId=${categoryId}&keywords=${keywords}&` +
        `page=${page}`, config('GET', null), onSuccess);
