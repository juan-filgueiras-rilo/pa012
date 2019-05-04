import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';

const CategorySelector = ({categories, dispatch, ...remainingProps} ) => (

    <select {...remainingProps}>

        <FormattedMessage id='project.catalog.CategorySelector.allDepartments'>
            {message => (<option value="">{message}</option>)}
        </FormattedMessage>

        {categories && categories.map(category => 
            <option key={category.id} value={category.id}>{category.name}</option>
        )}

    </select>

);

const mapStateToProps = state => ({
    categories: selectors.getCategories(state)
});

export default connect(mapStateToProps)(CategorySelector);
