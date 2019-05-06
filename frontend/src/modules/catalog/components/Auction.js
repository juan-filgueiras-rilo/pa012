import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

const Auction = ({history}) => (

    <div className="text-center">
        <button type="button" className="btn btn-primary"
            onClick={() => history.push('/catalog/add-product')}>
            <FormattedMessage id="project.global.buttons.addProduct"/>
        </button>
    </div>
);

export default connect()(Auction);