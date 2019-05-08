import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

import {Errors} from '../../common';
import * as actions from '../actions';

const initialState = {
    quantity: '',
    bidStatus: null,

    backendErrors: null,
};

class BidForm extends React.Component {

    constructor(props) {

        super(props);

        this.state = initialState;
        this.state.quantity = this.props.minPrice;
    }

    handleQuantityChange(event) {
        this.setState({quantity: event.target.value});
    }

    handleSubmit(event) {

        event.preventDefault();

        if (this.form.checkValidity()) {
            this.bid();
        } else {
            this.setBackendErrors(null);
            this.setBidStatus(null);
            this.form.classList.add('was-validated');
        }
    }

    bid() {
        this.props.bid(this.props.productId, this.state.quantity,
        
            bidStatus => this.setBidStatus(bidStatus),
            
            errors => this.setBackendErrors(errors));
    }

    setBackendErrors(backendErrors) {
        this.setState({backendErrors});
        this.setBidStatus(null);
    }

    setBidStatus(bidStatus) {
        this.setState({bidStatus});
    }

    handleErrorsClose() {
        this.setState({backendErrors: null});
    }

    render () {

        return (

            <div>
                <Errors errors={this.state.backendErrors}
                    onClose={ () => this.handleErrorsClose()}/>
                {this.state.bidStatus &&
                    <div className="alert alert-success" role="alert">
                        <FormattedMessage id="project.catalog.BidForm.BidStatus"/>: {this.state.bidStatus}
                    </div>
                }
                <div className="card-body">
                    <form ref={node => this.form = node}
                            className="needs-validation" noValidate 
                            onSubmit={(e) => this.handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="quantity" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.quantity"/>
                            </label>
                            <div className="col-md-4">
                                <input type="number" id="quantity" className="form-control"
                                    value={this.state.quantity}
                                    onChange={(e) => this.handleQuantityChange(e)}
                                    autoFocus
                                    min={this.props.minPrice}
                                    required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="offset-md-3 col-md-1">
                                <button type="submit" className="btn btn-primary">
                                    <FormattedMessage id="project.global.buttons.bid"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        );

    }

}

const mapDispatchToProps = {
    bid: actions.bid
}

BidForm = connect(state => {}, mapDispatchToProps)(BidForm);

BidForm.propTypes = {
    productId: PropTypes.number.isRequired,
    minPrice: PropTypes.number.isRequired,
    history: PropTypes.object.isRequired
};

export default BidForm;
