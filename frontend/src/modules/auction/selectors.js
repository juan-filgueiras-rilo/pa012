const getModuleState = state => state.auction;

export const getUserBids = state =>
    getModuleState(state).userBids;
