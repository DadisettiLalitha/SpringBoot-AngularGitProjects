import React, { Component } from 'react';
import {Nav,NavItem,NavbarBrand,Navbar,NavLink} from 'reactstrap';
class AppNav extends Component {
    state = {  }
    render() { 
        return (
            <div>
              <Navbar color="dark" dark expand="md">
                <NavbarBrand href="/">Online Expense Tracker</NavbarBrand>
                <Nav className="mr-auto" navbar>
                    <NavItem>
                      <NavLink href="/">Home</NavLink>
                    </NavItem>
                    <NavItem>
                      <NavLink href="/Categories/">Categories</NavLink>
                    </NavItem>
                    <NavItem>
                      <NavLink href="/Expense/">Expense</NavLink>
                    </NavItem>
                </Nav>
              </Navbar>
            </div>
          );
        }
}
        export default AppNav;
