import React from 'react';
import {connect} from 'react-redux';
import { BackLink } from "../../common";
import FindProductsResult from "./FindProductsResult";
import * as selectors from '../selectors';
import * as actions from '../actions';

class FindProductsResultWrapper extends React.Component {

    toNumber(value) {
        return value.length > 0 ? Number(value) : null;
    }

    parseAndFind(search) {

        const params = new URLSearchParams(search);
        
        this.props.findProducts(
            {categoryId: this.toNumber(params.get('categoryId')), 
                keywords: params.get('keywords').trim(), page: params.get('page')});
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
        return (
            <div>
                <BackLink/>
                <FindProductsResult history={this.props.history} search={this.props.location.search}/>
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

export default connect(mapStateToProps, mapDispatchToProps)(FindProductsResultWrapper);