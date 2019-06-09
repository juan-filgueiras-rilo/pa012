import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage, FormattedNumber, FormattedDate} from 'react-intl';

import users from '../../users';
import * as selectors from '../selectors';
import * as actions from '../actions';
import {BidForm} from '../../auction';
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

                        <h6 className="card-subtitle"></h6>
                            <strong><FormattedMessage id='project.global.fields.userName'/></strong>: {product.userName}
                        <h6 className="card-subtitle"></h6>
                            <strong><FormattedMessage id='project.global.fields.creationTime'/></strong>: {new Date(product.creationTime).toLocaleString()}
                        <h6 className="card-subtitle"></h6>
                            <strong><FormattedMessage id='project.global.fields.remainingTime'/></strong>: {product.remainingTime} min.
                        <h6 className="card-subtitle"></h6>  
                        <strong><FormattedMessage id='project.global.fields.initialPrice'/></strong>: <FormattedNumber value={product.initialPrice}/>€                                                       
                        <p className="card-text">
                            <strong><FormattedMessage id='project.global.fields.currentPrice'/></strong>: <strong><FormattedNumber value={product.currentPrice}/>€</strong>
                        <p className="card-subtitle">{product.shipmentInfo}</p>
                        </p>
                    </div>
                </div>
                {this.props.loggedIn
                    && this.props.userName != this.props.product.userName
                    && product.remainingTime > 0
                    &&
                    <div>
                        <br/>
                        <BidForm productId={product.id} 
                            minPrice={product.minPrice} 
                            history={this.props.history}/>
                    </div>
                }
            </div>

        );

    }

}

const mapStateToProps = (state) => ({
    loggedIn: users.selectors.isLoggedIn(state),
    userName: users.selectors.getUserName(state),
    product: selectors.getProduct(state),
    categories: selectors.getCategories(state),
});

const mapDispatchToProps = {
    findProductById: actions.findProductById,
    clearProduct: actions.clearProduct
}

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetails);