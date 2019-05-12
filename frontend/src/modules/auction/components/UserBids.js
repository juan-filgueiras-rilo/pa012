import React from 'react';
import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

import {ProductLink} from '../../common';

const UserBids = ({userBids}) => (

    <table className="table table-striped table-hover">

        <thead>
            <tr>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.date'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.name'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.quantity'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.bidStatus'/>
                </th>
            </tr>
        </thead>

        <tbody>
            {userBids.map((bid, index) => 
                <tr key={index}>
                    <td>{new Date(bid.date).toLocaleString()}}</td>
                    <td><ProductLink id={bid.productId} name={bid.productName}/></td>
                    <td>{bid.quantity}</td>
                    <td>{bid.bidStatus}</td>
                </tr>
            )}
        </tbody>

    </table>

);

UserBids.propTypes = {
    userBids: PropTypes.array.isRequired,
};

export default UserBids;