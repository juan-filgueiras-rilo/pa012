import React from 'react';
import { FindProductsResult } from './FindProductsResult';
import renderer from 'react-test-renderer';
import { IntlProvider } from 'react-intl';
import { initReactIntl } from '../../../i18n';

const {locale, messages} = initReactIntl();

jest.mock ('./Products', () => 'Products')
jest.mock ('../../common/components/Pager', () => 'Pager')

const emptyProductSearch = {
    criteria: null,
    result: {
        items: [],
        existMoreItems: false
    }
};

const productSearch = {
    criteria: {
        keywords: "",
        categoryId: "",
        page: 0,
    },
    result: {
        items: [
            {
                "id": 2,
                "categoryId": 1,
                "name": "producto2",
                "currentPrice": 10,
                "remainingTime": 993853
            },
            {
                "id": 1,
                "categoryId": 2,
                "name": "producto1",
                "currentPrice": 12.5,
                "remainingTime": 10
            }
        ],
        existMoreItems: false
    },
};

const categories = ["Movies","Technology"];

it('renders error message when there is no products', () => {
    const tree = renderer
    .create(
        <IntlProvider locale={locale} messages={messages}>
            <FindProductsResult productSearch={emptyProductSearch} categories={categories}/>
        </IntlProvider>
    )
    .toJSON();
    expect(tree).toMatchSnapshot();
});

it('renders products and pager when there is at least one product', () => {
    const tree = renderer
    .create(
        <FindProductsResult productSearch={productSearch} categories={categories}/>
    )
    .toJSON();
    expect(tree).toMatchSnapshot();
});