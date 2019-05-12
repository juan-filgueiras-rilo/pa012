import React from 'react';
import {connect} from 'react-redux';
import {Link, NavLink, withRouter} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

import {FindProducts,GetUserProducts} from '../../catalog';
import {GetUserBids} from '../../auction';
import users from '../../users';

const Header = ({userName}) => (

    <nav className="navbar navbar-expand-lg navbar-light bg-light border">
        <Link className="navbar-brand" to="/">PA Project</Link>
        <button className="navbar-toggler" type="button" 
            data-toggle="collapse" data-target="#navbarSupportedContent" 
            aria-controls="navbarSupportedContent" aria-expanded="false" 
            aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">

            <ul className="navbar-nav mr-auto">
                <li className="nav-item">
                    <NavLink exact className="nav-link" to="/">
                        <span className="fas fa-home"></span>&nbsp;
                        <FormattedMessage id="project.app.Header.home"/>
                    </NavLink>
                </li>

                <li>
                    <FindProducts/>
                </li>
                {userName &&
                    <NavLink className="btn btn-primary my-1" to="/catalog/add-product" id="addProduct">
                        <FormattedMessage id="project.catalog.addProductLink"/>

                    </NavLink>
                }
            </ul>
            
            {userName ? 

            <ul className="navbar-nav">
               
                <li className="nav-item dropdown">

                    <a className="dropdown-toggle nav-link" 
                        data-toggle="dropdown">
                        <span className="fas fa-user"></span>&nbsp;
                        {userName}
                    </a>
                    <div className="dropdown-menu dropdown-menu-right">
                        <Link className="dropdown-item text-left" to="/users/update-profile">
                            <FormattedMessage id="project.users.UpdateProfile.title"/>
                        </Link>
                        <Link className="dropdown-item" to="/users/change-password">
                            <FormattedMessage id="project.users.ChangePassword.title"/>
                        </Link>
                        <Link className="dropdown-divider" to="/auction/GetUserBids"></Link>
                        <GetUserBids/>
                        <GetUserProducts/>
                        <div className="dropdown-divider"></div>
                        <Link className="dropdown-item" to="/users/logout">
                            <FormattedMessage id="project.app.Header.logout"/>
                        </Link>
                    </div>

                </li>

            </ul>
            
            :

            <ul className="navbar-nav">
                <li className="nav-item">
                    <Link className="nav-link" to="/users/login">
                        <FormattedMessage id="project.users.Login.title"/>
                    </Link>
                </li>
            </ul>
            
            }

        </div>
    </nav>

);

const mapStateToProps = state => ({
    userName: users.selectors.getUserName(state)
});

export default connect(mapStateToProps)(Header);
