const getModuleState = state => state.catalog;

export const getCategories = state => 
    getModuleState(state).categories;

export const getCategory = (categories, id) =>
    categories.find(category => category.id === id);

export const getProductSearch = state =>
    getModuleState(state).productSearch;
