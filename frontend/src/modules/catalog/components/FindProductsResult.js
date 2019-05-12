import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
import * as actions from '../actions';
import {Pager, BackLink} from '../../common';
import Products from './Products';

const initialState = {
    query: '',
}

class FindProductsResult extends React.Component {

    constructor(props) {

        super(props);

        this.state = initialState;
    }

    toNumber(value) {
        return value.length > 0 ? Number(value) : null;
    }

    parseAndFind(search) {

        const params = new URLSearchParams(search);
        
        this.props.findProducts(
            {categoryId: this.toNumber(params.get('categoryId')), 
                keywords: params.get('keywords').trim(), page: params.get('page')},this.props.dispatch);
    }  

    componentDidMount() {
        this.parseAndFind(this.props.location.search);
    }
    
    componentWillReceiveProps(nextProps) {
        if (nextProps.location.search !== this.props.location.search) {
            this.parseAndFind(nextProps.location.search);
        }
    }

    render() {

        if (!this.props.productSearch) {
            return null;
        }

        if (this.props.productSearch.result.items.length === 0) {
            return (
                <div className="alert alert-danger" role="alert">
                    <FormattedMessage id='project.catalog.FindProductsResult.noProductsFound'/>
                </div>
            );
        }

        return (
            
            <div>
                <BackLink/>
                <Products products={this.props.productSearch.result.items} categories={this.props.categories}/>
                <Pager 
                    back={{
                        enabled: this.props.productSearch.criteria.page >= 1,
                        onClick: () => {
                            const newParams = new URLSearchParams(this.props.location.search);
                            newParams.set('page', this.toNumber(newParams.get('page'))-1);
                            this.props.history.push(`/catalog/find-products/search?`+newParams);
                        }}}
                    next={{
                        enabled: this.props.productSearch.result.existMoreItems,
                        onClick: () => {
                            const newParams = new URLSearchParams(this.props.location.search);
                            newParams.set('page', this.toNumber(newParams.get('page'))+1);
                            this.props.history.push(`/catalog/find-products/search?`+newParams);
                        }}}/>
            </div>

        );

    }
}

const mapStateToProps = (state) => ({
    productSearch: selectors.getProductSearch(state),
    categories: selectors.getCategories(state)
});

const mapDispatchToProps = {
    findProducts: actions.findProducts,
}

export default connect(mapStateToProps, mapDispatchToProps)(FindProductsResult);