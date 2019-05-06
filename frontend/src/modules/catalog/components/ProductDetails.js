import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage, FormattedNumber} from 'react-intl';

import users from '../../users';
import * as selectors from '../selectors';
import * as actions from '../actions';
import {BackLink} from '../../common';

class ProductDetails extends React.Component {

    componentDidMount() {

        const id = Number(this.props.match.params.id);

        if (!Number.isNaN(id)) {
            this.props.findProductById(id);
        }
    
    }

    componentWillUnmount() {
        this.props.clearProduct();
    }

    render() {

        const product = this.props.product;

        if (!product) {
            return null;
        }

        return (

            <div>

                <BackLink/>

                <div className="card text-center">
                    <div className="card-body">
                        <h5 className="card-title">{product.name}</h5>
                        <h6 className="card-subtitle text-muted">
                            <FormattedMessage id='project.global.fields.department'/>:&nbsp;
                                {selectors.getCategoryName(this.props.categories, product.categoryId)}
                        </h6>
                        <p className="card-text">{product.description}</p>
                        <h6 className="card-subtitle">{product.userName}</h6>
                        <h6 className="card-subtitle">{new Date(product.creationTime).toString()}</h6>
                        <h6 className="card-subtitle">{product.remainingTime}</h6>
                        <h6 className="card-subtitle">{product.initialPrice}</h6>
                        <h6 className="card-subtitle">{product.currentPrice}</h6>                       
                        <h6 className="card-subtitle">{product.shipmentInfo}</h6>                       
                        <p className="card-text">
                            <strong><FormattedMessage id='project.global.fields.currentPrice'/></strong>: {product.currentPrice}€
                        </p>
                    </div>
                </div>
                
                {this.props.loggedIn && 
                    <div>
                        <br/>
                    </div>
                }
            </div>

        );

    }

}

const mapStateToProps = (state) => ({
    loggedIn: users.selectors.isLoggedIn(state),
    product: selectors.getProduct(state),
    categories: selectors.getCategories(state)
});

const mapDispatchToProps = {
    findProductById: actions.findProductById,
    clearProduct: actions.clearProduct
}

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetails);