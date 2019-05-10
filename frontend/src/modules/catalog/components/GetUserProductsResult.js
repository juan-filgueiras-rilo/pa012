import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {withRouter} from 'react-router-dom';

import * as selectors from '../selectors';
import * as actions from '../actions';
import {Pager} from '../../common';
import UserProducts from './UserProducts';

const GetUserProductsResult = ({userProducts, previousGetUserProductsResultPage, nextGetUserProductsResultPage}) => {

    if (!userProducts) {
        return null;
    }

    if (userProducts.result.items.length === 0) {
        return (
            <div className="alert alert-danger" role="alert">
                <FormattedMessage id='project.catalog.GetUserProductsResult.noUserProducts'/>
            </div>
        );
    }

    return (

        <div>
            <UserProducts userProducts={userProducts.result.items}/>
            <Pager 
                back={{
                    enabled: userProducts.page >= 1,
                    onClick: () => previousGetUserProductsResultPage(userProducts.page)
                    
                }}
                next={{
                    enabled: userProducts.result.existMoreItems,
                    onClick: () => nextGetUserProductsResultPage(userProducts.page)

                }}/>
        </div>

    );
}

const mapStateToProps = (state) => ({
    userProducts: selectors.getUserProducts(state)
});

const mapDispatchToProps = {
    getUserProductsResult: actions.getUserProducts,
    previousGetUserProductsResultPage: actions.previousGetUserProductsPage,
    nextGetUserProductsResultPage: actions.nextGetUserProductsPage
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(GetUserProductsResult));