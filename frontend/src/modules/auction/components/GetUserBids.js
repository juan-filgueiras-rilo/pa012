import React from 'react';
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

import * as actions from '../actions';

class GetUserBids extends React.Component {

    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(event) {
        event.preventDefault();
        this.props.dispatch(actions.getUserBids(0));
        this.props.history.push('/auction/get-user-bids');
    }

    render () {

        return (
            <button type="button" className="btn btn-primary my-2 my-sm-0"
                onClick={(e) => this.handleClick(e)}>
                <FormattedMessage id='project.auction.GetUserBids.GetUserBids'/>
            </button>
        );

    }

}

export default withRouter(connect()(GetUserBids));
