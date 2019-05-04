import React from 'react';
import {connect} from 'react-redux';
import {BrowserRouter as Router} from 'react-router-dom';

import Header from './Header';
import Body from './Body';
import Footer from './Footer';
import users from '../../users';
import catalog from '../../catalog';

const reauthenticationCallback = dispatch => () => 
    dispatch(users.actions.logout());

class App extends React.Component {

    componentDidMount() {
  
        this.props.dispatch(
            users.actions.tryLoginFromServiceToken(
                reauthenticationCallback(this.props.dispatch)));

        this.props.dispatch(catalog.actions.findAllCategories());

    }

    render() {

        return (
            <div>
                <Router>
                    <div>
                        <Header/>
                        <Body/>
                    </div>
                </Router>
                <Footer/>
            </div>
        );

    }

}

export default connect()(App);