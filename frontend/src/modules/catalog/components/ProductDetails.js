import React from 'react';
import {connect} from 'react-redux';
import {Link} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

class ProductDetails extends React.Component {

    componentDidMount() {
        this.props.findProductById(this.props.match.params.id);
    }

    render() {

        const product = this.props.product;

        if (!product) {
            return null;
        }

        return (

            <div>
                {this.props.match.params.withBackLink === 'withBackLink' &&
                <p>
                    <Link to="/catalog/find-products-result">
                        <FormattedMessage id='project.catalog.ProductDetails.backToSearchResults'/>
                    </Link>
                </p>
                }
                <div className="card text-center">
                    <div className="card-body">
                        <h5 className="card-title">{product.name}</h5>
                        <h6 className="card-subtitle text-muted">
                            <FormattedMessage id='project.global.fields.department'/>:&nbsp;
                                {selectors.getCategory(this.props.categories, product.categoryId).name}
                        </h6>
                        <p className="card-text">{product.description}</p>
                        <p className="card-text">
                            <strong><FormattedMessage id='project.global.fields.price'/></strong>: {product.price}€
                        </p>
                    </div>
                </div>
                
            </div>

        );

    }

}