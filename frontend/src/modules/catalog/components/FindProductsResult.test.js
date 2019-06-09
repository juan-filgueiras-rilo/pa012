import React from 'react';
import { FindProductsResult } from './FindProductsResult';
import renderer from 'react-test-renderer';
import { IntlProvider } from 'react-intl';
import {en} from '../../../i18n/messages';

const emptyProductSearch = {
    criteria: {
        keywords: null,
        categoryId: null,
        page: -1,
    },
    result: {
        items: {
            length: 0,
            items: []
        },
        existMoreItems: false
    },
};

const productSearch = {
    criteria: {
        keywords: null,
        categoryId: null,
        page: -1,
    },
    result: {
        items: {
            length: 0,
            items: []
        },
        existMoreItems: false
    },
};

const categories = ["Movies","Technology"];

it('renders error message when there is no products', () => {
    const tree = renderer
    .create(
        <IntlProvider locale={'en'} messages={en}>
            <FindProductsResult productSearch={emptyProductSearch} categories={categories}/>
        </IntlProvider>
    )
    .toJSON();
    expect(tree).toMatchSnapshot();
});