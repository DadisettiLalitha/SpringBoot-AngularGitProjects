import React, { Component } from 'react';
import {Route, BrowserRouter as Router, Switch} from 'react-router-dom'; 
import Home from './Home';
import Category from './Category';
import Expenses from './Expenses';


class App extends Component {
    state = {  }
    render() { 
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/Categories' exact={true} component={Category}/>
                    <Route path='/Expense' exact={true} component={Expenses}/>
                </Switch>
            </Router>
          );
    }
}
export default App;