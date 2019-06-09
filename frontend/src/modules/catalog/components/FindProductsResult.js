import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
import {Pager} from '../../common';
import Products from './Products';

export const FindProductsResult = ({productSearch, categories, history, search}) => {

    if (!productSearch) {
        return null;
    }

    if (productSearch.result.items.length === 0) {
        return (
            <div className="alert alert-danger" role="alert">
                <FormattedMessage id='project.catalog.FindProductsResult.noProductsFound'/>
            </div>
        );
    }

    return (
        
        <div>
            <Products products={productSearch.result.items} categories={categories}/>
            <Pager 
                back={{
                    enabled: productSearch.criteria.page >= 1,
                    onClick: () => {
                        const newParams = new URLSearchParams(search);
                        newParams.set('page', Number(newParams.get('page'))-1);
                        history.push(`/catalog/find-products/search?`+newParams);
                    }
                }}
                next={{
                    enabled: productSearch.result.existMoreItems,
                    onClick: () => {
                        const newParams = new URLSearchParams(search);
                        newParams.set('page', Number(newParams.get('page'))+1);
                        history.push(`/catalog/find-products/search?`+newParams);
                    }
                }}
            />
        </div>

    );

}

const mapStateToProps = (state) => ({
    productSearch: selectors.getProductSearch(state),
    categories: selectors.getCategories(state)
});

export default connect(mapStateToProps)(FindProductsResult);