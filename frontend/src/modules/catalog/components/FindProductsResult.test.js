import React from 'react';
import { FindProductsResult } from './FindProductsResult';
import renderer from 'react-test-renderer';

const productSearch = {
    criteria: null,
    result: {
        items: {
            
        },
        existMoreItems: false
    },
};

it('renders error message when there is no products', () => {
    const tree = renderer
    .create(<FindProductsResult productSearch={}/>)
    .toJSON();
    expect(tree).toMatchSnapshot();
});