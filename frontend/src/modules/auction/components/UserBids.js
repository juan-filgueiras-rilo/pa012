import React from 'react';
import {FormattedMessage, FormattedNumber, FormattedDate, FormattedTime} from 'react-intl';
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
                    <td><FormattedDate value={new Date(bid.date)}/> - <FormattedTime value={new Date(bid.date)}/></td>
                    <td><ProductLink id={bid.productId} name={bid.productName}/></td>
                    <td>{<FormattedNumber value={bid.quantity}/>} €</td>
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