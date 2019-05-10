import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    userBids: null
};

const userBids = (state = initialState.userBids, action) => {

    switch (action.type) {

        case actionTypes.GET_USER_BIDS_COMPLETED:
            return action.userBids;

        default:
            return state;

    }

}

const reducer = combineReducers({
    userBids
});

export default reducer;
