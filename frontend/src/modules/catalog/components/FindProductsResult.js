import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
import * as actions from '../actions';
import {Pager} from '../../common';
import Products from './Products';

const FindProductsResult = ({productSearch, categories, previousFindProductsResultPage, nextFindProductsResultPage}) => {

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
                    handle: () => previousFindProductsResultPage(productSearch.criteria)}}
                next={{
                    enabled: productSearch.result.existMoreItems,
                    handle: () => nextFindProductsResultPage(productSearch.criteria)}}/>
        </div>

    );

}

const mapStateToProps = (state) => ({
    productSearch: selectors.getProductSearch(state),
    categories: selectors.getCategories(state)
});

const mapDispatchToProps = {
    previousFindProductsResultPage: actions.previousFindProductsResultPage,
    nextFindProductsResultPage: actions.nextFindProductsResultPage
}

export default connect(mapStateToProps, mapDispatchToProps)(FindProductsResult);