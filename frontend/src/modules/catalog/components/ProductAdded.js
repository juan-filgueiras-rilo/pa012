import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
//import ProductLink from './ProductLink';

const ProductAdded = ({addedProductId}) => addedProductId && (
    <div className="alert alert-success" role="alert">
        <FormattedMessage id="project.catalog.ProductAdded.ProductGenerated"/>:
        &nbsp;
    </div>
);

const mapStateToProps = state => ({
    addedProductId: selectors.getAddedProductId(state)
});

export default connect(mapStateToProps)(ProductAdded);