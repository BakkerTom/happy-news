import React, { Component } from 'react';
import { FormGroup, FormControl, ControlLabel, Button } from 'react-bootstrap';
import Authenticator from '../../Authenticator';
import PropTypes from 'prop-types';
import './Login.css';

/**
 * The Login component is the first component and handles user
 * authentication via a login screen.
 */
class Login extends Component {

    constructor(props) {
        super(props);

        this.state = {
            error: false
        };

        this.handleLogin = this.handleLogin.bind(this);
    }

    /**
     * Happens after the user clicks the Login button
     */
    handleLogin(e) {
        //Bind this to _this
        const _this = this;

        //Prevent default behaviour of submit button
        e.preventDefault();
        
        //Get form information
        const usernameInput = document.querySelector('#usernameInput');
        const passwordInput = document.querySelector('#passwordInput');

        //Get account information
        const username = usernameInput.value;
        const password = passwordInput.value;

        const auth = new Authenticator();

        auth.authenticate(username, password, function(didSucceed){
            if(didSucceed) {
                _this.props.handleAuth();
                console.log(`${username} logged in`);
            } else {
                //Display error message
                _this.setState({
                    error: true
                });

                passwordInput.value = '';
            }
        });        
    }
    
    render() {
        return (
            <div className='loginForm'>
                <div className='panel panel-default'>
                    <div className='panel-body'>
                        <div className='loginHeader'>
                            <h1>Happy News</h1>
                            <p>Your source of happiness. Sign in to your account to access the editor.</p>
                        </div>
            
                        <div className={'alert alert-danger ' + (this.state.error ? '' : 'hidden')} role='alert'>
                            Whoops... Username and/or password is invalid
                        </div>
                    
                        <form>
                            <FormGroup>
                                <ControlLabel>Username</ControlLabel>
                                <FormControl type="text" id='usernameInput' />
                            </FormGroup>
                            <FormGroup>
                                <ControlLabel>Password</ControlLabel>
                                <FormControl type="password" id='passwordInput' />
                            </FormGroup>
                            <hr />
                            <Button type='submit' onClick={this.handleLogin} bsSize='large' bsStyle='primary' block>Login</Button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

Login.propTypes = {
    handleAuth: PropTypes.func.isRequired
};

export default Login;