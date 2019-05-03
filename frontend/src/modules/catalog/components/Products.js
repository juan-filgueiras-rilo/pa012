import React from 'react';
import {Link} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

import * as selectors from '../selectors';

const Products = ({products, categories}) => (

    <table className="table table-striped table-hover">

        <thead>
            <tr>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.department'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.name'/>
                </th>
            </tr>
        </thead>

        <tbody>
            {products.map((product, index) => 
                <tr key={index}>
                    <td>{selectors.getCategory(categories, product.categoryId).name}</td>
                    <td><Link to={`/catalog/product-details/${product.id}/withBackLink`}>{product.name}</Link></td>
                </tr>
            )}
        </tbody>

    </table>

);

Products.propTypes = {
    products: PropTypes.array.isRequired,
    categories: PropTypes.array.isRequired
};

export default Products;