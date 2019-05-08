import React from 'react';
import {connect} from 'react-redux';

import AddProductForm from './AddProductForm';

const AddProduct = ({history}) => (
    <div>
        <AddProductForm history={history}/>
    </div>
);

export default connect()(AddProduct);