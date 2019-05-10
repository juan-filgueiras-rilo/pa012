import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {withRouter} from 'react-router-dom';

import * as selectors from '../selectors';
import * as actions from '../actions';
import {Pager} from '../../common';
import UserBids from './UserBids';

const GetUserBidsResult = ({userBids, previousGetUserBidsResultPage, nextGetUserBidsResultPage}) => {

    if (!userBids) {
        return null;
    }

    if (userBids.result.items.length === 0) {
        return (
            <div className="alert alert-danger" role="alert">
                <FormattedMessage id='project.catalog.GetUserBidsResult.noUserBids'/>
            </div>
        );
    }

    return (

        <div>
            <UserBids userBids={userBids.result.items}/>
            <Pager 
                back={{
                    enabled: userBids.page >= 1,
                    onClick: () => previousGetUserBidsResultPage(userBids.page)
                    
                }}
                next={{
                    enabled: userBids.result.existMoreItems,
                    onClick: () => nextGetUserBidsResultPage(userBids.page)

                }}/>
        </div>

    );
}

const mapStateToProps = (state) => ({
    userBids: selectors.getUserBids(state)
});

const mapDispatchToProps = {
    getUserBidsResult: actions.getUserBids,
    previousGetUserBidsResultPage: actions.previousGetUserBidsPage,
    nextGetUserBidsResultPage: actions.nextGetUserBidsPage
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(GetUserBidsResult));