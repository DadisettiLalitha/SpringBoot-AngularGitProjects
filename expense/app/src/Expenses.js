import React, { Component } from 'react';
import AppNav from './AppNav';
import DatePicker from 'react-datepicker';
import './App.css';
import "react-datepicker/dist/react-datepicker.css";
import {Table, Input, Label, Container, Form, FormGroup, Button} from 'reactstrap';
import {Link} from 'react-router-dom';
import Moment from 'react-moment';

class Expenses extends Component {

    
        // {
        //     "id": 100,
        //     "expensedate": "2019-06-16T17:00:00Z",
        //     "descript": "New York Buisness Trip",
        //     "location": "New York",
        //     "catgory": {
        //         "id": 1,
        //         "name": "Travel"
        //     }
        // }
    emptyItem = {
        descript : '',
        expensedate : new Date(),
        id : '100',
        location  : '',
        catgory : {id : 1, name : 'Travel'}

    }  

    constructor(props){
        super(props)
        this.state = { 
            isLoading : true,
            categories : [],
            expenses : [],
            date : new Date(),
            item : this.emptyItem
         }
         this.handleSubmit = this.handleSubmit.bind(this);
         this.handleChange = this.handleChange.bind(this);
         this.handleDateChange = this.handleDateChange.bind(this);
    }

    async handleSubmit(event){
        const item =this.state;
        await fetch(`/api/expenses`,
        {
            method : 'POST',
            headers :{
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            body : JSON.stringify(item),
        });
        event.peventDefault();
        this.props.history.push("/Expense");
    }

    handleChange(event){
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name]= value;
        this.setState({item});
        console.log(this.state);
    }

    handleDateChange(date){
        let item ={...this.state.item};
        item.expensedate = date;
        this.setState({item});
        console.log(item);
    }

        async remove(id){
            await fetch(`/api/expenses/${id}`,{
              method : 'DELETE',
              headers:{
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
              }
            }).then(() => {
                let updatedExpenses = [...this.state.expenses].filter(i => i.id !== id);
                this.setState({expenses : updatedExpenses});
            });
        }

  

     async componentDidMount(){
         const response = await fetch(`/api/categories`);
         const body = await response.json();
         this.setState({categories : body , isLoading : false});

         const responseExp = await fetch(`/api/expenses`);
         const bodyExp = await responseExp.json();
         this.setState({expenses : bodyExp, isLoading : false});
     }


    render() { 
        const title=<h3>Add Expense</h3>
        const{categories} = this.state;
        const{expenses, isLoading} = this.state;

        if(isLoading)
            return(<div>Loading...</div>)

        let optionList  =
            categories.map((category) =>
             <option value={category.id} key={category.id}>
                 {category.name}
                 </option>
             )

        let rows =
        expenses.map(expense =>
                <tr key={expense.id}>
                    <td>{expense.descript}</td>
            <td>{expense.location}</td>
            <td><Moment date={expense.expensedate} formate="yyyy/mm/dd"/></td>
            <td>{expense.catgory.name}</td>
                    <td><Button size="sm" color="danger" onClick={ () => this.remove(expense.id)}>Delete</Button></td>

                </tr>
                )        

        return (  
            <div>
                <AppNav/>
                  <Container>
                    {title}
                      <Form onSubmit ={this.handleSubmit}> 
                          <FormGroup className="col-md-4 mb-3">
                              <Label for="descript">Title</Label>
                              <Input type="descript" name="descript" id="descript" 
                                            onChange={this.handleChange} autoComplete="name"/>
                          </FormGroup>

                          <FormGroup>
                              <Label for="Category">Category</Label>
                               <select onChange={this.handleChange}>
                                   {optionList}
                               </select>
                          </FormGroup>

                          <FormGroup>
                              <Label for="expensedate">Expense Date</Label>
                              <DatePicker selected={this.state.item.expensedate} onChange={this.handleDateChange}/>
                          </FormGroup>

                          <div className="row">
                          <FormGroup className="col-md-4 mb-3">
                              <Label for="location">Location</Label>
                              <Input type="text" name="location" id="location" onChange={this.handleChange}/>
                          </FormGroup>
                          </div>  
                          <FormGroup>
                              <Button color="primary" type="submit">Save</Button>{' '}
                              <Button color="secondary" tag={Link} to="/">Cancel</Button>
                          </FormGroup>

                      </Form>
                  </Container>

                  {''}
                      <Container>
                            <h3>Expense List</h3>
                            <Table className="mt-4">
                                <thead>
                                    <tr>
                                        <th width="20%">Descript</th>
                                        <th width="10%">Location</th>
                                        <th width="20%">Date</th>
                                        <th width="10%">Category</th>
                                        <th width="10%">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {rows}
                                </tbody>
                            </Table>
                      </Container>


                </div>
        );
    }
}
 
export default Expenses;