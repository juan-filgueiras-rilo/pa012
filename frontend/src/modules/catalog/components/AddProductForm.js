import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

import {Errors} from '../../common';
import CategorySelector from './CategorySelector';
import * as actions from '../actions';

const initialState = {
    name: '',
    description: '',
    duration: '',
    initialPrice: '',
    shipmentInfo: '',
    categoryId: '',

    backendErrors: null,
};

class AddProductForm extends React.Component {

    constructor(props) {

        super(props);

        this.state = initialState;

    }

    handleNameChange(event) {
        this.setState({name: event.target.value});
    }

    handleDescriptionChange(event) {
        this.setState({description: event.target.value});
    }

    handleDurationChange(event) {
        this.setState({duration: event.target.value});
    }

    handleInitialPriceChange(event) {
        this.setState({initialPrice: event.target.value});
    }

    handleShipmentInfoChange(event) {
        this.setState({shipmentInfo: event.target.value});
    }

    handleCategoryIdChange(event) {
        this.setState({categoryId: event.target.value});
    }

    toNumber(value) {
        return value.length > 0 ? Number(value) : null;
    }

    handleSubmit(event) {

        event.preventDefault();

        if (this.form.checkValidity()) {
            this.addProduct();
        } else {
            this.setBackendErrors(null);
            this.form.classList.add('was-validated');
        }

    }

    addProduct() {

        this.props.addProduct(this.state.name.trim(),
            this.state.description.trim(), this.state.duration, 
            this.state.initialPrice, this.state.shipmentInfo.trim(), 
            this.state.categoryId,
        
            () => this.props.history.push('/catalog/product-added'),

            errors => this.setBackendErrors(errors));

    }

    setBackendErrors(backendErrors) {
        this.setState({backendErrors});
    }

    handleErrorsClose() {
        this.setState({backendErrors: null});
    }

    render () {

        return (

            <div>
                <Errors errors={this.state.backendErrors}
                    onClose={ () => this.handleErrorsClose()}/>

                <div className="card bg-light border-dark">
                    <h5 className="card-header">
                        <FormattedMessage id="project.shopping.AddProductForm.title"/>
                    </h5>
                    <div className="card-body">
                        <form ref={node => this.form = node}
                                className="needs-validation" noValidate 
                                onSubmit={(e) => this.handleSubmit(e)}>
                            <div className="form-group row">
                                <label htmlFor="name" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.global.fields.name"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="text" id="name" className="form-control"
                                        value={this.state.name}
                                        onChange={(e) => this.handleNameChange(e)}
                                        autoFocus
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="description" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.global.fields.description"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="text" id="description" className="form-control"
                                        value={this.state.description}
                                        onChange={(e) => this.handleDescriptionChange(e)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="duration" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.global.fields.duration"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="number" id="duration" className="form-control"
                                        value={this.state.duration}
                                        onChange={(e) => this.handleDurationChange(e)}
                                        required min="1"/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="initialPrice" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.global.fields.initialPrice"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="number" id="initialPrice" className="form-control"
                                        value={this.state.initialPrice}
                                        onChange={(e) => this.handleInitialPriceChange(e)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="ShipmentInfo" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.global.fields.shipmentInfo"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="text" id="shipmentInfo" className="form-control"
                                        value={this.state.shipmentInfo}
                                        onChange={(e) => this.handleShipmentInfoChange(e)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="categoryId" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.global.fields.department"/>
                                </label>
                                <div className="col-md-4">
                                    <CategorySelector id="categoryId" className="custom-select my-1 mr-sm-2"
                                        value={this.state.categoryId} onChange={e => this.handleCategoryIdChange(e)}/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <div className="offset-md-3 col-md-1">
                                    <button type="submit" className="btn btn-primary">
                                        <FormattedMessage id="project.global.buttons.addProduct"/>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );

    }

}

const mapDispatchToProps = {
    addProduct: actions.addProduct
}

AddProductForm = connect(state => {}, mapDispatchToProps)(AddProductForm);

AddProductForm.propTypes = {
    history: PropTypes.object.isRequired
};

export default AddProductForm;
