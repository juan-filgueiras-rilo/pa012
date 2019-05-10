import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import catalog from '../modules/catalog';
import auction from '../modules/auction';

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    catalog: catalog.reducer,
    auction: auction.reducer
});

export default rootReducer;
